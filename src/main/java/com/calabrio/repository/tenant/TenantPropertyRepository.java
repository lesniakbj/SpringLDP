package com.calabrio.repository.tenant;

import com.calabrio.model.tenant.TenantProperty;

import java.util.List;

public interface TenantPropertyRepository {
    List<TenantProperty> findAll();
}
