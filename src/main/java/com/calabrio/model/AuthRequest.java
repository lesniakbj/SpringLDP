package com.calabrio.model;

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
public class AuthRequest {
    private String email;
    private String password;
    private String tenantDb;

    public AuthRequest(String email, String password, String tenantDb) {
        this.email = email;
        this.password = password;
        this.tenantDb = tenantDb;
    }

    public String toString() {
        return "Username: " + email + ", Password: " + password + ", TenantDB: " + tenantDb;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTenantDb() {
        return tenantDb;
    }
}
