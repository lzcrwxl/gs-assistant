package com.fbee.modules.wechat.bean;


import com.fbee.modules.wechat.utils.WeChatUtils;

import java.util.Date;

public class TokenManager {

	public static AccessToken accessToken = null;
	
	public static AccessTicket accessTicket = null;

	public static long getCurentTime() {
		long nowTime = new Date().getTime();
		return nowTime;
	}
	
	public static synchronized String getAccessToken() 
	{
		
		if(accessToken == null || getCurentTime() > accessToken.getExpiresDate())
		{
			accessToken = WeChatUtils.getAccessToken();
			long nextExpireTime = getCurentTime() + accessToken.getExpiresIn()*1000;//转换为毫秒
			WechatLog.info("nextTokenExpireTime:" + nextExpireTime);
			accessToken.setExpiresDate(nextExpireTime);
		}
		
		if (null != accessToken)
		{
			WechatLog.info("获取access_token成功，有效时长{"+accessToken.getExpiresIn()+"}秒 token:{"+accessToken.getToken()+"}");
		}
		
		return accessToken.getToken();
	}

	public static synchronized String getAccessTicket() {

		if (accessTicket == null || getCurentTime() > accessTicket.getExpiresDate()) {
			accessTicket = WeChatUtils.getJsapiTicket();
			long nextExpireTime = getCurentTime() + accessTicket.getExpiresIn() * 1000;// 转换为毫秒
			WechatLog.info("nextTicketExpireTime:" + nextExpireTime);
			accessTicket.setExpiresDate(nextExpireTime);
		}

		if (null != accessTicket) {
			WechatLog.error("获取access_Ticket成功，有效时长{" + accessTicket.getExpiresIn() + "}秒 Ticket:{"
					+ accessTicket.getTicket() + "}");
		}
		return accessTicket.getTicket();
	}

}