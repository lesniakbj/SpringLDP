package com.calabrio.service.impl.tenant;

import com.calabrio.dao.TenantDao;
import com.calabrio.model.tenant.Tenant;
import com.calabrio.model.tenant.TenantProperty;
import com.calabrio.service.AbstractService;
import com.calabrio.service.TenantService;
import com.calabrio.util.properties.DbProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl extends AbstractService implements TenantService {
    private static final Logger log = Logger.getLogger(TenantServiceImpl.class);

    @Autowired
    TenantDao tenantDao;

    @Override
    public List<Tenant> getAllTenants() {
        log.debug("GetAllTenants TenantService");
        // Has to run as the default (Admin) tenant
        setTenantId(DbProperties.DEFAULT_TENANT);
        return tenantDao.getAll();
    }

    @Override
    public List<TenantProperty> getAllTenantProperties() {
        log.debug("GetAllTenantProperties TenantService");
        return tenantDao.getAllProperties();
    }

    @Override
    public Tenant addTenant(Tenant tenant) {
        // Has to run as the default (Admin) tenant
        setTenantId(DbProperties.DEFAULT_TENANT);

        // Create Tenant Database

        // Create Tenant DB User

        // Add Tenant To CommonTables
        return tenantDao.add(tenant);
    }

    @Override
    public void removeTenant(Tenant tenant) {
        tenantDao.delete(tenant);
    }
}
