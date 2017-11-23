package com.calabrio.dao.impl.telephony;

import com.calabrio.dao.AbstractDao;
import com.calabrio.model.telephony.TelephonyGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TelephonyGroupDaoImpl extends AbstractDao implements TelephonyGroupDao {
    @Override
    public List<TelephonyGroup> getAll() {
        return  super.getAllOfType(getSession(), TelephonyGroup.class);
    }
}
