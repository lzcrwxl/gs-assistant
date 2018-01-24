package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

public class OrderShareInfoForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private Integer userId;//用户id
	
	private String userAccount;//用户账号
	
	private Integer tenantId;//租户id
	
	private String orderNo;//订单流水号
	
	private String orderName;//订单名称
	
	private String ageRequirement;//年龄要求
	
	private String salarySituation;//工资情况
	
	private String remark;//备注
	
	private String singleReward;//成单奖励

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getAgeRequirement() {
		return ageRequirement;
	}

	public void setAgeRequirement(String ageRequirement) {
		this.ageRequirement = ageRequirement;
	}

	public String getSalarySituation() {
		return salarySituation;
	}

	public void setSalarySituation(String salarySituation) {
		this.salarySituation = salarySituation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSingleReward() {
		return singleReward;
	}

	public void setSingleReward(String singleReward) {
		this.singleReward = singleReward;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

}
