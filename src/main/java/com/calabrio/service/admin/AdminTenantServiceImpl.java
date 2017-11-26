package com.calabrio.service.admin;

import com.calabrio.model.tenant.Tenant;
import com.calabrio.model.tenant.TenantState;
import com.calabrio.repository.admin.AdminRepository;
import com.calabrio.repository.tenant.TenantRepository;
import com.calabrio.service.AbstractService;
import com.calabrio.util.ObjectsUtil;
import com.calabrio.util.properties.DbProperties;
import org.apache.log4j.Logger;
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
        // Has to run as the default (Admin) tenant
        setTenantId(DbProperties.DEFAULT_TENANT);

        // Set Tenant State and Add it
        tenant.setTenantState(TenantState.NEW);
        Tenant t = tenantRepository.add(tenant);

        // Create Tenant Database
        adminRepository.createTenantDatabase(t.getDatabaseName(), t.getDatabaseUserName(), t.getDatabasePassword());

        // Create Tenant Tables
        adminRepository.createTenantTables(t.getDatabaseName());

        // Populate Base User (Service User, Base Tenant Admin)
        adminRepository.createBaseTenantUsers(t.getDatabaseName(), t.getTenantId());

        return t;
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN_SYSTEM')")
    public void removeTenant(Tenant tenant) {
        tenantRepository.delete(tenant);
    }
}
