package com.calabrio.controller.admin.tenant;

import com.calabrio.controller.AbstractController;
import com.calabrio.model.tenant.Tenant;
import com.calabrio.service.admin.AdminTenantService;
import com.calabrio.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SystemAdminController extends AbstractController {
    private static final Logger log = Logger.getLogger(SystemAdminController.class);

    @Autowired
    private AdminTenantService tenantService;

    @RequestMapping(name = "tenant", value = "/admin/tenant", method = RequestMethod.GET)
    public ResponseEntity<String> tenant(HttpServletRequest rq) {
        log.debug("TenantAdminController Getting all Tenants");
        String json = JsonUtil.toJson(tenantService.getAllTenants());
        return ResponseEntity.ok(json);
    }

    @RequestMapping(name = "tenantById", value = "/admin/tenant/{tenantId}", method = RequestMethod.GET)
    public ResponseEntity<String> tenantById(HttpServletRequest rq, @RequestParam(value = "tenantId")Integer tenantId) {
        log.debug(String.format("TenantAdminController Getting Tenant by Id: %s", tenantId));
        String json = JsonUtil.toJson(tenantService.getTenantById(tenantId));
        return ResponseEntity.ok(json);
    }

    @RequestMapping(name = "addTenant", value = "/admin/tenant/add", method = RequestMethod.POST)
    public ResponseEntity<String> addTenant(HttpServletRequest rq, @RequestBody String requestJson) {
        log.debug("TenantAdminController Adding a New Tenant");
        try {
            Tenant tenant = JsonUtil.fromJson(requestBody(rq, requestJson), Tenant.class);
            log.debug(String.format("Tenant parsed as: %s", tenant));
            tenant = tenantService.addTenant(tenant);
            return ResponseEntity.ok(JsonUtil.toJson(tenant));
        } catch (Exception e) {
            log.debug("Error trying to parse Tenant JSON", e);
            return errorResponse(e.getMessage(), 400);
        }
    }

    @RequestMapping(name = "removeTenant", value = "/admin/tenant/remove", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeTenant(HttpServletRequest rq, @RequestBody String requestJson) {
        log.debug("TenantAdminController Deleting a New Tenant");
        try {
            Tenant tenant = JsonUtil.fromJson(requestBody(rq, requestJson), Tenant.class);
            log.debug(String.format("Tenant parsed as: %s", tenant));
            tenantService.removeTenant(tenant);
            return ResponseEntity.ok("Successfully Removed!");
        } catch (Exception e) {
            log.debug("Error trying to parse Tenant JSON", e);
            return errorResponse(e.getMessage(), 400);
        }
    }
}
