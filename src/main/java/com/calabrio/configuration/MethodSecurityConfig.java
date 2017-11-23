
package com.calabrio.configuration;

import com.calabrio.evaluator.UserPermissionEvaluator;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    private static final Logger log = Logger.getLogger(MethodSecurityConfig.class);

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        log.debug("Creating security expression handler.");
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }
}