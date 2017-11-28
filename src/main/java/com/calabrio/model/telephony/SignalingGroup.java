package com.calabrio.model.telephony;

import com.calabrio.model.server.Server;
import com.calabrio.model.server.SignalingGroupServer;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
 * Created by Brendan.Lesniak on 11/20/2017.
 */
@Entity
@Table(name = "SignalingGroup")
public class SignalingGroup {
    @Id
    @Column(name = "id", nullable =  false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Integer id;

    @Column(name =  "name", nullable = false)
    @Expose
    private String name;

    @ManyToOne
    @JoinColumn(name = "telephonyGroupId")
    @Expose(serialize = false)
    private TelephonyGroup telephonyGroup;

    @OneToOne
    @JoinColumn(name = "primarySignalingId")
    @Expose
    private Server primary;

    @OneToOne
    @JoinColumn(name = "backupSignalingId")
    @Expose
    private Server backup;

    @OneToMany(mappedBy = "signalingGroup", fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SELECT)
    @Expose
    private List<SignalingGroupServer> servers;

    @OneToMany(mappedBy = "signalingGroup", fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SELECT)
    @Expose
    private List<RecordingGroup> recordingGroups;

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

    public List<SignalingGroupServer> getServers() {
        return servers;
    }

    public void setServers(List<SignalingGroupServer> servers) {
        this.servers = servers;
    }

    public List<RecordingGroup> getRecordingGroups() {
        return recordingGroups;
    }

    public void setRecordingGroups(List<RecordingGroup> recordingGroups) {
        this.recordingGroups = recordingGroups;
    }
}
