package com.calabrio.service.impl.tenant;

import com.calabrio.model.tenant.Tenant;
import com.calabrio.model.tenant.TenantProperty;

import java.util.List;

public interface TenantService {
    List<TenantProperty> getAllTenantProperties();
}
