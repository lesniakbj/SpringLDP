package com.calabrio.interceptor;

import com.calabrio.model.generic.ErrorMessage;
import com.calabrio.model.user.WFOPerson;
import com.calabrio.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserSessionInterceptor extends AbstractInterceptor  {
    private static final Logger log = Logger.getLogger(UserSessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("PreHandle");

        // Ensure that we have a logged in user, excluded paths can be set
        // in config.WebMvcConfig.EXCLUDE_USER_PATHS
        WFOPerson user = getLoggedInUser(request);
        log.debug(String.format("Request Path: %s", request.getRequestURL()));
        if(user == null && !request.getContextPath().equals("/auth")) {
            ErrorMessage msg = new ErrorMessage("Must have a logged in user!");
            response.getWriter().write(JsonUtil.toJson(msg));
            return false;
        }

        // Otherwise we are good to go
        return true;
    }
}
