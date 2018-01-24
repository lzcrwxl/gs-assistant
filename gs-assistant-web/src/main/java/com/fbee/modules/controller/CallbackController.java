package com.fbee.modules.controller;


import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.service.CallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付/充值回调
 */
@RestController
@RequestMapping("/api/callback")
public class CallbackController {


    private static final Logger log = LoggerFactory.getLogger("callbackLogger");

    @Autowired
    CallbackService callbackServiceImpl;

    @Guest
    @RequestMapping(value = "/swiftpass", method = RequestMethod.POST)
    @ResponseBody
    public String swiftpassCallback(HttpServletRequest req){
        log.info("===[swiftpass callback ]===");
        Object obj = null;
        try {
            obj = callbackServiceImpl.handleService(req);
        } catch (Exception e) {
            log.info("callback error : "+e.getMessage());
            return "fail";
        }
        return obj == null ? "fail" : obj.toString();
    }

}
