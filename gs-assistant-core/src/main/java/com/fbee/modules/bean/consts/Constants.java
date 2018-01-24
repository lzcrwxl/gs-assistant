package com.fbee.modules.bean.consts;

import com.fbee.modules.bean.SessionKeys;
import com.fbee.modules.core.config.Global;

public class Constants {
	/**
	 * 用户缓存
	 */
	public static final String USER_SESSION = SessionKeys.USER_SESSION;
	/**
	 * banner默认的系统图片
	 */
	public static final String DEFAULT_BANNER_IMAGE_KEY="D_BANNER_IMAGE";

	public static final Integer CONTRACT_NUM=6;

	/**
	 * 微信应用appID
	 * */
	public static final String APPID = Global.getConfig("wechat.appid");
	public static final String APPSECRET = Global.getConfig("wechat.secret");
	public static final String MOBILE_HOST_NAME = Global.getConfig("mobileUrl");
	public static final String ENV = Global.getConfig("env");

	/**
	 * 应用Token
	 * */
	public static final String TOKEN = "jiacer";


	public static final String STATUS="00";

	/**
	 * url上用户授权key
	 */
	public static final String UA="token";
	public static final String UID="uid";
	public static final String QR_UA="qr_token";
	

	public static final String DEFAULT_PAGE_NO="1";
	
	public static final String DEFAULT_PAGE_SIZE="10";
	
	public static final String DAYTON="、";
	
	public static final String EMPTY="";

	public static final String COMMA=",";
	
	
	/**网站管理图片路径*/
	public static final String IMAGE_URL = "/images";

	public static final String WEB_SITE_IMGAE_BASE_PATH = "website";
	public static final String ABOUT_US_IMGAE_BASE_PATH = "aboutus";
	public static final String BANNER_IMGAE_BASE_PATH = "banner";


	public static final String YTX_DOMAIN = "sms.ytx.domain";
	public static final String YTX_PORT = "sms.ytx.port";
	public static final String YTX_ACCOUNT_SID = "sms.ytx.accountSid";
	public static final String YTX_AUTH_TOKEN = "sms.ytx.authToken";
	public static final String YTX_APP_ID = "sms.ytx.appId";

	
	/**合同信息图片路径*/
	public static final String ORDER_IMAGE_BASE_PATH = "order";
	
	public static final String CONTRACT_IMAGE_BASE_PATH = "contract";


	/**订单号前二位业务规则*/
	public static final String BD = "01";//本地订单
	public static final String WL = "02";//网络订单
	public static final String TFX = "03";//淘分享订单
	public static final String YY = "04";//预约订单
	
	public static final String JY = "05";//交易流水号
	public static final String ZF = "06";//支付流水号
	public static final String ZH = "07";//账户流水号
	public static final String CW = "08";//财务流水号
	
	public static final String AYCW = "09";//阿姨财务流水号
	
	
	/**权限缓存*/
	public static final String USER_PERMISSION = "permission_";	//用户权限前缀+用户id后缀
	public static final String USER_NO_PERMISSION = "no_permission_";	//用户无权限前缀+用户id后缀
	
	/*刷新*/
	public static final Integer REFRESH_TRUE = 1;
	public static final Integer REFRESH_FALSE = 0;
	
	public class AUTH_KEY {
        public static final String TOKEN = "token";
        public static final String UID = "uid";
        public static final String REFERER = "Referer";
        public static final String OPEN_ID = "openId";
    }
}
