package com.fbee.modules.mybatis.model;

import com.fbee.modules.bean.payment.GatewayPrepayInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付请求信息
 * Created by gaoyan on 05/07/2017.
 */
public class PayInfo implements Serializable{

    private String  payNo; //ID
    private Integer userId; //用户ID
    private String  orderNo; //订单ID
    private String  orderDesc; //描述
    private Integer  amount; //订单金额
    private String  tradeChannel; //支付渠道
    private Date    requestTime; //请求发起时间
    private Date    responseTime; //响应时间
    private String  requestInfo; //请求报文
    private String  requestUrl;  //请求url
    private String  responseInfo; //响应报文
    private String  resultCode; //返回状态码
    private String  errCode;    //错误码
    private String  errMsg;     //错误信息
    private String  tokenId;    //动态口令
    private String  payInfo;    //原生态支付信息
    private String  prepayId;  //预支付ID

    private String  originChannel; //来源渠道(WEB/APP/好服务)

    private Date    callbackTime; //回调时间
    private String  payStatus;//支付状态
    private String  callbackInfo; //回调信息

    private String codeImgUrl;
    private String codeUrl;

    public PayInfo(){}

    public PayInfo(GatewayPrepayInfo prepay, String orderNo, Integer userId, String reqUrl, String reqXml) {
        this.payNo = prepay.getOutTradeNo();
        this.userId = userId;
        this.orderNo = orderNo;
        this.orderDesc = prepay.getBody();
        this.amount = Integer.valueOf(prepay.getTotalFee());
        this.tradeChannel = prepay.getService();
        this.requestTime = new Date();
        this.requestInfo = reqXml;
        this.requestUrl = reqUrl;
    }

    public PayInfo(String outTradeNo, String body, String totalFee, String service, String orderNo, Integer userId, String reqUrl, String reqXml) {
        this.payNo = outTradeNo;
        this.userId = userId;
        this.orderNo = orderNo;
        this.orderDesc = body;
        this.amount = Integer.valueOf(totalFee);
        this.tradeChannel = service;
        this.requestTime = new Date();
        this.requestInfo = reqXml;
        this.requestUrl = reqUrl;
    }

    public String getCodeImgUrl() {
        return codeImgUrl;
    }

    public void setCodeImgUrl(String codeImgUrl) {
        this.codeImgUrl = codeImgUrl;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getOriginChannel() {
        return originChannel;
    }

    public void setOriginChannel(String originChannel) {
        this.originChannel = originChannel;
    }

    public Date getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(Date callbackTime) {
        this.callbackTime = callbackTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getCallbackInfo() {
        return callbackInfo;
    }

    public void setCallbackInfo(String callbackInfo) {
        this.callbackInfo = callbackInfo;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTradeChannel() {
        return tradeChannel;
    }

    public void setTradeChannel(String tradeChannel) {
        this.tradeChannel = tradeChannel;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(String responseInfo) {
        this.responseInfo = responseInfo;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

}
