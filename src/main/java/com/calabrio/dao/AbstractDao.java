package com.calabrio.dao;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;

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

    public Object querySingleResult(Query query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            log.debug("No results when attempting query!");
        }

        return null;
    }
}
