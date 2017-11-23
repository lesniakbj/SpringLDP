package com.calabrio.model.server;

import com.calabrio.model.telephony.SignalingGroup;
import com.calabrio.model.telephony.TelephonyGroup;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "TelephonyGroupServer")
public class TelephonyGroupServer extends Server {
    @OneToOne
    @JoinColumn(name = "telephonyGroupId")
    private TelephonyGroup telephonyGroup;

    @OneToOne
    @JoinColumn(name = "signalingGroupId")
    private SignalingGroup signalingGroup;
}