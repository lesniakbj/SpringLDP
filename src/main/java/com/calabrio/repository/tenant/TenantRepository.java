package com.calabrio.repository.tenant;

import com.calabrio.model.tenant.Tenant;

import java.util.List;

public interface TenantRepository {
    Tenant findById(Integer tenantId);
    List<Tenant> findAll();

    Tenant add(Tenant tenant);
    List<Tenant> addBatch(List<Tenant> tenants);

    Tenant update(Tenant tenant);
    void delete(Tenant tenant);
}
