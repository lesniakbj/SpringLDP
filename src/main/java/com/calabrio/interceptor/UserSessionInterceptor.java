package com.calabrio.interceptor;

import com.calabrio.model.ErrorMessage;
import com.calabrio.model.user.WFOPerson;
import com.calabrio.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserSessionInterceptor extends AbstractInterceptor  {
    private static final Logger log = Logger.getLogger(UserSessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.trace("PreHandle");

        // Ensure that we have a logged in user.
        WFOPerson user = getLoggedInUser(request);
        if(user == null) {
            ErrorMessage msg = new ErrorMessage("Must have a logged in user!");
            response.getWriter().write(JsonUtil.toJson(msg));
            return false;
        }

        // Otherwise we are good to go
        return true;
    }
}
