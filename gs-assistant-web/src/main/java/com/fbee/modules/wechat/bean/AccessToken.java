package com.fbee.modules.wechat.bean;


import com.fbee.modules.wechat.config.WechatConfig;

public class AccessToken
{
	// 获取到的凭证
	private String token;
	
	// 凭证有效时间，单位：秒
	private int expiresIn = WechatConfig.EXPIRESIN;
	
	//到期时间毫秒
	private long expiresDate =  0;
	
	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public int getExpiresIn()
	{
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn)
	{
		this.expiresIn = expiresIn;
	}

	public long getExpiresDate()
	{
		return expiresDate;
	}

	public void setExpiresDate(long expiresDate)
	{
		this.expiresDate = expiresDate;
	}
}
