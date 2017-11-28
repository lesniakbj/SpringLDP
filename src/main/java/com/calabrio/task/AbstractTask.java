package com.calabrio.task;

import org.apache.log4j.Logger;
import org.springframework.scheduling.TriggerContext;
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
public abstract class AbstractTask implements Runnable {
    private static final Logger log = Logger.getLogger(AbstractTask.class);

    protected static final String EVERY_SECOND = "* * * ? * *";
    protected static final String EVERY_FIVE_SECONDS = "*/5 * * ? * *";

    protected static final String EVERY_MINUTE = "0 * * ? * *";
    protected static final String EVERY_FIVE_MINUTES = "0 */5 * ? * *";

    protected static final String EVERY_HOUR = "0 0 * ? * *";

    public abstract Date getNextRunTime(TriggerContext triggerContext);

    public abstract String getCronExpression();
}
