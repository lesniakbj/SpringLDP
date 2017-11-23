package com.calabrio.controller.telephony;

import com.calabrio.controller.AbstractController;
import com.calabrio.service.TelephonyGroupService;
import com.calabrio.util.JsonUtil;
import com.calabrio.util.SpringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TelephonyGroupController extends AbstractController {
    private static final Logger log = Logger.getLogger(TelephonyGroupController.class);

    @Autowired
    private TelephonyGroupService telephonyGroupService;

    @Autowired
    private SpringUtil springUtil;

    @RequestMapping(name = "telephony", value = "/telephony", method = RequestMethod.GET)
    public ResponseEntity<String> index(HttpServletRequest rq) {
        return ResponseEntity.ok(JsonUtil.toJson(springUtil.listAllBeans()));
    }
}
