package com.calabrio.service;

import com.calabrio.datasource.context.TenantContext;

public abstract class AbstractService {
    public void setTenantId(Integer tenantId) {
        TenantContext.setTenantId(tenantId);
    }
}
