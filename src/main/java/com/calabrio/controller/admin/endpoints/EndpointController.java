package com.calabrio.controller.admin.endpoints;

import com.calabrio.controller.AbstractController;
import com.calabrio.dto.message.SuccessMessage;
import com.calabrio.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EndpointController extends AbstractController {
    private static final Logger log = Logger.getLogger(EndpointController.class);

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @RequestMapping(name = "endpoints", value = "/admin/endpoints", method = RequestMethod.GET)
    @PreAuthorize("hasPermission('ADMIN_SYSTEM')")
    public ResponseEntity<String> endpoints(HttpServletRequest rq) {
        String endpoints = handlerMapping.getHandlerMethods().toString();
        log.debug(String.format("Request for Endpoints, grabbing all mapped endpoints: %s", endpoints));
        clearSession(rq);
        return ResponseEntity.ok(JsonUtil.toJson(new SuccessMessage(endpoints)));
    }
}
