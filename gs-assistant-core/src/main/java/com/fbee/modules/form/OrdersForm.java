package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;
import org.apache.commons.lang.StringUtils;

public class OrdersForm implements ModelSerializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 表：orders
     * 字段：ORDER_NO
     * 注释：订单流水号
     *
     * @mbggenerated
     */
    private String orderNo;
	
    /**
     * 表：orders
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;
    
    /**
     * 表：orders
     * 字段：ORDER_STATUS
     * 注释：订单状态 01待支付定金 02 待面试 03 待支付尾款 04 订单完成 05 退款
     *
     * @mbggenerated
     */
	private String orderStatus;

	/**
	 * 订单查询：状态筛选
	 * process: 01,02,03,06
	 * finish: 04
	 * cancel: 05
	 */
	private String queryStatus;
    
    /**
     * 表：orders
     * 字段：SERVICE_ITEM_CODE
     * 注释：服务工种
     *
     * @mbggenerated
     */
	private String serviceItemCode;

	/**
	 * 表：order_customers_info
	 * 字段：SERVICE_PROVINCE
	 * 注释：服务区域
	 */
	private String serviceProvince;
    
    /**
     * 表：orders
     * 字段：ORDER_SOURCE
     * 注释：订单来源
     *
     * @mbggenerated
     */
    private String orderSource;
    
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
     * 注释：条件查询手机号
     *
     * @mbggenerated
     */
    private String memberMobile;
    
    /**
     * 页数
     */
    private Integer pageNum;
    
    /**
     * 页面大小
     */
    private Integer pageSize;

	private String addAccount;//操作员
	
	private Integer userId;//操作员

	public String getServiceProvince() {
		return serviceProvince;
	}

	public String getQueryStatus() {
		return queryStatus;
	}

	/**
	 * 订单查询：状态筛选
	 * process: 01,02,03,06
	 * finish: 04
	 * cancel: 05
	 */
	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
		if("process".equals(queryStatus)){
			this.queryStatus = "01,02,03,06";
		} else if("finish".equals(queryStatus)){
			this.queryStatus = "04";
		} else if("cancel".equals(queryStatus)){
			this.queryStatus = "05";
		}
	}

	public void setServiceProvince(String serviceProvince) {
		this.serviceProvince = serviceProvince;
	}

	public String getAddAccount() {
		return addAccount;
	}

	public void setAddAccount(String addAccount) {
		this.addAccount = addAccount;
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

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
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


}
