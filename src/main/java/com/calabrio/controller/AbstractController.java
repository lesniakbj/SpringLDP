package com.calabrio.controller;

import com.calabrio.dto.message.ErrorMessage;
import com.calabrio.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
public abstract class AbstractController {
    private static final Logger log = Logger.getLogger(AbstractController.class);

    public static ResponseEntity<String> errorResponse(String msg, Integer code) {
        ErrorMessage err = new ErrorMessage(msg);
        return ResponseEntity.status(code).body(JsonUtil.toJson(err));
    }

    public static String requestBody(HttpServletRequest rq, String requestBody) {
        if(isAjax(rq)) {
            log.debug("Got an AJAX Request!");
            try {
                String str = URLDecoder.decode(requestBody, "UTF-8");
                if(str.endsWith("=")) {
                    str = str.substring(0, str.length() - 1);
                }
                return str;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return requestBody;
    }

    public static void setAttribute(HttpServletRequest rq, String property, Object value) {
        rq.getSession().setAttribute(property, value);
    }

    public static void clearSession(HttpServletRequest rq) {
        log.debug("Clearing session attributes");
        rq.getSession().invalidate();
    }

    public static boolean isAjax(HttpServletRequest rq) {
        String requestedWithHeader = rq.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }
}
