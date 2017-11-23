package com.calabrio.model.telephony;

import com.calabrio.model.server.Server;

import javax.persistence.*;

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
@Table(name = "SignalingGroup")
public class SignalingGroup {
    @Id
    @Column(name = "id", nullable =  false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name =  "name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name =  "telephonyGroupId")
    private TelephonyGroup telephonyGroup;

    @OneToOne
    @JoinColumn(name = "primarySignalingId")
    private Server primary;

    @OneToOne
    @JoinColumn(name = "backupSignalingId")
    private Server backup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TelephonyGroup getTelephonyGroup() {
        return telephonyGroup;
    }

    public void setTelephonyGroup(TelephonyGroup telephonyGroup) {
        this.telephonyGroup = telephonyGroup;
    }

    public Server getPrimary() {
        return primary;
    }

    public void setPrimary(Server primary) {
        this.primary = primary;
    }

    public Server getBackup() {
        return backup;
    }

    public void setBackup(Server backup) {
        this.backup = backup;
    }
}
