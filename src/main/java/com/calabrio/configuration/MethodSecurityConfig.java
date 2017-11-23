
package com.calabrio.configuration;

import com.calabrio.security.WFOPermissionExpressionHandler;
import com.calabrio.security.evaluator.UserPermissionEvaluator;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration  {
    private static final Logger log = Logger.getLogger(MethodSecurityConfig.class);

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        log.debug("Creating security expression handler.");
        WFOPermissionExpressionHandler handler = new WFOPermissionExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }

    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("/auth").permitAll()
                    .anyRequest().permitAll();

        }
    }
}