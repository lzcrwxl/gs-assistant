package com.fbee.modules.core.sms;

import java.util.HashMap;
import java.util.Map;

public class SmsTemplates {
	
	/**
	 * 注册验证码模板ID
	 */
	public static final String REG_SMS_CODE = "165385";
	
	/**
	 * 登陆验证码模版id
	 */
	public static final String LOGIN_SMS_CODE = "223540";
	/**
	 * 重置密码验证码模板ID
	 */
	public static final String RESET_PWD_SMS_CODE = "158468";
	
	/*
	 * 家策门店助手，账号注册验证码模板ID
	 */
	public static final String REG_ASS_SMS_CODE = "223540";
   /**
    * 支付尾款成功模板ID
    */
	public static final String PAYMENT_SUCCESS = "173570";
	/**
	 * 支付定金成功模板ID
	 */
	public static final String PAYDJ_SUCCESS = "174917";
	/**
	 * 支付成功模板ID
	 */
	public static final String PAY_SUCCESS = "173570";
	/**
	 * 短信模板内容
	 */
	private static Map<String, String> CONTENT;
	
	static {
		CONTENT = new HashMap<String, String>();
		CONTENT.put(REG_SMS_CODE, "【家策好服务】注册验证码：{1}，{2}分钟内有效。为了确保您的账户安全，请勿向任何人泄露您的短信验证码。");
		CONTENT.put(RESET_PWD_SMS_CODE, "【家策好服务】密码重置验证码：{1}，{2}分钟内有效。为了确保您的账户安全，请勿向任何人泄露您的短信验证码。");
		CONTENT.put(PAY_SUCCESS, "【家策好服务】订单支付成功！您已在{1}成功预定{2}服务，服务自{3}开始。");
		CONTENT.put(LOGIN_SMS_CODE, "您的验证码：{1}，{2}分钟内有效。请勿向他人泄漏。");
		CONTENT.put(REG_ASS_SMS_CODE, "【家策门店助手】账号注册验证码：{1}，{2}分钟内有效。为了确保您的账户安全，请勿向任何人泄露您的短信验证码。");
	}
	
	public static String getContent(String templateId) {
		return CONTENT.get(templateId);
	}

}
