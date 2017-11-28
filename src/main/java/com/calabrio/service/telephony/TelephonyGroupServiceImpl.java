package com.calabrio.service.telephony;

import com.calabrio.model.telephony.TelephonyGroup;
import com.calabrio.repository.telephony.TelephonyGroupRepository;
import com.calabrio.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelephonyGroupServiceImpl extends AbstractService implements TelephonyGroupService {

    @Autowired
    private TelephonyGroupRepository telephonyGroupRepository;

    @Override
    public List<TelephonyGroup> getAllTelephonyGroups() {
        return telephonyGroupRepository.getAll();
    }

    @Override
    public TelephonyGroup addTelephonyGroup(TelephonyGroup tg) {
        return telephonyGroupRepository.add(tg);
    }
}
