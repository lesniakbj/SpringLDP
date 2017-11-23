package com.calabrio.dao.impl.telephony;

import com.calabrio.dao.AbstractDao;
import com.calabrio.model.telephony.TelephonyGroup;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TelephonyGroupDaoImpl extends AbstractDao implements TelephonyGroupDao {
    @Override
    public List<TelephonyGroup> getAll() {
        return  super.getAllOfType(getSession(), TelephonyGroup.class);
    }
}
