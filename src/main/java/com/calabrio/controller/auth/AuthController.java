package com.calabrio.controller.auth;

import com.calabrio.controller.AbstractController;
import com.calabrio.dto.message.SuccessMessage;
import com.calabrio.model.auth.AuthRequest;
import com.calabrio.model.tenant.Tenant;
import com.calabrio.model.user.WFOPerson;
import com.calabrio.service.impl.admin.AdminTenantService;
import com.calabrio.service.impl.person.WFOPersonService;
import com.calabrio.util.JsonUtil;
import com.calabrio.util.properties.SessionProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
@RestController
public class AuthController extends AbstractController {
    private static final Logger log = Logger.getLogger(AuthController.class);

    @Autowired
    private WFOPersonService userService;

    @Autowired
    private AdminTenantService adminTenantService;

    @RequestMapping(name = "auth", value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<String> auth(HttpServletRequest rq, @RequestBody String requestJson) {
        log.debug("Auth, attempting to authenticate user session");
        try {
            log.debug(String.format("Passed in RequestBody: %s", requestJson));
            AuthRequest auth = JsonUtil.fromJson(requestBody(rq, requestJson), AuthRequest.class);
            setAttribute(rq, SessionProperties.WFO_TENANT, auth.getTenantId());

            // If there isn't a TenantId, return a list of Tenant Name/Ids
            if(auth.getTenantId() == null) {
                List<Tenant> tenants = adminTenantService.getAllTenantsNoAuth();
                return ResponseEntity.ok(JsonUtil.toJson(tenants));
            }

            // Authenticate
            WFOPerson authUser = userService.authenticate(auth);
            if(authUser == null) {
                clearSession(rq);
                return errorResponse("Error authenticating user credentials", 400);
            }

            String json = JsonUtil.toJson(authUser);
            setAttribute(rq, SessionProperties.WFO_PERSON, json);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            clearSession(rq);
            return errorResponse(e.getMessage(), 400);
        }
    }

    @RequestMapping(name = "logout", value = "/auth/logout", method = RequestMethod.DELETE)
    public ResponseEntity<String> logout(HttpServletRequest rq) {
        log.debug("Logging out user, clearing session.");
        SecurityContextHolder.getContext().setAuthentication(null);
        clearSession(rq);
        return ResponseEntity.ok(JsonUtil.toJson(new SuccessMessage("Successfully logged out")));
    }
}
