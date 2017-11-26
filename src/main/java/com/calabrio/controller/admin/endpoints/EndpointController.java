package com.calabrio.controller.admin.endpoints;

import com.calabrio.controller.AbstractController;
import com.calabrio.dto.message.SuccessMessage;
import com.calabrio.model.auth.EndpointMapping;
import com.calabrio.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EndpointController extends AbstractController {
    private static final Logger log = Logger.getLogger(EndpointController.class);

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @RequestMapping(name = "endpoints", value = "/admin/endpoints", method = RequestMethod.GET)
    @PreAuthorize("hasPermission('ADMIN_SYSTEM')")
    public ResponseEntity<String> endpoints(HttpServletRequest rq) {
        Map<RequestMappingInfo, HandlerMethod> endpoints = handlerMapping.getHandlerMethods();
        List<EndpointMapping> mappings = new ArrayList<>();
        for(Map.Entry<RequestMappingInfo, HandlerMethod> info : endpoints.entrySet()) {
            EndpointMapping mapping = new EndpointMapping();
            mapping.setPathPattern(info.getKey().getPatternsCondition().getPatterns().toString());
            mapping.setMethod(info.getKey().getMethodsCondition().getMethods().toString());
            mappings.add(mapping);
        }
        log.debug(String.format("Request for Endpoints, grabbing all mapped endpoints: %s", mappings));
        return ResponseEntity.ok(JsonUtil.toJson(mappings));
    }
}
