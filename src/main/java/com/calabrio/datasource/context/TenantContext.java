package com.calabrio.datasource.context;

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
public class TenantContext {
    private static final ThreadLocal<Integer> TENANT_CONTEXT = new ThreadLocal<>();

    public static void setTenantId(Integer tenantId) {
        TENANT_CONTEXT.set(tenantId);
    }

    public static Integer getTenantId() {
        return TENANT_CONTEXT.get();
    }
}
