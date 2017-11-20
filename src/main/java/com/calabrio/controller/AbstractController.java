package com.calabrio.controller;

import com.calabrio.model.ErrorMessage;
import com.calabrio.model.user.WFOPerson;
import com.calabrio.util.DbProperties;
import com.calabrio.util.JsonUtil;
import com.calabrio.util.SessionProperties;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.stream.Collectors;

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
abstract class AbstractController {
    private static final Logger log = Logger.getLogger(AbstractController.class);

    static ResponseEntity<String> errorResponse(String msg, Integer code) {
        ErrorMessage err = new ErrorMessage(msg);
        return ResponseEntity.status(code).body(JsonUtil.toJson(err));
    }

    static String requestBody(HttpServletRequest rq) {
        try {
            return rq.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            log.debug("Error parsing request body!");
        }

        return "";
    }

    static void setAttribute(HttpServletRequest rq, String property, Object value) {
        rq.getSession().setAttribute(property, value);
    }

    static void clearSession(HttpServletRequest rq) {
        log.debug("Clearing session attributes");
        rq.getSession().invalidate();
    }
}
