package com.calabrio.service.impl;

import com.calabrio.dao.WFOUserDao;
import com.calabrio.model.AuthRequest;
import com.calabrio.model.WFOUser;
import com.calabrio.service.WFOUserService;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

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
@Service
public class WFOUserServiceImpl implements WFOUserService {
    private static final Logger log = Logger.getLogger(WFOUserServiceImpl.class);

    @Autowired
    WFOUserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Override public WFOUser authenticate(AuthRequest auth) throws AuthenticationException {
        log.debug(String.format("Authenticating user with auth request: %s", auth));

        WFOUser user = userDao.findByEmail(auth.getEmail());
        if(user == null) {
            throw new AuthenticationException("Unable to find user!");
        }

        if(!userDao.authenticate(user, auth.getPassword())) {
            throw new AuthenticationException("Unable to authenticate user!");
        }

        return user;
    }
}
