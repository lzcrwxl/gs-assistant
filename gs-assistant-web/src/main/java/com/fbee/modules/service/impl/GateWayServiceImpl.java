package com.fbee.modules.service.impl;


import com.fbee.modules.basic.WebUtils;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.payment.GatewayPrepayInfo;
import com.fbee.modules.bean.payment.SwiftpassConfig;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.jsonData.json.PrepayJsonData;
import com.fbee.modules.mybatis.dao.MembersInfoMapper;
import com.fbee.modules.mybatis.dao.PayInfoMapper;
import com.fbee.modules.mybatis.model.PayInfo;
import com.fbee.modules.service.GateWayService;
import com.fbee.modules.service.TenantService;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.utils.prepay.MD5;
import com.fbee.modules.utils.prepay.SignUtils;
import com.fbee.modules.utils.prepay.XmlUtils;
import com.google.common.collect.Maps;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * 支付网关调用
 * Created by gaoyan on 04/07/2017.
 */
@Service
public class GateWayServiceImpl implements GateWayService {

    private static Logger log = LoggerFactory.getLogger("paymentLogger");

    private final static String version = "1.1";
    private final static String charset = "UTF-8";
    private final static String sign_type = "MD5";

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Autowired
    TenantService tenantService;


    /**
     * <一句话功能简述>
     * <功能详细描述>支付请求
     *
     * @throws ServletException
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    @Override
    public Object prepay(GatewayPrepayInfo prepay, String orderNo) throws ServletException, IOException {
        if (!"prod".equals(Constants.ENV)) {
            prepay.setTotalFee("1");
        }

        UserBean user = WebUtils.getCurrentUser();
        log.info(String.format("user_info: [%s|%s|%s|%s]", user.getTenantId(), user.getUserId(), user.getDomain(), user.getLoginAccount()));
        log.info(String.format("===pay===预充值/支付 flowNo[%s]", prepay.getOutTradeNo()));
        SortedMap<String, String> map = Maps.newTreeMap();
        map.put("service", prepay.getService());
        map.put("version", prepay.getVersion());
        map.put("charset", prepay.getCharset());
        map.put("sign_type", prepay.getSignType());

        map.put("mch_id", prepay.getMchId());
        map.put("notify_url", prepay.getNotifyUrl());
        map.put("nonce_str", prepay.getNonceStr());

        map.put("limit_credit_pay", prepay.getLimitCreditPay());
        map.put("body", prepay.getBody());
        map.put("mch_create_ip", prepay.getMchCreateIp());
        map.put("is_raw", prepay.getIsRaw());
        map.put("out_trade_no", prepay.getOutTradeNo());
        map.put("sub_openid", prepay.getSubOpenid());
        map.put("sub_appid", prepay.getSubAppid());
        map.put("total_fee", prepay.getTotalFee());
        if (!"prod".equals(Constants.ENV)) {
            map.put("sub_appid", Global.getConfig("wechat.appid.prod"));
//            //需要用生产openid
            map.put("sub_openid", user.getUnionId());
        }

        Map<String, String> params = SignUtils.paraFilter(map);
        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
        SignUtils.buildPayParams(buf, params, false);
        String preStr = buf.toString();
        String sign = MD5.sign(preStr, "&key=" + SwiftpassConfig.key, "utf-8");
        map.put("sign", sign);

        String reqUrl = SwiftpassConfig.req_url;

        String reqXml = XmlUtils.parseXML(map);

        log.info("=====pay=====支付请求" + reqUrl + "]");
        log.info("=====pay=====支付请求" + reqXml + "]");

        //将请求信息插入数据库
        PayInfo payInfo = new PayInfo(prepay, orderNo, user.getUserId(), reqUrl, reqXml);
        log.info("insert pay-info ... begin");
        payInfoMapper.insert(payInfo);
        log.info("insert pay-info ... end");

        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        String res = null;
        Map<String, String> resultMap = null;
        try {
            HttpPost httpPost = new HttpPost(reqUrl);
            StringEntity entityParams = new StringEntity(reqXml, "utf-8");
            httpPost.setEntity(entityParams);
            httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
            if (response != null && response.getEntity() != null) {
                resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
                res = XmlUtils.toXml(resultMap);

                log.info("===pay===请求结果：" + res);
                payInfo.setPayNo(prepay.getOutTradeNo());
                payInfo.setResponseInfo(res);
                payInfo.setResponseTime(new Date());


                if (!SignUtils.checkParam(resultMap, SwiftpassConfig.key)) {
                    res = "验证签名不通过";
                    payInfo.setResultCode(response.getStatusLine().getStatusCode() + "");
                    payInfo.setErrCode(resultMap.get("status"));
                    payInfo.setErrMsg(resultMap.get("message"));
                } else {
                    if ("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))) {
                        String code_img_url = resultMap.get("code_img_url");
                        log.info("===pay===code_img_url : " + code_img_url);
                        res = "ok";
                        payInfo.setResultCode(resultMap.get("result_code"));
                        payInfo.setPayInfo(resultMap.get("pay_info"));
                        payInfo.setTokenId(resultMap.get("token_id"));
                        if (resultMap.get("pay_info") != null) {
                            Map<String, String> pinfo = (Map<String, String>) JsonUtils.fromJson(resultMap.get("pay_info"), Map.class);
                            try {
                                payInfo.setPrepayId(pinfo.get("package"));
                            } catch (Exception e) {
                                log.error("获取prepay_Id失败 == " + resultMap.get("pay_info"));
                                throw e;
                            }
                        }
                        payInfo.setCodeImgUrl(code_img_url);

                        tenantService.recharge(prepay.getOutTradeNo(), user.getTenantId(), user.getLoginAccount());

                    } else {
                        payInfo.setResultCode(resultMap.get("result_code"));
                        payInfo.setErrCode(resultMap.get("status"));
                        payInfo.setErrMsg(resultMap.get("message"));
                    }
                }
                //更新预支付响应信息
                log.info("update pay-info ... begin");
                payInfoMapper.update(payInfo);
                log.info("udpate pay-info ... end");

            } else {
                log.warn("===prepay===" + response.getStatusLine().getReasonPhrase());
                res = "请求预支付接口失败";
            }
        } catch (SQLException e) {
            log.error("===pay===操作失败，原因：" + e.getMessage());
            res = "支付请求创建失败，请联系管理员";
        } catch (Exception e) {
            log.error("===pay===操作失败，原因：" + e.getMessage());
            res = "请求支付失败，请重试";
        } finally {
            if (response != null) {
                response.close();
            }
            if (client != null) {
                client.close();
            }
        }
        if (StringUtils.isNotBlank(payInfo.getCodeImgUrl())) {
            Map<String, String> rlt = new HashMap<String, String>();
            rlt.put("tradeNo", payInfo.getPayNo());
            rlt.put("codeImgUrl", payInfo.getCodeImgUrl());
            return rlt;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if ("ok".equals(res)) {
            Map<String, String> pinfo = (Map<String, String>) JsonUtils.fromJson(resultMap.get("pay_info"), Map.class);
            PrepayJsonData pd = new PrepayJsonData();
            pd.setAppId(pinfo.get("appId"));
            pd.setNonceStr(pinfo.get("nonceStr"));
            pd.setPackageInfo(pinfo.get("package"));
            pd.setPaySign(pinfo.get("paySign"));
            pd.setSignType(pinfo.get("signType"));
            pd.setTimeStamp(pinfo.get("timeStamp"));
            return pd;
        } else {
            result.put("status", "500");
            result.put("msg", res);
        }
        return result;
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>订单查询
     *
     * @throws ServletException
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    @Override
    public Map<String, String> query(String tradeNo) throws ServletException, IOException {
//        log.debug("订单查询...");
//        SortedMap<String, String> map = new TreeMap<String, String>();
//        GatewayPrepayInfo info = GatewayPrepayInfo.getQueryRequest(tradeNo);
//
//        map.put("service", info.getService());
//        map.put("version", info.getVersion());
//        map.put("charset", charset);
//        map.put("sign_type", sign_type);
//        //map.put("sub_appid", info.getSubAppid());
//        map.put("mch_id", info.getMchId());
//        map.put("out_trade_no", tradeNo);
//
//        String key = SwiftpassConfig.key;
//        String reqUrl = SwiftpassConfig.req_url;
//        map.put("nonce_str", String.valueOf(new Date().getTime()));
//
//        Map<String, String> params = SignUtils.paraFilter(map);
//        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
//        SignUtils.buildPayParams(buf, params, false);
//        String preStr = buf.toString();
//        String sign = MD5.sign(preStr, "&key=" + key, "utf-8");
//        map.put("sign", sign);
//
//        log.debug("reqUrl:" + reqUrl);
//
//        CloseableHttpResponse response = null;
//        CloseableHttpClient client = null;
//        String res = null;
//        SwiftpassCallbackInfo sp = null;
//        try {
//            HttpPost httpPost = new HttpPost(reqUrl);
//            StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map), "utf-8");
//            httpPost.setEntity(entityParams);
//            httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
//            client = HttpClients.createDefault();
//            response = client.execute(httpPost);
//
//            if (response != null && response.getEntity() != null) {
//                Map<String, String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
//                res = XmlUtils.toXml(resultMap);
//                log.info("gateway query - 请求结果：" + res);
//                String resJson = JsonUtils.toJson(resultMap);
//                sp = JsonUtils.fromJson(resJson, SwiftpassCallbackInfo.class);
//                sp.setResultXml(res);
//
//                if (!SignUtils.checkParam(resultMap, key)) {
//                    res = "验证签名不通过";
//                } else {
//                    if ("0".equals(resultMap.get("status"))) {
//                        if ("0".equals(resultMap.get("result_code"))) {
//                            log.debug("业务成功，在这里做相应的逻辑处理");
//                            String trade_state = resultMap.get("trade_state");
//                            log.debug("trade_state : " + trade_state);
//                            log.debug("这里商户需要同步自己的订单状态。。。");
//                            if(!"SUCCESS".equalsIgnoreCase(sp.getTradeState())){
//                                Map<String, String> result = new HashMap<String, String>();
//                                result.put("status", sp.getTradeState());
//                                result.put("msg", "支付失败");
//                                return result;
//                            }
//                            res = (String) financeFlowService.processSwift(sp);
//                            if ("success".equalsIgnoreCase(res)) {
//                                Map<String, String> result = new HashMap<String, String>();
//                                result.put("status", sp.getTradeState());
//                                result.put("msg", "支付成功");
//                                return result;
//                            }
//                            log.error("支付成功， 但业务订单处理失败");
//                            res = "订单处理失败";
//                        } else {
//                            log.error("业务失败，尝试重新请求，并查看错误代码描叙");
//                            res = sp.getErrMsg();
//                        }
//                    } else {
//                        log.error("这里是请求参数有问题...");
//                        res = sp.getErrMsg();
//                    }
//                }
//            } else {
//                log.error("查询请求失败...");
//                res = "查询请求失败!";
//            }
//        } catch (Exception e) {
//            log.error("操作失败，原因：" + e.getMessage());
//            res = "操作失败";
//        } finally {
//            if (response != null) {
//                response.close();
//            }
//            if (client != null) {
//                client.close();
//            }
//        }
//        Map<String, String> result = new HashMap<String, String>();
//        result.put("status", sp.getTradeState());
//        result.put("msg", res);
        return null;
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>退款查询
     *
     * @throws ServletException
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    @Override
    public Map<String, String> refundQuery() throws ServletException, IOException {
        log.debug("退款查询...");
//        SortedMap<String, String> map = XmlUtils.getParameterMap(SessionManager.getRequest());
//
//        map.put("service", "trade.refund.query");
//        map.put("version", version);
//        map.put("charset", charset);
//        map.put("sign_type", sign_type);
//
//        String key = SwiftpassConfig.key;
//        String reqUrl = SwiftpassConfig.req_url;
//        map.put("mch_id", SwiftpassConfig.mch_id);
//        map.put("nonce_str", String.valueOf(new Date().getTime()));
//
//        Map<String, String> params = SignUtils.paraFilter(map);
//        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
//        SignUtils.buildPayParams(buf, params, false);
//        String preStr = buf.toString();
//        String sign = MD5.sign(preStr, "&key=" + key, "utf-8");
//        map.put("sign", sign);
//
//        log.debug("reqUrl:" + reqUrl);
//
//        CloseableHttpResponse response = null;
//        CloseableHttpClient client = null;
//        String res = null;
//        try {
//            HttpPost httpPost = new HttpPost(reqUrl);
//            StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map), "utf-8");
//            httpPost.setEntity(entityParams);
//            httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
//            client = HttpClients.createDefault();
//            response = client.execute(httpPost);
//            if (response != null && response.getEntity() != null) {
//                Map<String, String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
//                res = XmlUtils.toXml(resultMap);
//                log.debug("请求结果：" + res);
//
//                if (!SignUtils.checkParam(resultMap, key)) {
//                    res = "验证签名不通过";
//                }
//            } else {
//                res = "操作失败!";
//            }
//        } catch (Exception e) {
//            log.error("操作失败，原因：", e);
//            res = "操作失败";
//        } finally {
//            if (response != null) {
//                response.close();
//            }
//            if (client != null) {
//                client.close();
//            }
//        }
//        Map<String, String> result = new HashMap<String, String>();
//        if (res.startsWith("<")) {
//            result.put("status", "200");
//            result.put("msg", "操作成功，请在日志文件中查看");
//        } else {
//            result.put("status", "500");
//            result.put("msg", res);
//        }
        return null;
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>退款
     *
     * @throws ServletException
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    @Override
    public Map<String, String> refund() throws ServletException, IOException {
        log.debug("退款...");
//        SortedMap<String, String> map = XmlUtils.getParameterMap(SessionManager.getRequest());
//
//        map.put("service", "trade.single.refund");
//        map.put("version", version);
//        map.put("charset", charset);
//        map.put("sign_type", sign_type);
//
//        String key = SwiftpassConfig.key;
//        String reqUrl = SwiftpassConfig.req_url;
//        map.put("mch_id", SwiftpassConfig.mch_id);
//        map.put("op_user_id", SwiftpassConfig.mch_id);
//        map.put("nonce_str", String.valueOf(new Date().getTime()));
//
//        Map<String, String> params = SignUtils.paraFilter(map);
//        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
//        SignUtils.buildPayParams(buf, params, false);
//        String preStr = buf.toString();
//        String sign = MD5.sign(preStr, "&key=" + key, "utf-8");
//        map.put("sign", sign);
//
//        log.debug("reqUrl:" + reqUrl);
//
//        CloseableHttpResponse response = null;
//        CloseableHttpClient client = null;
//        String res = null;
//        try {
//            HttpPost httpPost = new HttpPost(reqUrl);
//            StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map), "utf-8");
//            httpPost.setEntity(entityParams);
//            httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
//            client = HttpClients.createDefault();
//            response = client.execute(httpPost);
//            if (response != null && response.getEntity() != null) {
//                Map<String, String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
//                res = XmlUtils.toXml(resultMap);
//                log.debug("请求结果：" + res);
//
//                if (!SignUtils.checkParam(resultMap, key)) {
//                    res = "验证签名不通过";
//                }
//            } else {
//                res = "操作失败!";
//            }
//        } catch (Exception e) {
//            log.error("操作失败，原因：", e);
//            res = "操作失败";
//        } finally {
//            if (response != null) {
//                response.close();
//            }
//            if (client != null) {
//                client.close();
//            }
//        }
//        Map<String, String> result = new HashMap<String, String>();
//        if (res.startsWith("<")) {
//            result.put("status", "200");
//            result.put("msg", "操作成功，请在日志文件中查看");
//        } else {
//            result.put("status", "500");
//            result.put("msg", res);
//        }
        return null;
    }

}
