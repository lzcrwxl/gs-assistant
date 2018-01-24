package com.fbee.modules.wechat.core;

import com.fbee.modules.wechat.bean.OAuthTokenBean;
import com.fbee.modules.wechat.bean.WechatLog;
import com.fbee.modules.wechat.bean.WechatUserinfoBean;
import com.fbee.modules.wechat.config.WechatConfig;
import com.fbee.modules.wechat.utils.WeChatUtils;
import net.sf.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WechatAuthrize {
	/**
	 * 微信授权链接
	 */
	public static final String AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
	/**
	 * 微信取access_token的url
	 */
	public static final String AUTH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	/**
	 * 微信取用户信息的url
	 */
	public static final String AUTH_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo";
	/**
	 * 刷新access_token的url
	 */
	public static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	public static OAuthTokenBean getAccessToken(String code) {
		try {
			StringBuilder requestUrl = new StringBuilder(AUTH_ACCESS_TOKEN_URL);
			requestUrl.append("?").append("appid=").append(WechatConfig.APPID).append("&secret=").append(WechatConfig.APPSECRET)
				.append("&code=").append(code).append("&grant_type=").append("authorization_code");
			
			JSONObject respToken = WeChatUtils.httpRequest(requestUrl.toString(), "GET", null);
			
			if (respToken == null) {
				WechatLog.error("request error, url = " + requestUrl.toString());
				return null;
			}
			if (respToken.has("openid")) {
				String accessToken = respToken.getString("access_token");
				int expiresIn = respToken.getInt("expires_in");
				String refreshToken = respToken.getString("refresh_token");
				String scope = respToken.getString("scope");
				String openId = respToken.getString("openid");
				
				OAuthTokenBean token = new OAuthTokenBean();
				token.setAccessToken(accessToken);
				token.setExpiresIn(expiresIn);
				token.setRefreshToken(refreshToken);
				token.setOpenId(openId);
				token.setScope(scope);
				
				WechatLog.info(token);
				
				return token;
			} else {
				if (respToken.has("errcode")){
					WechatLog.error("base code 认证失败：errcode=" + respToken.getInt("errcode") + "; errmsg=" + respToken.getString("errmsg"));
				}

				return null;
			}
		} catch (Exception e) {
			WechatLog.error("getAccessToken error:", e);
			return null;
		}
	}
	
	public static WechatUserinfoBean getWechatUserinfo(String accessToken, String openId, String lang) {
		try {
			WechatUserinfoBean userinfo = new WechatUserinfoBean();
			
			StringBuilder requestUrl = new StringBuilder(AUTH_GET_USERINFO);
			requestUrl.append("?access_token=").append(accessToken).append("&openid=").append(openId).append("&lang=").append(lang);
			
			JSONObject respToken = WeChatUtils.httpRequest(requestUrl.toString(), "GET", null);
			
			if (respToken == null) {
				WechatLog.error("request error, url = " + requestUrl.toString());
				return null;
			}
			
			if (respToken.has("openid")) {
				String nickname = respToken.getString("nickname");
				String sex = respToken.getString("sex");
				String province = respToken.getString("province");
				String city = respToken.getString("city");
				String country = respToken.getString("country");
				String headImgUrl = respToken.getString("headimgurl");
				Object[] privilege = respToken.getJSONArray("privilege").toArray();
				
				userinfo.setOpenid(openId);
				userinfo.setNickname(emojiFilter(nickname));
				userinfo.setSex(sex);
				userinfo.setProvince(province);
				userinfo.setCity(city);
				userinfo.setCountry(country);
				userinfo.setHeadImgUrl(headImgUrl);
				userinfo.setPrivilege(privilege);
				
				if (respToken.has("unionid")) {
					userinfo.setUnionid(respToken.getString("unionid"));
				}
				
				WechatLog.info(userinfo);
				
				return userinfo;
			} else {
				if (respToken.has("errcode")){
					WechatLog.error("userinfo code 认证失败：errcode=" + respToken.getInt("errcode") + "; errmsg=" + respToken.getString("errmsg"));
				}

				return null;
			}
			
		} catch (Exception e) {
			WechatLog.error("getWechatUserinfo error:", e);
			return null;
		}
	}
	
	public static String emojiFilter(String str) {
		Pattern emoji = Pattern
				.compile(
						"[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
						Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Matcher emojiMatcher = emoji.matcher(str);
		return emojiMatcher.replaceAll("");
	}
	
	/**
	 * 刷新access_token
	 * @param refToken
	 * @return
	 */
	public static OAuthTokenBean refreshAccessToken(String refToken) {
		try {
			StringBuilder requestUrl = new StringBuilder(REFRESH_TOKEN_URL);
			requestUrl.append("?appid=").append(WechatConfig.APPID).append("&grant_type=refresh_token&refresh_token=").append(refToken);
			JSONObject respToken = WeChatUtils.httpRequest(requestUrl.toString(), "GET", null);
			
			if (respToken == null) {
				WechatLog.error("request error, url = " + requestUrl.toString());
				return null;
			}
			if (respToken.has("openid")) {
				String accessToken = respToken.getString("access_token");
				int expiresIn = respToken.getInt("expires_in");
				String refreshToken = respToken.getString("refresh_token");
				String scope = respToken.getString("scope");
				String openId = respToken.getString("openid");
				
				OAuthTokenBean token = new OAuthTokenBean();
				token.setAccessToken(accessToken);
				token.setExpiresIn(expiresIn);
				token.setRefreshToken(refreshToken);
				token.setOpenId(openId);
				token.setScope(scope);
				
				WechatLog.info(token);
				
				return token;
			} else {
				if (respToken.has("errcode")){
					WechatLog.error("base code 认证失败：errcode=" + respToken.getInt("errcode") + "; errmsg=" + respToken.getString("errmsg"));
				}
				return null;
			}
		} catch (Exception e) {
			WechatLog.error("refreshAccessToken error:", e);
			return null;
		}
		
	}
	
	/**
	 * 获取微信授权登陆链接
	 * @return
	 */
	public static String getWechatAuthUrl(String redirectUrl){
		StringBuilder url = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
		url.append(WechatConfig.APPID).append("&redirect_uri=").append(redirectUrl)
			.append("&response_type=code&scope=snsapi_base&state=fbeeWebconsole#wechat_redirect");
		return url.toString();
	}
}
