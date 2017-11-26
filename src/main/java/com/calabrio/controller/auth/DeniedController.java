package com.calabrio.controller.auth;

import com.calabrio.controller.AbstractController;
import com.calabrio.dto.message.ErrorMessage;
import com.calabrio.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DeniedController extends AbstractController {
    private static final Logger log = Logger.getLogger(DeniedController.class);

    @RequestMapping(name = "denied", value = "/denied", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<String> denied(HttpServletRequest rq) {
        return ResponseEntity.status(401).body(JsonUtil.toJson(new ErrorMessage("Access is Denied!")));
    }
}
