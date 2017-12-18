package com.calabrio.datasource;

import com.calabrio.datasource.context.TenantContext;
import com.calabrio.util.properties.DbProperties;
import com.calabrio.util.properties.SessionProperties;
import org.apache.log4j.Logger;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

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
public class TenantResolver implements CurrentTenantIdentifierResolver {
    private static final Logger log = Logger.getLogger(TenantResolver.class);

    @Override
    public String resolveCurrentTenantIdentifier() {
        log.debug("Attempting to resolve current tenant.");
        return Integer.toString(resolveTenant());
    }

    // First check to see if our TenantContext for the running thread has
    // been set; otherwise, we will want to set the TenantId based on the
    // HTTP session of the logged in user.
    private Integer resolveTenant() {
        // TenantContext should be used in Service/Intra-App calls.
        Integer tenantDb = TenantContext.getTenantId();
        if(tenantDb != null) {
            log.debug(String.format("Tenant Resolved using TenantContext to: %s", tenantDb));
            return tenantDb;
        }

        // Session Properties should be set on the HTTP Session level, for a logged in user.
        tenantDb = SessionProperties.getTenantId();
        if(tenantDb != null) {
            log.debug(String.format("Tenant Resolved using SessionProperties to: %s", tenantDb));
            return tenantDb;
        }

        log.debug("Resolved to default tenant ID.");
        return DbProperties.DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
