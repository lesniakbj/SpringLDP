package com.calabrio.dao.impl.person;

import com.calabrio.dao.AbstractDao;
import com.calabrio.model.user.WFOPerson;
import org.apache.log4j.Logger;
import org.hibernate.query.Query;
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
public class WFOPersonDaoImpl extends AbstractDao implements WFOPersonDao {
    private static final Logger log = Logger.getLogger(WFOPersonDaoImpl.class);

    public WFOPerson findByEmail(String email) {
        log.debug("Finding user by email.");
        Query query = super.getQuery("FROM WFOPerson WHERE email = :email").setParameter("email", email);
        return super.querySingleResult(query);
    }

    public boolean authenticate(WFOPerson user, String password) {
        log.debug("Authenticating user.");

        if(user == null) {
            return false;
        }

        Query query = super.getQuery("FROM WFOPerson WHERE password = :password").setParameter("password", password);
        WFOPerson pwUser =  super.querySingleResult(query);

        return pwUser != null && pwUser.equals(user);
    }
}
