package com.calabrio.datasource;

import com.calabrio.util.ConnectionUtil;
import com.calabrio.util.properties.DbProperties;
import org.apache.log4j.Logger;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * (c) Copyright 2017 Calabrio, Inc.
 * All Rights Reserved. www.calabrio.com LICENSED MATERIALS
 * Property of Calabrio, Inc., Minnesota, USA
 * <p>
 * No part of this publication may be reproduced, stored or transmitted,
 * in any form or by any means (electronic, mechanical, photocopying,
 * recording or otherwise) without prior written permission from Calabrio Software.
 * <p>
 * <p>
 * Created by Brendan.Lesniak on 11/17/2017.
 */
@Component
public class MultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    private static final Logger log = Logger.getLogger(MultiTenantConnectionProvider.class);
    private static final Properties props = loadProperties();

    private DriverManagerDataSource defaultDataSource;

    private Integer tenantId;
    private DriverManagerDataSource tenantDataSource;

    @Autowired
    @Qualifier(value = "dataSourceLookupBean")
    private TenantDataSourceLookup dataSourceLookup;

    public MultiTenantConnectionProvider(){
        defaultDataSource = initDataSourceDefaults();
        tenantDataSource = initDataSourceDefaults();
    }

    private DriverManagerDataSource initDataSourceDefaults() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(props.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(ConnectionUtil.connectionString(props.getProperty("jdbc.default.host"), props.getProperty("jdbc.default.port"), props.getProperty("jdbc.default.db"), props.getProperty("jdbc.username"), props.getProperty("jdbc.password")));
        return dataSource;
    }

    @Override
    protected DataSource selectAnyDataSource() {
        log.debug("Selecting Default DataSource");
        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        log.debug(String.format("Connection to DataSource by: %s", tenantIdentifier));

        Integer tId = Integer.parseInt(tenantIdentifier);
        if(tenantIdentifier == null || Objects.equals(tId, DbProperties.DEFAULT_TENANT)) {
            log.debug("No tenant Identifier.");
            return defaultDataSource;
        }

        // Check to see if the tenant as same as last time, if so just return the datasource.
        if(Objects.equals(tenantId, tId)) {
            return tenantDataSource;
        }

        tenantDataSource = (DriverManagerDataSource)dataSourceLookup.getDataSource(tenantIdentifier, selectAnyDataSource());
        log.debug(String.format("Data Source Selected: %s", tenantDataSource.getUrl()));
        return tenantDataSource;
    }

    private static Properties loadProperties() {
        log.debug("Loading properties for Connection Provider");
        try(InputStream in = MultiTenantConnectionProvider.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(in);
            return prop;
        } catch (IOException e) {
            log.debug("Error loading properties for Connection Provider");
        }

        return null;
    }
}
