package com.calabrio.service.impl.admin;

import com.calabrio.repository.tenant.TenantDao;
import com.calabrio.model.tenant.Tenant;
import com.calabrio.service.AbstractService;
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
    private TenantDao tenantDao;

    @Override
    public List<Tenant> getAllTenantsNoAuth() {
        log.debug("GetAllTenants TenantService");
        // Has to run as the default (Admin) tenant
        setTenantId(DbProperties.DEFAULT_TENANT);
        List<Tenant> tenants = tenantDao.findAll();
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
        return tenantDao.findAll();
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN_SYSTEM')")
    public Tenant getTenantById(Integer tenantId) {
        log.debug("GetTenantById TenantService");
        // Has to run as the default (Admin) tenant
        setTenantId(DbProperties.DEFAULT_TENANT);
        return tenantDao.findById(tenantId);
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN_SYSTEM')")
    public Tenant addTenant(Tenant tenant) {
        // Has to run as the default (Admin) tenant
        setTenantId(DbProperties.DEFAULT_TENANT);

        // Create Tenant Database

        // Create Tenant DB User

        // Add Tenant To CommonTables
        return tenantDao.add(tenant);
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN_SYSTEM')")
    public void removeTenant(Tenant tenant) {
        tenantDao.delete(tenant);
    }
}
