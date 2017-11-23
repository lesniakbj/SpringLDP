package com.calabrio.model.telephony;

import com.calabrio.model.server.TelephonyGroupServer;

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
    private Integer id;

    @Column(name =  "name", nullable = false)
    private String name;

    @Column(name =  "telephonyGroupType", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TelephonyGroupType telephonyGroupType;

    @Column(name =  "inclusionList")
    private String inclusionList;

    @OneToMany
    @JoinColumn(name = "inclusionListId")
    private List<TelephonyGroupServer> servers;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "MetadataMappings")
    @MapKeyColumn(name = "metaDataKey")
    @Column(name = "metaDataValue")
    private Map<String,String> customMetadataMappings;

    //private Server acd;
}
