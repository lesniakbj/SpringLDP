package com.calabrio.datasource;

import com.calabrio.model.WFOUser;
import com.calabrio.util.DbProperties;
import com.calabrio.util.SessionProperties;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

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
public class TenantResolver implements CurrentTenantIdentifierResolver {
    private static final Logger log = Logger.getLogger(TenantResolver.class);

    @Override
    public String resolveCurrentTenantIdentifier() {
        log.debug("Attempting to resolve current tenant.");
        return resolveTenant();
    }

    private String resolveTenant() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attr != null) {
            HttpSession sess = attr.getRequest().getSession(false);
            if(sess != null) {
                String tenantDb = (String) sess.getAttribute(SessionProperties.WFO_DB);
                if(tenantDb != null) {
                    log.debug(String.format("Tenant Resolved to: %s", tenantDb));
                    return tenantDb;
                }
            }
        }

        log.debug("Resolved to default tenant ID.");
        return DbProperties.DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
