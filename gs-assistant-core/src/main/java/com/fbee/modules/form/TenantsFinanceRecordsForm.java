package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

public class TenantsFinanceRecordsForm implements ModelSerializable {

	private static final long serialVersionUID = 1L;
	private String startTime;// 收入或支出开始时间
	private String endTime;// 收入或支出结束时间
	private String inOutType;// 收支类型
	private int tenantsId;// 租户id
	private String inOutNo;// 流水账号
	private String draweeType;//身份类别
	public String getInOutNo() {
		return inOutNo;
	}

	public void setInOutNo(String inOutNo) {
		this.inOutNo = inOutNo;
	}
	
	public int getTenantsId() {
		return tenantsId;
	}

	public void setTenantsId(int tenantsId) {
		this.tenantsId = tenantsId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getInOutType() {
		return inOutType;
	}

	public void setInOutType(String inOutType) {
		this.inOutType = inOutType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDraweeType() {
		return draweeType;
	}

	public void setDraweeType(String draweeType) {
		this.draweeType = draweeType;
	}

}
