package com.calabrio.dao;

import com.calabrio.model.WFOUser;
import org.hibernate.Session;

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
public interface WFOUserDao {
    WFOUser findByEmail(String email);

    boolean authenticate(WFOUser user, String password);
}
