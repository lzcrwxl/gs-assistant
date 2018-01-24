package com.fbee.modules.bean.payment;

import com.fbee.modules.bean.consts.PaymentConst;
import com.fbee.modules.core.config.Global;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 支付网关 预支付对象
 * Created by gaoyan on 17/07/2017.
 * SortedMap<String, String> map = Maps.newTreeMap();
 * map.put("service", "pay.weixin.jspay");
 * map.put("version", version);
 * map.put("charset", charset);
 * map.put("sign_type", sign_type);
 * <p>
 * map.put("mch_id", SwiftpassConfig.mch_id);
 * map.put("notify_url", SwiftpassConfig.notify_url);
 * map.put("nonce_str", RandomStringUtils.random(32,true,true));
 * <p>
 * map.put("body",onlineOrderInfo.getOrderDesc());
 * map.put("mch_create_ip","127.0.0.1");
 * map.put("is_raw","1");
 * map.put("out_trade_no", pay_no);
 * map.put("sub_openid", SessionManager.getUser().getOpenId());
 * map.put("sub_appid", Constants.APPID);
 * map.put("total_fee",Math.round(onlineOrderInfo.getAmount()*100)+"");
 */
public class GatewayPrepayInfo implements Serializable {

    private String service; //接口类型
    private String version; //版本号
    private String charset; //字符集
    private String signType; //签名方式
    private String mchId;    //商户号
    private String notifyUrl; //通知地址
    private String nonceStr;  //随机字符串
    private String mchCreateIp; //终端IP
    private String timeStart; //订单生成时间

    private String transaNo; //商户订单号
    private String outTradeNo; //商户订单号
    private String totalFee; //总金额
    private String isRaw;     //是否原生态
    private String subAppid;   //appid
    private String subOpenid;   //openid

    private String deviceInfo; //设备号
    private String body; //商品描述
    private String attach; //附加信息
    private String limitCreditPay; //是否限制信用卡
    private String timeExpire; //超时时间
    private String opUserId;   //操作员
    private String goodsTag;    //商品标记
    private String productId;  //商品ID
    private String sign;        //签名

    public static GatewayPrepayInfo getWxJsPayInfo(String outTradeNo,BigDecimal amount, String openId, String body) {
        GatewayPrepayInfo info = new GatewayPrepayInfo();
        info.init();
        info.setService(PaymentConst.TradeChannel.WX_JS_PAY.getService());
        info.setIsRaw("1");
        info.setOutTradeNo(outTradeNo);
        info.setSubOpenid(openId);
        info.setSubAppid(Global.getConfig("wechat.appid"));
        info.setTotalFee(Math.round(amount.doubleValue()*100)+"");
        info.setBody(body);
        return info;
    }

    public static GatewayPrepayInfo getWxQRPay(String outTradeNo,BigDecimal amount, String body) {
        GatewayPrepayInfo info = new GatewayPrepayInfo();
        info.init();
        info.setService(PaymentConst.TradeChannel.WECHATPAY.getService());
        info.setTotalFee(Math.round(amount.doubleValue()*100)+"");
        info.setOutTradeNo(outTradeNo);
        info.setBody(body);
        return info;
    }

    public static GatewayPrepayInfo getAliQRPay(String outTradeNo, BigDecimal amount, String body) {
        GatewayPrepayInfo info = new GatewayPrepayInfo();
        info.init();
        info.setService(PaymentConst.TradeChannel.ALIPAY.getService());
        info.setTotalFee(Math.round(amount.doubleValue()*100)+"");
        info.setOutTradeNo(outTradeNo);
        info.setBody(body);
        return info;
    }

    public static GatewayPrepayInfo getQueryRequest(String outTradeNo) {
        GatewayPrepayInfo info = new GatewayPrepayInfo();
        info.init();
        //info.setService(OrderConst.TradeChannel.QUERY_STATUS.getService());
        info.setOutTradeNo(outTradeNo);
        //info.setSubAppid(Constants.APPID);
        return info;
    }

    public void init() {
        this.version = "1.1"; //版本号
        this.charset = "UTF-8"; //字符集
        this.signType = "MD5"; //签名方式
        this.mchId = SwiftpassConfig.mch_id;    //商户号
        this.notifyUrl = SwiftpassConfig.notify_url; //通知地址
        this.nonceStr = RandomStringUtils.random(32, true, true);  //随机字符串
        try {
            this.mchCreateIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            this.mchCreateIp = "127.0.0.1";
        }
    }

    public String getSubOpenid() {
        return subOpenid;
    }

    public void setSubOpenid(String subOpenid) {
        this.subOpenid = subOpenid;
    }

    public String getIsRaw() {
        return isRaw;
    }

    public void setIsRaw(String isRaw) {
        this.isRaw = isRaw;
    }

    public String getSubAppid() {
        return subAppid;
    }

    public void setSubAppid(String subAppid) {
        this.subAppid = subAppid;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getMchCreateIp() {
        return mchCreateIp;
    }

    public void setMchCreateIp(String mchCreateIp) {
        this.mchCreateIp = mchCreateIp;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getLimitCreditPay() {
        return limitCreditPay;
    }

    public void setLimitCreditPay(String limitCreditPay) {
        this.limitCreditPay = limitCreditPay;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
