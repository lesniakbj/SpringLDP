package com.calabrio.util.properties;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
public class SessionProperties {
    public static final String WFO_TENANT = "WFOTenant";
    public static final String WFO_PERSON = "WFOPerson";

    public static Integer getTenantId() {
        return (Integer)((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(false).getAttribute(WFO_TENANT);
    }

    public static void setTenantId(Integer tenantId) {
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(false).setAttribute(WFO_TENANT, tenantId);
    }
}
