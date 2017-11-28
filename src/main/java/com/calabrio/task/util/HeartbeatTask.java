package com.calabrio.task.util;

import com.calabrio.repository.admin.AdminRepository;
import com.calabrio.task.AbstractTask;
import com.calabrio.util.properties.DbProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

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
    private AdminRepository adminRepository;

    @Override
    public void run() {
        log.debug("Running heartbeat task...");
        setTenantId(DbProperties.DEFAULT_TENANT);
        setTenantSession();

        if(!adminRepository.heartbeat()) {
            log.debug("Error attempting heartbeat query!");
        }
    }

    @Override
    public Date getNextRunTime(TriggerContext triggerContext) {
        log.debug("Getting next run time for HeartBeat");
        CronTrigger trigger = new CronTrigger(getCronExpression());
        return trigger.nextExecutionTime(triggerContext);
    }

    @Override
    public String getCronExpression() {
        return EVERY_MINUTE;
    }

    @Override
    public Integer getTenantId() {
        return null;
    }
}
