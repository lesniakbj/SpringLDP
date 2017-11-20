package com.calabrio.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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
public class AbstractDao {
    private static final Logger log = Logger.getLogger(AbstractDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    public <T> List<T> getAllOfType(Session sess, Class<T> type) {
        CriteriaQuery<T> criteria = sess.getCriteriaBuilder().createQuery(type);
        Root<T> root = criteria.from(type);
        criteria.select(root);
        return sess.createQuery(criteria).list();
    }

    public Object querySingleResult(Query query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            log.debug("No results when attempting query!");
        }

        return null;
    }

    public Query getQuery(String query) {
        return getSession().createQuery(query);
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
