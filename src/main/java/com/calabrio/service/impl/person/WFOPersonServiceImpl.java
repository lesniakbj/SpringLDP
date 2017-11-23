package com.calabrio.service.impl.person;

import com.calabrio.dao.impl.person.WFOPersonDao;
import com.calabrio.dao.impl.tenant.TenantDao;
import com.calabrio.model.auth.AuthRequest;
import com.calabrio.model.user.WFOPerson;
import com.calabrio.service.AbstractService;
import org.apache.log4j.Logger;
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
public class WFOPersonServiceImpl extends AbstractService implements WFOPersonService {
    private static final Logger log = Logger.getLogger(WFOPersonServiceImpl.class);

    @Autowired
    private WFOPersonDao userDao;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Override public WFOPerson authenticate(AuthRequest auth) throws AuthenticationException {
        log.debug(String.format("Authenticating user with auth request: %s", auth));
        setTenantId(auth.getTenantId());

        WFOPerson user = userDao.findByEmail(auth.getEmail());
        if(user == null) {
            throw new AuthenticationException("Unable to find user!");
        }

        log.debug(String.format("Attempting to auth user: %s", user));
        if(!userDao.authenticate(user, auth.getPassword())) {
            throw new AuthenticationException("Unable to authenticate user!");
        }

        return user;
    }
}
