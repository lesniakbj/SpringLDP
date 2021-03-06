package com.calabrio.repository;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
public class AbstractRepository {
    private static final Logger log = Logger.getLogger(AbstractRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    public <T> List<T> getAllOfType(Session sess, Class<T> type) {
        log.debug(String.format("Getting all of type: %s", type));
        CriteriaQuery<T> criteria = sess.getCriteriaBuilder().createQuery(type);
        Root<T> root = criteria.from(type);
        criteria.select(root);
        return sess.createQuery(criteria).list();
    }
    public <T> Query<T> getQuery(String query, Class<T> type) {
        return getSession().createQuery(query, type);
    }
    public <T> T querySingleResult(Query<T> query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            log.debug("No results when attempting query!");
        }

        return null;
    }
    public <T> List<T> queryListResult(Query<T> query) {
        try {
            return query.list();
        } catch (NoResultException ex) {
            log.debug("No results when attempting query!");
        }

        return null;
    }

    public <T> T add(T type) {
        log.debug("AddingTenant TenantDao");
        getSession().save(type);
        getSession().flush();
        getSession().clear();
        return type;
    }
    public <T> List<T> batchAdd(List<T> types) {
        log.debug("AddingTenantBatch TenantDao");
        Session session = getSession();
        Transaction tx = session.getTransaction();

        int count = 0;
        for(T t : types) {
            session.save(t);
            if(count % 20 == 0) {
                session.flush();
                session.clear();
            }
            count++;
        }

        tx.commit();
        session.close();
        return types;
    }

    public <T> T update(T type) {
        getSession().update(type);
        getSession().flush();
        getSession().clear();
        return type;
    }

    public <T> void delete(T type) {
        getSession().delete(type);
        getSession().flush();
        getSession().clear();
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}