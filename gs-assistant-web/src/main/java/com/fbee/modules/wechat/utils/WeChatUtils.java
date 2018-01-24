package com.fbee.modules.wechat.utils;

import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.wechat.bean.AccessTicket;
import com.fbee.modules.wechat.bean.AccessToken;
import com.fbee.modules.wechat.bean.TokenManager;
import com.fbee.modules.wechat.bean.WechatLog;
import com.fbee.modules.wechat.config.WechatConfig;
import com.fbee.modules.wechat.core.X509TrustManagerImpl;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 微信工具类
 * @author 
 */
public class WeChatUtils{
	
	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return 返回微信服务器响应的信息
	 */
	public static String httpRequestString(String requestUrl,
			String requestMethod, String reqParam) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new X509TrustManagerImpl() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != reqParam) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(reqParam.getBytes(WechatConfig.CHARSET_UTF8));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, WechatConfig.CHARSET_UTF8);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			WechatLog.info("wechat response string: " + buffer.toString());
			return buffer.toString();
		} catch (ConnectException e) {
			WechatLog.error("connect to wechat server error:", e);
		} catch (Exception e) {
			WechatLog.error("wechat request error:", e);
		}
		return null;
	}
	
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String reqParam) {
		JSONObject jsonObject=new JSONObject();
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new X509TrustManagerImpl() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != reqParam) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(reqParam.getBytes(WechatConfig.CHARSET_UTF8));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, WechatConfig.CHARSET_UTF8);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			WechatLog.info("wechat response string: " + buffer.toString());
			jsonObject=JSONObject.fromObject(buffer.toString());
		} catch (ConnectException e) {
			WechatLog.error("connect to wechat server error:", e);
		} catch (Exception e) {
			WechatLog.error("wechat request error:", e);
		}
		return jsonObject;
	}
	
	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<Object,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)   
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + WechatConfig.KEY);  
        String sign = MD5Util.MD5Encode(sb.toString(), WechatConfig.CHARSET_UTF8);  
        return sign;  
    } 
	
	/**
	 * 使用SHA加密, JS-SDK使用权限签名算法 规则是:按参数名称a-z排序,遇到空值的参数不参加签名。 sign
	 */
	@SuppressWarnings("rawtypes")
	public static String createSignature(SortedMap<Object,Object> parameters)
	{
		StringBuffer sb = new StringBuffer();
		Set<?> es = parameters.entrySet();
		Iterator<?> it = es.iterator();
		while (it.hasNext())
		{
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!StringUtils.isEmpty(v) && !"sign".equals(k)
					&& !"key".equals(k))
			{
				sb.append(k + "=" + v + "&");
			}
		}
		String str = sb.substring(0, sb.length()-1);
		
		WechatLog.info("JS-SDK验签加密字符串"+str);
		
		String sign = DigestUtils.shaHex(str);
		return sign;
	}
	
	
	/**
	 * 获取appsecret
	 */
	public static AccessTicket getJsapiTicket() {
		AccessTicket accessTicket = null;
		String tAccessToken = TokenManager.getAccessToken();
		String requestUrl = WechatConfig.JSAPITICKET.replace("ACCESS_TOKEN", tAccessToken);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessTicket = new AccessTicket();
				accessTicket.setTicket(jsonObject.getString("ticket"));
			} catch (JSONException e) {
				e.printStackTrace();
				accessTicket = null; // 获取Ticket失败
				WechatLog.error("获取Ticket失败 errcode:{} errmsg:{}" + jsonObject.getInt("errcode")
						+ jsonObject.getString("errmsg"));
			}
		}
		return accessTicket;
	}

	/**
	 * 获取accessToken
	 */
	public static AccessToken getAccessToken() {
		AccessToken accessToken = null;
		String requestUrl = WechatConfig.ACCESSTOKENURL.replace("APPID", WechatConfig.APPID).replace("APPSECRET",
				WechatConfig.APPSECRET);

		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
			} catch (JSONException e) {
				accessToken = null; // 获取token失败
				WechatLog.error("获取token失败 errcode:{} errmsg:{}" + jsonObject.getInt("errcode")
						+ jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
}
