package com.calabrio.service.person;

import com.calabrio.model.auth.AuthRequest;
import com.calabrio.model.user.WFOPerson;

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
public interface WFOPersonService {
    WFOPerson authenticate(AuthRequest auth) throws AuthenticationException;
}
