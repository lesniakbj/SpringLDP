package com.calabrio.controller;

import com.calabrio.model.user.WFOUser;
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
        return ResponseEntity.status(code).body(toJson(err));
    }

    static WFOUser getUser(HttpServletRequest rq) {
        try {
            return fromJson((String)rq.getSession().getAttribute("WFOUser"), WFOUser.class);
        } catch (ParseException e) {
            log.debug("Unable to parse user from session.");
            return null;
        }
    }

    static String requestBody(HttpServletRequest rq) {
        try {
            return rq.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            log.debug("Error parsing request body!");
        }

        return "";
    }

    static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    static <T> T fromJson(String json, Class<T> clazz) throws ParseException {
        Gson gson = new Gson();
        T response = gson.fromJson(json, clazz);

        if(response == null) {
            throw new ParseException("Error parsing JSON!", -1);
        }

        return response;
    }


    private static class ErrorMessage {
        private String error;

        ErrorMessage(String error) {
            this.error = error;
        }
    }
}
