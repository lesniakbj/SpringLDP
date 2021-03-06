package com.calabrio.task.util;

import com.calabrio.service.admin.AdminTenantService;
import com.calabrio.task.AbstractTask;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by Brendan.Lesniak on 11/28/2017.
 */
@Component
public class HeartbeatTask extends AbstractTask {
    private static final Logger log = Logger.getLogger(HeartbeatTask.class);

    @Autowired
    private AdminTenantService adminTenantService;

    @Override
    public void run() {
        log.debug("Running heartbeat task...");

        // Heartbeat to check all Tenant Connections
        if(!adminTenantService.heartbeat()) {
            log.debug("Error attempting heartbeat query!");
        }
    }

    @Override
    public String getCronExpression() {
        // Get the expression from the Db, Properties, Etc...
        return EVERY_MINUTE;
    }
}
