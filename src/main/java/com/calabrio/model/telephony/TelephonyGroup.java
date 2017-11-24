package com.calabrio.model.telephony;

import com.calabrio.model.server.Server;
import com.calabrio.model.server.TelephonyGroupServer;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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
@Table(name = "TelephonyGroup")
public class TelephonyGroup {
    @Id
    @Column(name =  "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Integer id;

    @Column(name =  "name", nullable = false)
    @Expose
    private String name;

    @Column(name =  "telephonyGroupTypeId", nullable = false)
    @Enumerated(EnumType.STRING)
    @Expose
    private TelephonyGroupType telephonyGroupType;

    @Column(name =  "inclusionList")
    @Expose
    private String inclusionList;

    @OneToOne
    @JoinColumn(name = "acdServerId", nullable = false)
    @Expose
    private Server acdServer;

    @OneToMany(mappedBy = "telephonyGroup", fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SELECT)
    @Expose
    private List<TelephonyGroupServer> servers;

    @OneToMany(mappedBy = "telephonyGroup", fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SELECT)
    @Expose
    private List<SignalingGroup> signalingGroups;

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

    public TelephonyGroupType getTelephonyGroupType() {
        return telephonyGroupType;
    }

    public void setTelephonyGroupType(TelephonyGroupType telephonyGroupType) {
        this.telephonyGroupType = telephonyGroupType;
    }

    public String getInclusionList() {
        return inclusionList;
    }

    public void setInclusionList(String inclusionList) {
        this.inclusionList = inclusionList;
    }

    public Server getAcdServer() {
        return acdServer;
    }

    public void setAcdServer(Server acdServer) {
        this.acdServer = acdServer;
    }

    public List<TelephonyGroupServer> getServers() {
        return servers;
    }

    public void setServers(List<TelephonyGroupServer> servers) {
        this.servers = servers;
    }

    public List<SignalingGroup> getSignalingGroups() {
        return signalingGroups;
    }

    public void setSignalingGroups(List<SignalingGroup> signalingGroups) {
        this.signalingGroups = signalingGroups;
    }
}
