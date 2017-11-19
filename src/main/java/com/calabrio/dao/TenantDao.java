package com.calabrio.dao;

import com.calabrio.model.tenant.Tenant;

import java.util.List;

public interface TenantDao {
    List<Tenant> getAll();
}
