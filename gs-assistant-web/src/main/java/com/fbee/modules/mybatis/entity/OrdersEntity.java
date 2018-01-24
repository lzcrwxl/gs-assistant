package com.fbee.modules.mybatis.entity;

import com.fbee.modules.mybatis.model.Orders;
import org.apache.commons.lang.StringUtils;

public class OrdersEntity extends Orders{
    
	private static final long serialVersionUID = 1L;


    /**
     * 注释：条件查询开始时间
     *
     * @mbggenerated
     */
    private String startTime;
    
    /**
     * 注释：条件查询结束时间
     *
     * @mbggenerated
     */
    private String endTime;
    
    /**
     * 注释：条件查询客户姓名
     *
     * @mbggenerated
     */
    private String memberName;
    
    /**
     * 页数
     */
    private Integer pageNum;
    
    /**
     * 页面大小
     */
    private Integer pageSize;
    
    private String reserveOrderNo;

	private String membermobile;

	/**
	 * 订单查询：状态筛选
	 * process: 01,02,03,06
	 * finish: 04
	 * cancel: 05
	 */
	private String[] queryStatus;
	/**
	 * 服务区域
	 */
	private String serviceProvince;

	public String[] getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(String queryStatus) {
		if(StringUtils.isNotBlank(queryStatus)){
			this.queryStatus = queryStatus.split(",");
		}
	}

	public String getServiceProvince() {
		return serviceProvince;
	}

	public void setServiceProvince(String serviceProvince) {
		this.serviceProvince = serviceProvince;
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getReserveOrderNo() {
		return reserveOrderNo;
	}

	public void setReserveOrderNo(String reserveOrderNo) {
		this.reserveOrderNo = reserveOrderNo;
	}

	public String getMembermobile() {
		return membermobile;
	}

	public void setMembermobile(String membermobile) {
		this.membermobile = membermobile;
	}
	
}