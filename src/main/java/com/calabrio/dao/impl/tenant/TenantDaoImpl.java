package com.calabrio.dao.impl.tenant;

import com.calabrio.dao.AbstractDao;
import com.calabrio.dao.TenantDao;
import com.calabrio.model.tenant.Tenant;
import com.calabrio.model.tenant.TenantProperty;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TenantDaoImpl extends AbstractDao implements TenantDao {
    private static final Logger log = Logger.getLogger(TenantDaoImpl.class);

    @Override
    public List<Tenant> getAll() {
        log.debug("GetAll TenantDao");
        return super.getAllOfType(getSession(), Tenant.class);
    }

    @Override
    public List<TenantProperty> getAllProperties() {
        log.debug("GetAllProperties TenantDao");
        return super.getAllOfType(getSession(), TenantProperty.class);
    }

    @Override
    public Tenant add(Tenant tenant) {
        log.debug("AddingTenant TenantDao");
        return super.add(tenant);
    }

    @Override
    public List<Tenant> addBatch(List<Tenant> tenants) {
        log.debug("AddingTenantBatch TenantDao");
        return super.batchAdd(tenants);
    }

    @Override
    public void delete(Tenant tenant) {
        log.debug("DeleteTenant TenantDao");
        getSession().delete(tenant);
    }
}
