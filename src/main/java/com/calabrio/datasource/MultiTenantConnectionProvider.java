package com.calabrio.datasource;

import com.calabrio.util.DbProperties;
import org.apache.log4j.Logger;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
@Configurable
public class MultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    private static final Logger log = Logger.getLogger(MultiTenantConnectionProvider.class);

    private static final Properties props = loadProperties();
    private DriverManagerDataSource defaultDataSource;
    private DriverManagerDataSource tenantDataSource;
    private TenantDataSourceLookup dataSourceLookup;

    public MultiTenantConnectionProvider(){
        defaultDataSource = initDataSourceDefaults();
        tenantDataSource = initDataSourceDefaults();
        dataSourceLookup = new TenantDataSourceLookup();
    }

    private DriverManagerDataSource initDataSourceDefaults() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(props.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(formatConnectionString(props.getProperty("jdbc.default.host"), props.getProperty("jdbc.default.port"), props.getProperty("jdbc.default.db"), props.getProperty("jdbc.username"), props.getProperty("jdbc.password")));
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

        if(tenantIdentifier == null || Integer.parseInt(tenantIdentifier) == -1) {
            log.debug("No tenant Identifier.");
            return defaultDataSource;
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

    static String formatConnectionString(String host, String port, String databaseName, String user, String pass) {
        return String.format("jdbc:sqlserver://%s:%s;database=%s;user=%s;password=%s;", host, port, databaseName, user, pass);
    }
}
