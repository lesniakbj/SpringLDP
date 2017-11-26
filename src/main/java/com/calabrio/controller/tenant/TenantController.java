package com.calabrio.controller.tenant;

import com.calabrio.controller.AbstractController;
import com.calabrio.service.impl.tenant.TenantService;
import com.calabrio.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TenantController extends AbstractController {
    private static final Logger log = Logger.getLogger(TenantController.class);

    @Autowired
    private TenantService tenantService;

    @RequestMapping(name = "tenantProperties", value = "/tenant/properties", method = RequestMethod.GET)
    public ResponseEntity<String> tenantProperties(HttpServletRequest rq) {
        String json = JsonUtil.toJson(tenantService.getAllTenantProperties());
        clearSession(rq);
        return ResponseEntity.ok(json);
    }
}
