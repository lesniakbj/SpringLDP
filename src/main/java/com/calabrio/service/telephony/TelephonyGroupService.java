package com.calabrio.service.telephony;

import com.calabrio.model.telephony.TelephonyGroup;

import java.util.List;

public interface TelephonyGroupService {
    public List<TelephonyGroup> getAllTelephonyGroups();

    TelephonyGroup addTelephonyGroup(TelephonyGroup tg);
}
