package com.calabrio.service.impl;

import com.calabrio.dao.TelephonyGroupDao;
import com.calabrio.model.telephony.TelephonyGroup;
import com.calabrio.service.AbstractService;
import com.calabrio.service.TelephonyGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelephonyGroupServiceImpl extends AbstractService implements TelephonyGroupService {

    @Autowired
    private TelephonyGroupDao telephonyGroupDao;

    @Override
    public List<TelephonyGroup> getAllTelephonyGroups() {
        return telephonyGroupDao.getAll();
    }
}
