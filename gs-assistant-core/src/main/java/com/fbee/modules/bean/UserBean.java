package com.fbee.modules.bean;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: UserBean 
* @Description: 后端登陆用户bean--用户session
* @author 贺章鹏
* @date 2016年12月27日 下午5:36:18 
*  
*/
public class UserBean implements ModelSerializable{
	private static final long serialVersionUID = 1L;
	
	private Integer userId;//用户id
	
	private String loginAccount;//用户账号
	
	private String userName;//用户
	
	private Integer tenantId;//租户id
	
	private String domain;//租户二级域名

	private String openid;

	private String userType;
	
	private String mobile;
	
	private String unionId;

	/**
	 * 登陆秘要
	 */
	private String token;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
}
