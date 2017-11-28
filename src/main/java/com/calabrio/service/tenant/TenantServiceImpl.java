package com.calabrio.service.tenant;

import com.calabrio.model.tenant.TenantProperty;
import com.calabrio.repository.tenant.TenantPropertyRepository;
import com.calabrio.service.AbstractService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl extends AbstractService implements TenantService {
    private static final Logger log = Logger.getLogger(TenantServiceImpl.class);

    @Autowired
    private TenantPropertyRepository tenantPropertyRepository;

    @Override
    @PreAuthorize("hasPermission('ADMIN_TENANT')")
    public List<TenantProperty> getAllTenantProperties() {
        log.debug("GetAllTenantProperties TenantService");
        return tenantPropertyRepository.findAll();
    }
}
