package com.calabrio.security.evaluator;

import com.calabrio.util.ObjectsUtil;
import org.apache.log4j.Logger;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class UserPermissionEvaluator implements PermissionEvaluator {
    private static final Logger log = Logger.getLogger(UserPermissionEvaluator.class);
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.debug(String.format("Checking the following auth: [%s/%s/%s]", authentication, targetDomainObject, permission));
        if(ObjectsUtil.allNull(authentication, targetDomainObject) || !(permission instanceof String)) {
            return false;
        }

        String tgt = targetDomainObject.getClass().getSimpleName().toUpperCase();
        return hasPrivilege(authentication, tgt, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.debug(String.format("Checking the following auth: [%s/%s/%s]", authentication, targetType, permission));
        if(ObjectsUtil.allNull(authentication, targetType) || !(permission instanceof String)) {
            return false;
        }

        return hasPrivilege(authentication, targetType.toUpperCase(), permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, String target, String permission) {
        return auth.getAuthorities().stream().anyMatch((ga) -> ga.getAuthority().startsWith(target) && ga.getAuthority().contains(permission));
    }
}
