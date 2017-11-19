package com.calabrio.dao.impl.tenant;

import com.calabrio.dao.AbstractDao;
import com.calabrio.dao.TenantDao;
import com.calabrio.dao.WFOPersonDao;
import com.calabrio.model.tenant.Tenant;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class TenantDaoImpl extends AbstractDao implements TenantDao {
    private static final Logger log = Logger.getLogger(TenantDaoImpl.class);

    @Override
    public List<Tenant> getAll() {
        log.debug("GetAll TenantDao");

        Query q = getSession().createQuery("FROM Tenant");
        return q.getResultList();
    }
}
