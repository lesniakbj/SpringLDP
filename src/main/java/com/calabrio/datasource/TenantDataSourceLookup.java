package com.calabrio.datasource;

import com.calabrio.model.tenant.Tenant;
import com.calabrio.util.ConnectionUtil;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(value = "dataSourceLookupBean")
public class TenantDataSourceLookup implements DataSourceLookup {
    private static final Logger log = Logger.getLogger(TenantDataSourceLookup.class);

    private List<Tenant> tenants;
    private Map<Integer, DataSource> tenantDataSources;

   DataSource getDataSource(String dataSourceName, DataSource ds) throws DataSourceLookupFailureException {
        if(tenantDataSources == null && ds != null) {
            tenants = loadTenants(ds);
            log.debug("Tenants Size " + tenants.size());
            initTenantDataSources();
        }

        log.debug(String.format("Getting: %s / %s", dataSourceName, tenantDataSources.get(Integer.parseInt(dataSourceName))));
        return tenantDataSources.get(Integer.parseInt(dataSourceName));
    }

    // NOTE: This is a raw query as we create this Lookup bean
    // before Hibernate can fully initialize, thus we do not actually
    // have access to a Hibernate Session.
    private static final String TENANT_DATA_STORE = "SELECT tenantId, databaseName, databaseUserName, databasePassword FROM Tenant";
    private List<Tenant> loadTenants(DataSource ds) {
        List<Tenant> tenants = new ArrayList<>();
        log.debug("Retrieving Tenant DataSources");
        try(ResultSet rs = ds.getConnection().prepareStatement(TENANT_DATA_STORE).executeQuery()) {
            while(rs.next()) {
                Tenant t = new Tenant();
                t.setTenantId(rs.getInt("tenantId"));
                t.setDatabaseName(rs.getString("databaseName"));
                t.setDatabaseUserName(rs.getString("databaseUserName"));
                t.setDatabasePassword(rs.getString("databasePassword"));
                tenants.add(t);
            }
        } catch (SQLException e) {
            log.debug("Error trying to query for Tenant Data Stores.", e);
        }

        return tenants;
    }

    private void initTenantDataSources() {
        tenantDataSources = new HashMap<>();
        for(Tenant t : tenants) {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ds.setUrl(ConnectionUtil.connectionString("localhost","1433",t.getDatabaseName(),t.getDatabaseUserName(),t.getDatabasePassword()));
            tenantDataSources.put(t.getTenantId(), ds);
        }
    }

    @Override
    public DataSource getDataSource(String dataSourceName) throws DataSourceLookupFailureException {
        return getDataSource(dataSourceName, null);
    }
}
