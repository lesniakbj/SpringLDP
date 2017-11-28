package com.calabrio.repository.admin;

public interface AdminRepository {
    boolean heartbeat();
    boolean createTenantDatabase(String databaseName, String username, String password) ;
    boolean createTenantTables(String databaseName);
    boolean createBaseTenantUsers(String databaseName, Integer tenantId);
}
