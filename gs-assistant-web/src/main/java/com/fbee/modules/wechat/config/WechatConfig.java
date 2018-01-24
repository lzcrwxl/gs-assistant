package com.fbee.modules.wechat.config;


import com.fbee.modules.core.config.Global;

/**
 * @Description: TODO
 * @author hzp
 * @date 2016-6-12
 *
 */
public class WechatConfig {
	//微信公众号身份的唯一标识
	public static final String APPID= Global.getConfig("wechat.appid");
	
	public static final String TOKEN="hezhangpeng";
	
	//受理商ID
	public static final String MCHID="1248020001";
	
	//商户支付密钥Key
	public static final String KEY="O3bJ6Eiz7dL6xk2824U0O2s46xh56yNs";
	
	public static final String APPSECRET= Global.getConfig("wechat.secret");
	
	//=======【统一下单路径】===================================
	public static final String  UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	//=======【JSAPI路径设置】===================================
	public static final String  JS_API_CALL_URL = "";
	
	//=======【证书路径设置】=====================================
	//证书路径,注意应该填写绝对路径
	public static final String SSLCERT_PATH="/cacert/apiclient_cert.pem";
	
	public static final String SSLKEY_PATH="/cacert/apiclient_key.pem";
	
	//=======【异步通知url设置】===================================
	//异步通知url，
	public static final String NOTIFY_URL="";
	
	//=======【curl超时设置】===================================
	//本例程通过curl使用HTTP POST方法，此处可修改其超时时间，默认为30秒
	public static final String CURL_TIMEOUT="";
	
	public static final String CHARSET_UTF8="UTF-8";
	
	public static final String CHARSET_GBK="GBK";
	
	public static final String KEYSTORE_FILE = "D:\\wc_cert\\apiclient_cert.p12";
	
	public static class SignType{
		public static final String MD5="MD5";//md5签名
	}
	
	public static final String SUCCESS="SUCCESS";
	
	public static final String SYSTEM_ERROR="ERROR";
	
	public static final String FAIL="FAIL";
	
	public static final String METHOD_POST="POST";
	
	public static final String METHOD_GET="GET";
	
	//token ticket 过期时间
	public static int EXPIRESIN = 7000; 
	
	//GET方式请求获得jsapi_ticket
	public final static String JSAPITICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String ACCESSTOKENURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	public static String LANG_ZH_CN="zh_CN";
	
}
