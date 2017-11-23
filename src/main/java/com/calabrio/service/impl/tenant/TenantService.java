package com.calabrio.service.impl.tenant;

import com.calabrio.model.tenant.Tenant;
import com.calabrio.model.tenant.TenantProperty;

import java.util.List;

public interface TenantService {
    List<Tenant> getAllTenants();
    List<TenantProperty> getAllTenantProperties();

    Tenant addTenant(Tenant tenant);
    void removeTenant(Tenant tenant);
}
