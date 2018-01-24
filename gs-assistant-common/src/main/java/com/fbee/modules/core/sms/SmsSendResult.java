package com.fbee.modules.core.sms;

import java.util.HashMap;
import java.util.Map;

/**
 * 云通讯短信发送结果
 * @author sk.chen
 *
 */
public class SmsSendResult {
	
	public static final String SUCCESS = "000000";
	public static final String FAILURE = "999999";
	
	public static final String ERROR_172001 = "172001";
	public static final String ERROR_172002 = "172002";
	public static final String ERROR_172003 = "172003";
	public static final String ERROR_900001 = "900001";
	public static final String ERROR_900002 = "900002";
	public static final String ERROR_900003 = "900003";

	private static Map<String, String> RESULT_MAG;
	
	static {
		RESULT_MAG = new HashMap<String, String>();
		RESULT_MAG.put(SUCCESS, "发送成功");
		RESULT_MAG.put(FAILURE, "发送失败");
		
		RESULT_MAG.put(ERROR_172001, "网络错误");
		RESULT_MAG.put(ERROR_172002, "无返回");
		RESULT_MAG.put(ERROR_172003, "返回包体错误");

	}
	
	public static String getMsg(String code) {
		return RESULT_MAG.get(code);
	}
	
	private String resultCode;
	private String resultMsg;
	private String smsMessageSid;
	
	public String getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultMsg() {
		return resultMsg;
	}
	
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	public String getSmsMessageSid() {
		return smsMessageSid;
	}

	public void setSmsMessageSid(String smsMessageSid) {
		this.smsMessageSid = smsMessageSid;
	}

	public SmsSendResult() {
		
	}
	
	public static SmsSendResult sendSuccess(String smsMessageSid) {
		SmsSendResult smsSendResult = new SmsSendResult();
		smsSendResult.setResultCode(SUCCESS);
		smsSendResult.setResultMsg(getMsg(SUCCESS));
		smsSendResult.setSmsMessageSid(smsMessageSid);
		return smsSendResult;
	}
	
	public static SmsSendResult sendFailure(String resultCode) {
		SmsSendResult smsSendResult = new SmsSendResult();
		smsSendResult.setResultCode(resultCode);
		smsSendResult.setResultMsg(getMsg(resultCode));
		return smsSendResult;
	}
	
	public static SmsSendResult sendFailure(String resultCode, String resultMsg) {
		SmsSendResult smsSendResult = new SmsSendResult();
		smsSendResult.setResultCode(resultCode);
		smsSendResult.setResultMsg(resultMsg);
		return smsSendResult;
	}
}
