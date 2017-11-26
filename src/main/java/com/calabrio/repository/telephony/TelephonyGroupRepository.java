package com.calabrio.repository.telephony;

import com.calabrio.model.telephony.TelephonyGroup;

import java.util.List;

public interface TelephonyGroupRepository {
    public List<TelephonyGroup> getAll();

    TelephonyGroup add(TelephonyGroup tg);
}
