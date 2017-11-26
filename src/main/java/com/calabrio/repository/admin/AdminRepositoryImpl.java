package com.calabrio.repository.admin;

import com.calabrio.repository.AbstractRepository;
import org.apache.log4j.Logger;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminRepositoryImpl extends AbstractRepository implements AdminRepository {
    private static final Logger log = Logger.getLogger(AdminRepositoryImpl.class);

    private static final String CREATE_TENANT_DB = "EXEC admin.createTenantDatabase :dbName, :username, :password";
    private static final String CREATE_TENANT_TABLES = "EXEC admin.createTenantTables :dbName";
    private static final String CREATE_BASE_TENANT_USERS = "EXEC admin.createTenantUsers :dbName, :tenantId";

    @Override
    public boolean createTenantDatabase(String databaseName, String username, String password) {
        log.debug("Creating Tenant DB Query");
        NativeQuery createDb = getSession().createNativeQuery(CREATE_TENANT_DB);
        createDb.setParameter("dbName", databaseName);
        createDb.setParameter("username", username);
        createDb.setParameter("password", password);
        return createDb.executeUpdate() >= 0;
    }

    @Override
    public boolean createTenantTables(String databaseName) {
        log.debug("Creating Tenant Tables Query");
        NativeQuery createTables = getSession().createNativeQuery(CREATE_TENANT_TABLES);
        createTables.setParameter("dbName", databaseName);
        return createTables.executeUpdate() >= 0;
    }

    @Override
    public boolean createBaseTenantUsers(String databaseName, Integer tenantId) {
        log.debug("Creating Base Tenant Users Query");
        NativeQuery createUsers = getSession().createNativeQuery(CREATE_BASE_TENANT_USERS);
        createUsers.setParameter("dbName", databaseName);
        createUsers.setParameter("tenantId", tenantId);
        return createUsers.executeUpdate() >= 0;
    }
}
