package com.fbee.modules.bean;

import java.util.Map;

/**
 * @ClassName: MatchSpecial
 * @Description: 用户存储--个人特点、服务内容计算值
 * @author 贺章鹏
 * @date 2017年2月8日 上午10:44:09
 * 
 */
public class MatchSpecial {

	// 个人特点
	private int doNotFamily;// 不做家庭

	//服务
	private Map<Integer, String> serviceContents;

	public Map<Integer, String> getServiceContents() {
		return serviceContents;
	}

	public void setServiceContents(Map<Integer, String> serviceContents) {
		this.serviceContents = serviceContents;
	}

	public int getDoNotFamily() {
		return doNotFamily;
	}

	public void setDoNotFamily(int doNotFamily) {
		this.doNotFamily = doNotFamily;
	}

}
