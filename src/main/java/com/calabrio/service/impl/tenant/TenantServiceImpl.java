package com.calabrio.service.impl.tenant;

import com.calabrio.dao.TenantDao;
import com.calabrio.model.tenant.Tenant;
import com.calabrio.service.AbstractService;
import com.calabrio.service.TenantService;
import com.calabrio.util.DbProperties;
import org.apache.log4j.Logger;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl extends AbstractService implements TenantService {
    private static final Logger log = Logger.getLogger(TenantServiceImpl.class);

    @Autowired
    TenantDao tenantDao;

    @Override
    public List<Tenant> getAllTenants() {
        log.debug("GetAllTenants TenantService");
        setTenantId(DbProperties.DEFAULT_TENANT);
        return tenantDao.getAll();
    }
}
