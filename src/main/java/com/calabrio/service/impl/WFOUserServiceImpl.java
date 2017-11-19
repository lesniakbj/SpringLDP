package com.calabrio.service.impl;

import com.calabrio.dao.WFOUserDao;
import com.calabrio.datasource.MultiTenantConnectionProvider;
import com.calabrio.model.auth.AuthRequest;
import com.calabrio.model.user.WFOUser;
import com.calabrio.service.WFOUserService;
import com.calabrio.util.DbProperties;
import com.calabrio.util.SessionProperties;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.naming.AuthenticationException;
import java.util.Map;
import java.util.Objects;

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

        if(auth.getTenantId() == null) {
            Map<Integer, String> dbMap = MultiTenantConnectionProvider.getTenantDbMap();
            boolean hasMoreThanOneTenant = dbMap.size() > 2; // Note: There will always be a single 'Common' tenant.
            if(hasMoreThanOneTenant) {
                throw new AuthenticationException("Please specify tenant!");
            } else {
                setTenantId(dbMap, auth);
            }
        }

        WFOUser user = userDao.findByEmail(auth.getEmail());
        if(user == null) {
            throw new AuthenticationException("Unable to find user!");
        }

        if(!userDao.authenticate(user, auth.getPassword())) {
            throw new AuthenticationException("Unable to authenticate user!");
        }

        return user;
    }

    private void setTenantId(Map<Integer, String> dbMap, AuthRequest auth) {
        Integer id = DbProperties.DEFAULT_TENANT;
        for(Integer key : dbMap.keySet()) {
            if(!Objects.equals(key, DbProperties.DEFAULT_TENANT)) {
                id = key;
            }
        }
        auth.setTenantId(id);
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        attr.getRequest().getSession().setAttribute(SessionProperties.WFO_TENANT, auth.getTenantId());
    }
}
