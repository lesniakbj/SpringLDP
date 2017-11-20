package com.calabrio.service;

import com.calabrio.util.properties.SessionProperties;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class AbstractService {
    public void setTenantId(Integer tenantId) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        attr.getRequest().getSession().setAttribute(SessionProperties.WFO_TENANT, tenantId);
    }
}
