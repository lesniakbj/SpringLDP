package com.calabrio.datasource;

import com.calabrio.util.DbProperties;
import org.apache.log4j.Logger;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
public class MultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    private static final Logger log = Logger.getLogger(MultiTenantConnectionProvider.class);

    private static final Properties props = loadProperties();
    private DriverManagerDataSource defaultDataSource;
    private DriverManagerDataSource tenantDataSource;
    private Map<Integer, String> tenantDbMap;

    public MultiTenantConnectionProvider(){
        defaultDataSource = initDataSourceDefaults();
        tenantDataSource = initDataSourceDefaults();
        initTenantMap();
    }

    private DriverManagerDataSource initDataSourceDefaults() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(props.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(props.getProperty("jdbc.short.url"));
        dataSource.setCatalog(props.getProperty("jdbc.default.db"));
        dataSource.setPassword(props.getProperty("jdbc.password"));
        dataSource.setUsername(props.getProperty("jdbc.username"));
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

        String tenantDb = tenantDbMap.get(Integer.parseInt(tenantIdentifier));
        if(tenantDb == null) {
            log.debug(String.format("Unable to find by id %s, got null.", tenantIdentifier));
            return defaultDataSource;
        }

        tenantDataSource.setCatalog(tenantDb);

        log.debug(String.format("Data Source Selected: %s/%s/%s/%s", tenantDataSource.getUrl(), tenantDataSource.getCatalog(), tenantDataSource.getUsername(), tenantDataSource.getPassword()));
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

    private void initTenantMap() {
        tenantDbMap = new HashMap<>();
        tenantDbMap.put(-1, "SpringLDPCommon");
        try(ResultSet rs = getAnyConnection().prepareStatement("SELECT tenantId, tenantDbName FROM WFOTenant").executeQuery()) {
            while (rs.next()) {
                tenantDbMap.put(rs.getInt("tenantId"), rs.getString("tenantDbName"));
            }
        } catch (SQLException e) {
            log.debug("Error querying for Tenant Map");
        }
    }
}
