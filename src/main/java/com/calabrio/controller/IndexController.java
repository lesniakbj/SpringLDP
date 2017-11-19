package com.calabrio.controller;

import com.calabrio.model.user.WFOUser;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
public class IndexController extends AbstractController {
    private static final Logger log = Logger.getLogger(IndexController.class);

    @RequestMapping(name = "index", value = "/index", method = RequestMethod.GET)
    public ResponseEntity<String> index(HttpServletRequest rq) {
        WFOUser user = getUser(rq);
        if(user == null) {
            return errorResponse("Must have valid User!", 400);
        }

        return ResponseEntity.ok("Hello World!");
    }
}
