package com.fbee.modules.form;

import java.math.BigDecimal;
import java.util.Date;

public class StaffFinancialForm {
    private String inOutNo; //交易流水号
    private String payType;//交易类型
    private String inOutType;//收支类型
    private BigDecimal inOutAmount;//收支金额

    private String isUsable; //是否可用
    private String status;//状态
    private String transType;//交易方式
	private Integer tenantId; // 租户id
	private Integer staffId;// 阿姨id
	private String addTime;//  添加时间
	private String addAccount;// 操作人
	private String remarks;// 备注

	private String financeTime; //财务记录时间
	private String inOutObject ;//收支对象

	public String getInOutObject() {
		return inOutObject;
	}

	public void setInOutObject(String inOutObject) {
		this.inOutObject = inOutObject;
	}


	public String getFinanceTime() {
		return financeTime;
	}

	public void setFinanceTime(String financeTime) {
		this.financeTime = financeTime;
	}

	public String getInOutNo() {
		return inOutNo;
	}

	public void setInOutNo(String inOutNo) {
		this.inOutNo = inOutNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}



	public String getInOutType() {
		return inOutType;
	}

	public void setInOutType(String inOutType) {
		this.inOutType = inOutType;
	}

	public BigDecimal getInOutAmount() {
		return inOutAmount;
	}

	public void setInOutAmount(BigDecimal inOutAmount) {
		this.inOutAmount = inOutAmount;
	}

	public String getIsUsable() {
		return isUsable;
	}

	public void setIsUsable(String isUsable) {
		this.isUsable = isUsable;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}


	public String getAddTime() {
		return addTime;
	}

	public String getAddAccount() {
		return addAccount;
	}

	public void setAddAccount(String addAccount) {
		this.addAccount = addAccount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
