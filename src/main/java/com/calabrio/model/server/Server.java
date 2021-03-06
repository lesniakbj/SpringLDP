package com.calabrio.model.server;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

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
 * Created by Brendan.Lesniak on 11/20/2017.
 */
@Entity
@Table(name = "Server", indexes = {})
@Inheritance(strategy = InheritanceType.JOINED)
public class Server {
    @Id
    @Column(name = "id", nullable = false)
    @Expose
    private Integer id;

    @Column(name = "serverType", nullable = false)
    @Enumerated(EnumType.STRING)
    @Expose
    private ServerType serverType;

    @Column(name = "ipHostName", nullable = false)
    @Expose
    private String ipHostName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public String getIpHostName() {
        return ipHostName;
    }

    public void setIpHostName(String ipHostName) {
        this.ipHostName = ipHostName;
    }
}
