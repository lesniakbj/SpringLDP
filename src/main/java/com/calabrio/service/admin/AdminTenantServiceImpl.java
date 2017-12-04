package com.calabrio.service.admin;

import com.calabrio.model.tenant.Tenant;
import com.calabrio.model.tenant.TenantState;
import com.calabrio.repository.admin.AdminRepository;
import com.calabrio.repository.tenant.TenantRepository;
import com.calabrio.service.AbstractService;
import com.calabrio.util.properties.DbProperties;
import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminTenantServiceImpl extends AbstractService implements AdminTenantService {
    private static final Logger log = Logger.getLogger(AdminTenantServiceImpl.class);

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public List<Tenant> getAllTenantsNoAuth() {
        log.debug("GetAllTenants TenantService");
        // Has to run as the default (Admin) tenant
        setTenantId(DbProperties.DEFAULT_TENANT);
        List<Tenant> tenants = tenantRepository.findAll();
        for(Tenant t : tenants) {
            t.setDatabasePassword(null);
            t.setDatabaseUserName(null);
            t.setDatabaseName(null);
        }
        return tenants;
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN_SYSTEM')")
    public List<Tenant> getAllTenants() {
        log.debug("GetAllTenants TenantService");
        // Has to run as the default (Admin) tenant
        setTenantId(DbProperties.DEFAULT_TENANT);
        return tenantRepository.findAll();
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN_SYSTEM')")
    public Tenant getTenantById(Integer tenantId) {
        log.debug("GetTenantById TenantService");
        // Has to run as the default (Admin) tenant
        setTenantId(DbProperties.DEFAULT_TENANT);
        return tenantRepository.findById(tenantId);
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN_SYSTEM')")
    public Tenant addTenant(Tenant tenant) {
        log.debug(String.format("Add Tenant %s", tenant));
        // Has to run as the default (Admin) tenant
        setTenantId(DbProperties.DEFAULT_TENANT);

        if(tenantRepository.findByName(tenant.getTenantName(), tenant.getDatabaseName()) != null) {
            throw new ServiceException(String.format("Unable to add Tenant! Already Exists by name: %s", tenant.getTenantName()));
        }

        // Set Tenant State and Add it
        tenant.setTenantState(TenantState.NEW);
        Tenant t = tenantRepository.add(tenant);

        // Create Tenant Database
        boolean createDb = adminRepository.createTenantDatabase(t.getDatabaseName(), t.getDatabaseUserName(), t.getDatabasePassword());
        boolean createTables = adminRepository.createTenantTables(t.getDatabaseName());
        boolean createUsers =  adminRepository.createBaseTenantUsers(t.getDatabaseName(), t.getTenantId());

        if(!(createDb && createTables && createUsers)) {
            t.setTenantState(TenantState.FAILED_SETUP);
            tenantRepository.update(t);
        }

        return t;
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN_SYSTEM')")
    public void removeTenant(Tenant tenant) {
        log.debug(String.format("Delete Tenant %s", tenant));

        // Has to run as the default (Admin) tenant
        setTenantId(DbProperties.DEFAULT_TENANT);
        tenantRepository.delete(tenant);
    }

    @Override
    public boolean heartbeat() {
        setTenantId(DbProperties.DEFAULT_TENANT);
        List<Tenant> tenants = getAllTenants();
        for(Tenant t : tenants) {
            log.debug(String.format("Checking Tenant %s Heat Beat", t.getTenantId()));
            setTenantId(t.getTenantId());
            if(!adminRepository.heartbeat()) {
                return false;
            }
        }
        return true;
    }
}
