package com.calabrio.dao;

import com.calabrio.model.tenant.Tenant;
import com.calabrio.model.tenant.TenantProperty;

import java.util.List;

public interface TenantDao {
    List<Tenant> getAll();
    List<TenantProperty> getAllProperties();

    Tenant add(Tenant tenant);
    List<Tenant> addBatch(List<Tenant> tenants);
    void delete(Tenant tenant);
}
