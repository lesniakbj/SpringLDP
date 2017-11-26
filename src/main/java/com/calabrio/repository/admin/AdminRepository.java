package com.calabrio.repository.admin;

import com.calabrio.repository.AbstractRepository;

public interface AdminRepository {
    boolean createTenantDatabase(String databaseName, String username, String password) ;
    boolean createTenantTables(String databaseName);
    boolean createBaseTenantUsers(String databaseName, Integer tenantId);
}
