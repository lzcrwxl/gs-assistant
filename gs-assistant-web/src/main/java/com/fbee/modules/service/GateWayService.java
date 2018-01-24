package com.fbee.modules.service;


import com.fbee.modules.bean.payment.GatewayPrepayInfo;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by gaoyan on 04/07/2017.
 */
public interface GateWayService {

    Object prepay(GatewayPrepayInfo gw, String orderNo) throws ServletException, IOException;

    Map<String, String> query(String trade) throws ServletException, IOException ;

    Map<String, String> refundQuery() throws ServletException, IOException ;

    Map<String, String> refund() throws ServletException, IOException ;
}
