package com.fbee.modules.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.sms.SSLClient;
import com.fbee.modules.core.sms.SmsSendResult;
import com.fbee.modules.core.sms.SmsUtil;
import com.fbee.modules.service.SendSmsService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SendSmsYTX implements SendSmsService {

	private static final String CHARSET_UTF8 = "UTF-8";

	public static final String DOMAIN = Global.getConfig(Constants.YTX_DOMAIN);
	public static final String PORT = Global.getConfig(Constants.YTX_PORT);
	public static final String ACCOUNT_SID = Global.getConfig(Constants.YTX_ACCOUNT_SID);
	public static final String AUTH_TOKEN = Global.getConfig(Constants.YTX_AUTH_TOKEN);
	public static final String APP_ID = Global.getConfig(Constants.YTX_APP_ID);
	@Override
	public SmsSendResult send(String to, String templateId, String[] datas) {
		// 校验参数
		if ((SmsUtil.isEmpty(to)) || (SmsUtil.isEmpty(templateId))) {
			throw new IllegalArgumentException("必选参数:" + (SmsUtil.isEmpty(to) ? " 手机号码 " : "")
					+ (SmsUtil.isEmpty(templateId) ? " 模板Id " : "") + "为空");
		}
		// 初始化HttpClient
		CloseableHttpClient httpClient = initHttpClient();
		if (httpClient == null) {
			return SmsSendResult.sendFailure(SmsSendResult.FAILURE);
		}
		// 初始化HttpeRsponse
		CloseableHttpResponse httpeRsponse = null;
		String responseBody = "";
		try {
			// 获取时间戳，yyyyMMddHHmmss，用于生成验证参数和验证信息，两者使用的时间戳必须一致
			String timestamp = SmsUtil.getTimestamp();
			// 申请接口请求URL
			String url = generateUrl(timestamp);
			if (SmsUtil.isEmpty(url)) {
				return SmsSendResult.sendFailure(SmsSendResult.FAILURE);
			}
			HttpPost httpPost = new HttpPost(url);
			// 生成请求参数
			String requsetBody = generateRequestBody(to, templateId, datas);
			if (SmsUtil.isEmpty(requsetBody)) {
				return SmsSendResult.sendFailure(SmsSendResult.FAILURE);
			}

			Log.info("send template sms request body =  " + requsetBody);

			StringEntity formEntity = new StringEntity(requsetBody, CHARSET_UTF8);
			formEntity.setContentType("application/json");
			formEntity.setContentEncoding(CHARSET_UTF8);
			httpPost.setEntity(formEntity);
			// 生成验证信息
			String authorization = generateAuthorization(timestamp);
			if (SmsUtil.isEmpty(authorization)) {
				return SmsSendResult.sendFailure(SmsSendResult.FAILURE);
			}
			// 设置包头信息 ，formEntity中含有Content-Length属性，这里无需再设置，否则会抛出异常
			httpPost.addHeader("Authorization", authorization);
			httpPost.addHeader("Accept", "application/json");
			httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
			// 发起请求
			httpeRsponse = httpClient.execute(httpPost);
			// 获取返回结果
			responseBody = EntityUtils.toString(httpeRsponse.getEntity(), CHARSET_UTF8).trim();

			Log.info("send template sms response body =  " + responseBody);
		} catch (IOException e) {
			return SmsSendResult.sendFailure(SmsSendResult.ERROR_172001);
		} catch (Exception e) {
			return SmsSendResult.sendFailure(SmsSendResult.ERROR_172002);
		} finally {
			if (httpeRsponse != null) {
				try {
					httpeRsponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 解析返回结果
		try {
			JsonParser parser = new JsonParser();
			JsonObject responseJson = parser.parse(responseBody).getAsJsonObject();
			if (SmsSendResult.SUCCESS.equals(responseJson.get("statusCode").getAsString())) {
				JsonObject templateSMS = responseJson.get("templateSMS").getAsJsonObject();
				String smsMessageSid = templateSMS.get("smsMessageSid").getAsString();
				return SmsSendResult.sendSuccess(smsMessageSid);
			} else {
				return SmsSendResult.sendFailure(responseJson.get("statusCode").getAsString(),
						responseJson.get("statusMsg").getAsString());
			}
		} catch (Exception e) {
			return SmsSendResult.sendFailure(SmsSendResult.ERROR_172003);
		}
	}

	/**
	 * 初始化HttpClient
	 * 
	 * @return
	 */
	protected static CloseableHttpClient initHttpClient() {
		SSLClient sslClient = new SSLClient();
		try {
			return sslClient.registerSSL("TLS");
		} catch (Exception e) {
			Log.error("init httpclient error", e);
			return null;
		}
	}

	/**
	 * 生成验证参数
	 * 
	 * @param timestamp
	 * @return
	 */
	protected static String generateSignatur(String timestamp) {
		try {
			return SmsUtil.md5Digest(ACCOUNT_SID + AUTH_TOKEN + timestamp);
		} catch (NoSuchAlgorithmException e) {
			Log.error("generate signatur error", e);
			return null;
		} catch (UnsupportedEncodingException e) {
			Log.error("generate signatur error", e);
			return null;
		}
	}

	/**
	 * 生成接口请求URL
	 * 
	 * @param timestamp
	 * @return
	 */
	protected static String generateUrl(String timestamp) {
		String signatur = generateSignatur(timestamp);
		if (SmsUtil.isEmpty(signatur)) {
			return null;
		}
		return new StringBuilder("https://").append(DOMAIN).append(":").append(PORT).append("/")
				.append("2013-12-26").append("/").append("Accounts").append("/").append(ACCOUNT_SID).append("/")
				.append("SMS/TemplateSMS").append("?sig=").append(signatur).toString();
	}

	/**
	 * 生成请求数据
	 * 
	 * @param to
	 * @param templateId
	 * @param datas
	 * @return
	 */
	protected static String generateRequestBody(String to, String templateId, String[] datas) {
		JsonObject json = new JsonObject();
		json.addProperty("appId", APP_ID);
		json.addProperty("to", to);
		json.addProperty("templateId", templateId);
		if (datas != null) {
			StringBuilder sb = new StringBuilder("[");
			for (String s : datas) {
				sb.append("\"" + s + "\"" + ",");
			}
			sb.deleteCharAt(sb.length() - 1).append("]");
			JsonParser parser = new JsonParser();
			JsonArray Jarray = parser.parse(sb.toString()).getAsJsonArray();
			json.add("datas", Jarray);
		}
		return json.toString();
	}

	/**
	 * 生成验证信息
	 * 
	 * @param timestamp
	 * @return
	 */
	protected static String generateAuthorization(String timestamp) {
		try {
			return SmsUtil.base64Encoder((ACCOUNT_SID + ":" + timestamp));
		} catch (UnsupportedEncodingException e) {
			Log.error("generate authorization error", e);
			return null;
		}
	}

	public static void main(String[] args) {
		SendSmsYTX sendSmsYTX = new SendSmsYTX();
		System.out.println(sendSmsYTX.send("17316339860", "1", new String[] { "9527", "10" }).getResultMsg());

	}
}
