package com.fbee.modules.jsonData.json;

import java.io.Serializable;

/**
 *  预支付返回info
 *  公众号id	    appId	是	String	对应初始化请求中返回的pay_info中的信息
    时间戳	    timeStamp	是	String	对应初始化请求中返回的pay_info中的信息
    随机字符串	nonceStr	是	String	对应初始化请求中返回的pay_info中的信息
    订单详情扩展字符串	package	是	String	对应初始化请求中返回的pay_info中的信息
    签名方式	    signType	是	String	对应初始化请求中返回的pay_info中的信息
    签名	        paySign	是	String	对应初始化请求中返回的pay_info中的信息
 * Created by gaoyan on 10/07/2017.
 */
public class PrepayJsonData implements Serializable{


    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String packageInfo;
    private String signType;
    private String paySign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackage() {
        return packageInfo;
    }

    public void setPackageInfo(String packageInfo) {
        this.packageInfo = packageInfo;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
