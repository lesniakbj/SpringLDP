package com.calabrio.model.auth;

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
    private Integer tenantId;

    public AuthRequest(String email, String password, Integer tenantId) {
        this.email = email;
        this.password = password;
        this.tenantId = tenantId;
    }

    public String toString() {
        return "Username: " + email + ", Password: " + password + ", TenantId: " + tenantId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }
}
