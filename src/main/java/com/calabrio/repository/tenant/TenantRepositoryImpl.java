package com.calabrio.repository.tenant;

import com.calabrio.repository.AbstractRepository;
import com.calabrio.model.tenant.Tenant;
import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TenantRepositoryImpl extends AbstractRepository implements TenantRepository {
    private static final Logger log = Logger.getLogger(TenantRepositoryImpl.class);

    @Override
    public Tenant findById(Integer tenantId) {
        Query query = super.getQuery("FROM Tenant WHERE tenantId = :tenantId").setParameter("tenantId", tenantId);
        return querySingleResult(query);
    }

    @Override
    public List<Tenant> findAll() {
        log.debug("GetAll TenantDao");
        return super.getAllOfType(getSession(), Tenant.class);
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
