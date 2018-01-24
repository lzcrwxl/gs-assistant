package com.fbee.modules.service;

import java.util.Map;

import com.fbee.modules.core.bean.SmsCode;

public interface SmsService {
	
	public SmsCode sendResetPwdSmsCode(String mobile);//重置密码发短信

	SmsCode sendPaySuccess(String mobile, String companyName, String serviceName, String serviceStartTime);//支付成功发短信

	public SmsCode sendLoginSmsCode(String mobile);
	public  Map<String, Object>  sendAddCheckMes(String mobile);

}
