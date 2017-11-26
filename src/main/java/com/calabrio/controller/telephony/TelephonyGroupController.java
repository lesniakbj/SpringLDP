package com.calabrio.controller.telephony;

import com.calabrio.controller.AbstractController;
import com.calabrio.model.telephony.TelephonyGroup;
import com.calabrio.service.impl.telephony.TelephonyGroupService;
import com.calabrio.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TelephonyGroupController extends AbstractController {
    private static final Logger log = Logger.getLogger(TelephonyGroupController.class);

    @Autowired
    private TelephonyGroupService telephonyGroupService;

    @RequestMapping(name = "telephony", value = "/telephony", method = RequestMethod.GET)
    @PreAuthorize("hasPermission('VIEW_TELEPHONY')")
    public ResponseEntity<String> telephony(HttpServletRequest rq) {
        log.debug("TelephonyGroupController ListAll");
        String json = JsonUtil.toJson(telephonyGroupService.getAllTelephonyGroups());
        clearSession(rq);
        return ResponseEntity.ok(json);
    }

    @RequestMapping(name = "telephonyAdd", value = "/telephony", method = RequestMethod.POST)
    @PreAuthorize("(hasPermission('VIEW_TELEPHONY') AND hasPermission('ADMIN_TELEPHONY')) OR isServiceUser()")
    public ResponseEntity<String> telephonyAdd(HttpServletRequest rq, @RequestBody String requestJson) {
        log.debug("TelephonyGroupController Add");
        try {
            TelephonyGroup tg = JsonUtil.fromJson(requestBody(rq, requestJson), TelephonyGroup.class);
            log.debug(String.format("Telephony group parsed as: %s", tg));
            tg = telephonyGroupService.addTelephonyGroup(tg);
            return ResponseEntity.ok(JsonUtil.toJson(tg));
        } catch (Exception e) {
            log.debug("Error trying to parse Tenant JSON", e);
            return errorResponse(e.getMessage(), 400);
        }
    }
}
