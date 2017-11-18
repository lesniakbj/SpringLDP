package com.calabrio.datasource;

import com.calabrio.util.DbProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.stereotype.Component;

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
@Component(value = "dataSourceLookup")
public class TenantDataSourceLookup extends MapDataSourceLookup {
    private static final Logger log = Logger.getLogger(TenantDataSourceLookup.class);

    @Autowired
    public TenantDataSourceLookup(DataSource defaultDataSource) {
        super();
        initializeDataSources(defaultDataSource);
    }

    private void initializeDataSources(DataSource defaultDataSource) {
        addDataSource(DbProperties.DEFAULT_TENANT, defaultDataSource);
    }
}
