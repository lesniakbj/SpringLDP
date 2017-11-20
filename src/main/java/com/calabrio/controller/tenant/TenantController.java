package com.calabrio.controller.tenant;

import com.calabrio.controller.AbstractController;
import com.calabrio.model.tenant.Tenant;
import com.calabrio.service.TenantService;
import com.calabrio.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController
public class TenantController extends AbstractController {
    private static final Logger log = Logger.getLogger(TenantController.class);

    @Autowired
    private TenantService tenantService;

    @RequestMapping(name = "tenant", value = "/admin/tenant", method = RequestMethod.GET)
    public ResponseEntity<String> tenant(HttpServletRequest rq) {
        String json = JsonUtil.toJson(tenantService.getAllTenants());
        clearSession(rq);
        return ResponseEntity.ok(json);
    }

    @RequestMapping(name = "addTenant", value = "/admin/tenant/add", method = RequestMethod.POST)
    public ResponseEntity<String> addTenant(HttpServletRequest rq) {
        try {
            Tenant tenant = JsonUtil.fromJson(requestBody(rq), Tenant.class);
            log.debug(String.format("Tenant parsed as: %s", tenant));
            tenant = tenantService.addTenant(tenant);
            return ResponseEntity.ok(JsonUtil.toJson(tenant));
        } catch (ParseException e) {
            log.debug("Error trying to parse Tenant JSON", e);
            return errorResponse(e.getMessage(), 400);
        }
    }

    @RequestMapping(name = "removeTenant", value = "/admin/tenant/remove", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeTenant(HttpServletRequest rq) {
        try {
            Tenant tenant = JsonUtil.fromJson(requestBody(rq), Tenant.class);
            log.debug(String.format("Tenant parsed as: %s", tenant));
            tenantService.removeTenant(tenant);
            return ResponseEntity.ok("Successfully Removed!");
        } catch (ParseException e) {
            log.debug("Error trying to parse Tenant JSON", e);
            return errorResponse(e.getMessage(), 400);
        }
    }

    @RequestMapping(name = "tenantProperties", value = "/tenant/properties", method = RequestMethod.GET)
    public ResponseEntity<String> tenantProperties(HttpServletRequest rq) {
        String json = JsonUtil.toJson(tenantService.getAllTenantProperties());
        clearSession(rq);
        return ResponseEntity.ok(json);
    }
}
