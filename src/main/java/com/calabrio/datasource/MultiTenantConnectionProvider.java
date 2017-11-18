package com.calabrio.datasource;

import org.apache.log4j.Logger;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;

import javax.sql.DataSource;

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

    @Autowired
    private DataSource defaultDataSource;

    @Autowired
    private DataSourceLookup dataSourceLookup;

    @Override
    protected DataSource selectAnyDataSource() {
        log.debug(String.format("Selecting Any DataSource: %s", defaultDataSource));
        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        log.debug(String.format("Looking up DataSource by: %s, %s", dataSourceLookup, tenantIdentifier));
        DataSource ds = dataSourceLookup.getDataSource(tenantIdentifier);
        log.debug(String.format("Data Source Selected: %s", ds));
        return ds;
    }
}
