package com.fbee.modules.wechat.bean;


import com.fbee.modules.wechat.config.WechatConfig;

public class AccessTicket
{
	// 获取到的凭证
	private String ticket;
	
	// 凭证有效时间，单位：秒
	private int expiresIn = WechatConfig.EXPIRESIN;
	
	//到期时间毫秒
	private long expiresDate =  0;
	
	public String getTicket()
	{
		return ticket;
	}

	public void setTicket(String ticket)
	{
		this.ticket = ticket;
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
