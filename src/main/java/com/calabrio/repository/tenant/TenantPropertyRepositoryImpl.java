package com.calabrio.repository.tenant;

import com.calabrio.model.tenant.TenantProperty;
import com.calabrio.repository.AbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TenantPropertyRepositoryImpl extends AbstractRepository implements TenantPropertyRepository {
    @Override
    public List<TenantProperty> findAll() {
        return null;
    }
}
