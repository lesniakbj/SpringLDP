package com.calabrio.model.server;

import com.calabrio.model.telephony.SignalingGroup;
import com.calabrio.model.telephony.TelephonyGroup;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterJoinTable;
import org.hibernate.annotations.ParamDef;

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
@Table(name = "TelephonyGroupServer")
@PrimaryKeyJoinColumn(name = "serverFk", referencedColumnName = "id")
public class TelephonyGroupServer extends Server {
    @ManyToOne
    @JoinColumn(name = "telephonyGroupId")
    @Expose(serialize = false)
    private TelephonyGroup telephonyGroup;

    public TelephonyGroup getTelephonyGroup() {
        return telephonyGroup;
    }

    public void setTelephonyGroup(TelephonyGroup telephonyGroup) {
        this.telephonyGroup = telephonyGroup;
    }
}