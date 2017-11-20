package com.calabrio.dao.impl.tenant;

import com.calabrio.dao.AbstractDao;
import com.calabrio.dao.TenantDao;
import com.calabrio.model.tenant.Tenant;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@Transactional
public class TenantDaoImpl extends AbstractDao implements TenantDao {
    private static final Logger log = Logger.getLogger(TenantDaoImpl.class);

    @Override
    public List<Tenant> getAll() {
        log.debug("GetAll TenantDao");
        return getAllOfType(getSession(), Tenant.class);
    }
}
