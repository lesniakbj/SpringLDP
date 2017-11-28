package com.calabrio.repository.telephony;

import com.calabrio.model.telephony.TelephonyGroup;
import com.calabrio.repository.AbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TelephonyGroupRepositoryImpl extends AbstractRepository implements TelephonyGroupRepository {
    @Override
    public List<TelephonyGroup> getAll() {
        return super.getAllOfType(getSession(), TelephonyGroup.class);
    }

    @Override
    public TelephonyGroup add(TelephonyGroup tg) {
        return super.add(tg);
    }
}
