package com.calabrio.controller.auth;

import com.calabrio.controller.AbstractController;
import com.calabrio.model.auth.AuthRequest;
import com.calabrio.model.user.WFOPerson;
import com.calabrio.security.UserPrincipal;
import com.calabrio.service.impl.person.WFOPersonService;
import com.calabrio.util.JsonUtil;
import com.calabrio.util.properties.SessionProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

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

    @RequestMapping(name = "auth", value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<String> auth(HttpServletRequest rq) {
        try {
            AuthRequest auth = JsonUtil.fromJson(requestBody(rq), AuthRequest.class);
            setAttribute(rq, SessionProperties.WFO_TENANT, auth.getTenantId());

            // Authenticate
            WFOPerson authUser = userService.authenticate(auth);
            if(authUser == null) {
                clearSession(rq);
                return errorResponse("Error authenticating user credentials", 400);
            }

            // Set our Security Context for this User
            setSecurityContext(authUser);

            String json = JsonUtil.toJson(authUser);
            setAttribute(rq, SessionProperties.WFO_PERSON, json);
            return ResponseEntity.ok(json);
        } catch (ParseException | AuthenticationException e) {
            log.debug(e.getMessage(), e);
            clearSession(rq);
            return errorResponse(e.getMessage(), 400);
        }
    }

    private void setSecurityContext(WFOPerson authUser) {
        UserPrincipal principal = new UserPrincipal();
        principal.setPerson(authUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
