package com.calabrio.configuration;

import com.calabrio.security.evaluator.UserPermissionEvaluator;
import com.calabrio.security.handler.UserAccessDeniedHandler;
import com.calabrio.security.handler.WFOPermissionExpressionHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends GlobalMethodSecurityConfiguration  {
    private static final Logger log = Logger.getLogger(WebSecurityConfig.class);

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        log.debug("Creating security expression handler.");
        WFOPermissionExpressionHandler handler = new WFOPermissionExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }

    @Configuration
    public static class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserAccessDeniedHandler userAccessDeniedHandler;

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.csrf().disable()
                .exceptionHandling()
                    .accessDeniedPage("/denied")
                    .and()
                .logout()
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .and()
                .authorizeRequests()
                    .antMatchers("/auth").anonymous()
                    .antMatchers("/demo.html").permitAll()
                    .anyRequest().authenticated();
        }
    }
}