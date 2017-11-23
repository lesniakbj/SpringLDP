package com.calabrio.security;

import org.apache.log4j.Logger;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class WFOPermissionExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    private static final Logger log = Logger.getLogger(WFOPermissionExpressionRoot.class);

    public WFOPermissionExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    @Override
    public void setFilterObject(Object filterObject) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }
}
