package com.calabrio.controller;

import com.calabrio.model.AuthRequest;
import com.calabrio.model.WFOUser;
import com.calabrio.service.WFOUserService;
import com.calabrio.util.SessionProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private WFOUserService userService;

    @RequestMapping(name = "auth", value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<String> auth(HttpServletRequest rq) {
        try {
            AuthRequest auth = fromJson(requestBody(rq), AuthRequest.class);
            log.debug(String.format("Auth parsed as: %s", auth));

            // Set our Tenant DB
            rq.getSession().setAttribute(SessionProperties.WFO_DB, auth.getTenantDb());

            // Query the WFOUserService to retrieve the WFOUser associated with
            // that username and password that we got from the AuthRequest.
            WFOUser authUser = userService.authenticate(auth);
            if(authUser == null) {
                return errorResponse("Error authenticating user credentials", 400);
            }

            String json = toJson(authUser);
            rq.getSession().setAttribute(SessionProperties.WFO_USER, json);
            return ResponseEntity.ok(json);
        } catch (ParseException | AuthenticationException e) {
            log.debug(e.getMessage(), e);
            return errorResponse(e.getMessage(), 400);
        }
    }
}
