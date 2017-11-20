package com.calabrio.controller;

import com.calabrio.model.tenant.Tenant;
import com.calabrio.service.TenantService;
import com.calabrio.util.DbProperties;
import com.calabrio.util.JsonUtil;
import com.calabrio.util.SessionProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TenantController extends AbstractController {
    private static final Logger log = Logger.getLogger(TenantController.class);

    @Autowired
    private TenantService tenantService;

    @RequestMapping(name = "tenant", value = "/tenant", method = RequestMethod.GET)
    public ResponseEntity<String> tenant(HttpServletRequest rq) {
        String json = JsonUtil.toJson(tenantService.getAllTenants());
        clearSession(rq);
        return ResponseEntity.ok(json);
    }
}
