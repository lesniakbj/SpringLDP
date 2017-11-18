package com.calabrio.provider;

import com.calabrio.model.WFOUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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
@Component
public class WFOUserDetailsProvider {
    @Bean
    @Scope("prototype")
    public WFOUser wfoUserDetails() {
        return (WFOUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
