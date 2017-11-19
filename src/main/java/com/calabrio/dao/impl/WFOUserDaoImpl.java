package com.calabrio.dao.impl;

import com.calabrio.dao.AbstractDao;
import com.calabrio.dao.WFOUserDao;
import com.calabrio.model.user.WFOUser;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
@Repository
@Transactional
public class WFOUserDaoImpl extends AbstractDao implements WFOUserDao {
    private static final Logger log = Logger.getLogger(WFOUserDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public WFOUser findByEmail(String email) {
        log.debug("Finding user by email.");

        Query query = getSession().createQuery("FROM WFOUser WHERE email = :email");
        query.setParameter("email", email);
        return (WFOUser) querySingleResult(query);
    }

    public boolean authenticate(WFOUser user, String password) {
        log.debug("Authenticating user.");

        if(user == null) {
            return false;
        }

        Query query = getSession().createQuery("FROM WFOUser WHERE password = :password");
        query.setParameter("password", password);
        WFOUser pwUser = (WFOUser) querySingleResult(query);

        return pwUser != null && pwUser.equals(user);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
