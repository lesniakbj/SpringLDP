package com.calabrio.interceptor;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;

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
 * Created by Brendan.Lesniak on 11/29/2017.
 */
public class QueryInterceptor extends EmptyInterceptor {
    private static final Logger log = Logger.getLogger(QueryInterceptor.class);

    @Override
    public String onPrepareStatement(String sql) {
        log.debug(String.format("[SQL]: %s", sql));
        return super.onPrepareStatement(sql);
    }
}
