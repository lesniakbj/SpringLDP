package com.calabrio.interceptor;

import com.calabrio.model.user.WFOPerson;
import com.calabrio.util.JsonUtil;
import com.calabrio.util.SessionProperties;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

public abstract class AbstractInterceptor implements HandlerInterceptor {
    private static final Logger log = Logger.getLogger(AbstractInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.trace("PostHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.trace("AfterCompletion");
    }

    static WFOPerson getLoggedInUser(HttpServletRequest rq) {
        try {
            return JsonUtil.fromJson((String)rq.getSession().getAttribute(SessionProperties.WFO_PERSON), WFOPerson.class);
        } catch (ParseException e) {
            log.debug("Unable to parse user from session.");
            return null;
        }
    }
}
