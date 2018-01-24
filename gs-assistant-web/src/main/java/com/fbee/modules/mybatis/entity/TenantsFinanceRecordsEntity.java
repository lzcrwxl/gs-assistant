package com.fbee.modules.mybatis.entity;

import com.fbee.modules.mybatis.model.TenantsFinanceRecords;

public class TenantsFinanceRecordsEntity extends TenantsFinanceRecords {

	private static final long serialVersionUID = 1L;
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	private int pageNumber;// 当前页数
	private int pageSize;// 每页显示几条

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}