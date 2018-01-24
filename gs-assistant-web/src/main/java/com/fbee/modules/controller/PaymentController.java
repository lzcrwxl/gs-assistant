package com.fbee.modules.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fbee.modules.bean.UserBean;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.payment.GatewayPrepayInfo;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.PaymentForm;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.redis.consts.RedisKey;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.GateWayService;
import com.fbee.modules.service.TenantService;
import com.fbee.modules.utils.JsonUtils;

/**
 * 账户相关：充值/支付
 */
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final static Logger log = LoggerFactory.getLogger("paymentLogger");
    
    @Autowired
    CommonService commonService;

    @Autowired
    private GateWayService service;

    @Autowired
    TenantService tenantService;
    
    private JedisTemplate redis = JedisUtils.getJedisTemplate();

    /**
     * 预充值：
     * 1. 微信支付返回prepay_id
     * 2. 扫码支付返回二维码
     *
     * @return
     */
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public JsonResult recharge(@RequestBody PaymentForm paymentForm) {
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }

        String flowNo = commonService.createOrderNo("06");
        //生成payinfo no
        BigDecimal payAmount = paymentForm.getAmount();
        String transDesc = paymentForm.getRemarks();
        String payType = paymentForm.getPayType();
        String openId = null;
        if(userBean != null && userBean.getOpenid() != null){
        	openId = userBean.getOpenid();
        }
    	if(openId == null && !"".equals(openId) && "P01".equalsIgnoreCase(payType)){
        	log.info("openId can't be null");
    		return JsonResult.failure(ResultCode.PARAMS_ERROR);
        }
        log.info(String.format("query orderNo from tenantflow by flowNo [%s]", flowNo));
        if (payAmount == null || payAmount.compareTo(BigDecimal.ZERO) < 1) {
            log.info("amount must be more then 0, please send request to pay again.");
            return JsonResult.failure(ResultCode.PARAMS_ERROR, "amount must be more then 0, please send request to pay again.");//金额不一致
        }
        log.info(String.format("create gateway [%s]", flowNo));
        GatewayPrepayInfo gw = null;
        if ("P03".equalsIgnoreCase(payType)) {
            gw = GatewayPrepayInfo.getAliQRPay(flowNo, payAmount, transDesc);
        } else if ("P02".equalsIgnoreCase(payType)) {
            gw = GatewayPrepayInfo.getWxQRPay(flowNo, payAmount, transDesc);
        } else if ("P01".equalsIgnoreCase(payType)) {
			gw = GatewayPrepayInfo.getWxJsPayInfo(flowNo, payAmount, openId, transDesc);
		}else {
            log.error(String.format("payType[%s] error. need between [P02,P03]", payType));
            return JsonResult.failure(ResultCode.PARAMS_ERROR, String.format("payType[%s] error. need between [P02,P03]", payType));
        }
        log.info("create orderNo[%s] prepay info [%s]", flowNo, JsonUtils.toJson(gw));
        Object rlt = null;
        try {
            gw.setLimitCreditPay("1");
            rlt = service.prepay(gw, "0000");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("prepay end .. " + JsonUtils.toJson(rlt));

        return JsonResult.success(rlt);

    }

    /**
     * 预支付：
     * 1. 微信支付返回prepay_id
     * 2. 扫码支付返回二维码
     *
     * @return
     */
    @RequestMapping(value = "/prepay", method = RequestMethod.GET)
    public JsonResult prepay() {
//        HttpSession session = SessionUtils.getSession();
//        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
//        if (userBean == null) {
//            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
//        }
//        //生成payinfo no
//        String tradeFlowNo = commonService.createOrderNo(Constants.ZF);//支付流水号 06
//        String payAmount = request.getParameter("payAmount");
//        String transDesc = request.getParameter("transDesc");
//        String payType = request.getParameter("payType"); //P03支付宝，P02微信
//
//        log.info(String.format("query orderNo from tenantflow by flowNo [%s]", tradeFlowNo));
//        String orderNo = (String) session.getAttribute("pay_order_no");
//        if (StringUtils.isBlank(orderNo)) {
//            log.info(String.format("orderNo is not exist [%s]", orderNo));
//            return "orderNo is not exist";
//        }
//
//
//        log.info(String.format("verificationPayAmount[%s][%s]", orderNo, payAmount));
//        String amountCorrect = ordersService.verificationPayAmount(orderNo, payAmount);
//        if (StringUtils.isBlank(amountCorrect)) {
//            log.info("order status is not between in [01,03], can't pay");
//            return "order status is not between in [01,03], can't pay";
//        }
//        if (amountCorrect.equals("0") && "prod".equalsIgnoreCase(Global.getConfig("env"))) {
//            log.info("amount is error, please send request to pay again.");
//            return "amount is error, please send request to pay again.";//金额不一致
//        }
//
//        log.info(String.format("create gateway [%s]", tradeFlowNo));
//        GatewayPrepayInfo gw = null;
//        if ("P03".equalsIgnoreCase(payType)) {
//            gw = GatewayPrepayInfo.getAliQRPay(tradeFlowNo, Double.valueOf(payAmount), transDesc);
//        } else if ("P02".equalsIgnoreCase(payType)) {
//            gw = GatewayPrepayInfo.getWxQRPay(tradeFlowNo, Double.valueOf(payAmount), transDesc);
//        } else {
//            log.error(String.format("payType[%s] error. need between [P02,P03]", payType));
//            return String.format("payType[%s] error. need between [P02,P03]", payType);
//        }
//        log.info("create orderNo[%s] prepay info [%s]", orderNo, JsonUtils.toJson(gw));
//        Object rlt = service.prepay(gw, orderNo);
//        log.info("prepay end .. " + JsonUtils.toJson(rlt));
//
//        Map<String, String> str = new HashMap<String, String>();
//        str.put("pay_data", JsonUtils.toJson(rlt));
//        return JsonUtils.toJson(str);
        return null;
    }


    /**
     * 查询支付结果
     *
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getResult(String tradeNo) {
        return tenantService.getPayResult(tradeNo);
    }

    /**
     * 退款
     *
     * @return
     */
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult refund() {

        return JsonResult.success(null);
    }

    
}
