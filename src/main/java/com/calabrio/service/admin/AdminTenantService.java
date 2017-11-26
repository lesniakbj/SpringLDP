package com.calabrio.service.admin;

import com.calabrio.model.tenant.Tenant;

import java.util.List;

public interface AdminTenantService {
    Tenant getTenantById(Integer tenantId);
    List<Tenant> getAllTenants();
    List<Tenant> getAllTenantsNoAuth();

    Tenant addTenant(Tenant tenant);
    void removeTenant(Tenant tenant);
}
