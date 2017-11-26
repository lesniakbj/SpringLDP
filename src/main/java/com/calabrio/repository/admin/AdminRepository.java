package com.calabrio.repository.admin;

import com.calabrio.repository.AbstractRepository;

public interface AdminRepository {
    public void createTenantDatabase(String databaseName, String username, String password) ;

    void createTenantTables(String databaseName);

    void createBaseTenantUsers(String databaseName, Integer tenantId);
}
