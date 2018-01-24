package com.fbee.modules.mybatis.entity;

import com.fbee.modules.mybatis.model.OrderChangehsRecords;

import java.util.Date;

public class OrderChangehsRecordsEntity extends OrderChangehsRecords {
    
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String orderNo;
	private String hsRemark;
	private Date hsApplicationTime;
	private Date hsHandlingTime;
	private Integer mxId;

	@Override
	public Integer getMxId() {
		return mxId;
	}

	@Override
	public void setMxId(Integer mxId) {
		this.mxId = mxId;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getOrderNo() {
		return orderNo;
	}

	@Override
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String getHsRemark() {
		return hsRemark;
	}

	@Override
	public void setHsRemark(String hsRemark) {
		this.hsRemark = hsRemark;
	}

	@Override
	public Date getHsApplicationTime() {
		return hsApplicationTime;
	}

	@Override
	public void setHsApplicationTime(Date hsApplicationTime) {
		this.hsApplicationTime = hsApplicationTime;
	}

	@Override
	public Date getHsHandlingTime() {
		return hsHandlingTime;
	}

	@Override
	public void setHsHandlingTime(Date hsHandlingTime) {
		this.hsHandlingTime = hsHandlingTime;
	}
}