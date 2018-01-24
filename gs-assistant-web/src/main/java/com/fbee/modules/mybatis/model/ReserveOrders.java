package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class ReserveOrders implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 表：reserve_orders
     * 字段：ORDER_NO
     * 注释：订单流水号
     *
     * @mbggenerated
     */
    private String orderNo;

    /**
     * 表：reserve_orders
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：reserve_orders
     * 字段：MEMBER_ID
     * 注释：客户ID
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 表：reserve_orders
     * 字段：MEMBER_MOBILE
     * 注释：手机号码
     *
     * @mbggenerated
     */
    private String memberMobile;

    /**
     * 表：reserve_orders
     * 字段：MEMBER_NAME
     * 注释：客户姓名
     *
     * @mbggenerated
     */
    private String memberName;

    /**
     * 表：reserve_orders
     * 字段：MEMBER_SEX
     * 注释：客户性别
     *
     * @mbggenerated
     */
    private String memberSex;

    /**
     * 表：reserve_orders
     * 字段：STAFF_ID
     * 注释：阿姨id
     *
     * @mbggenerated
     */
    private Integer staffId;

    /**
     * 表：reserve_orders
     * 字段：SERVICE_ITEM_CODE
     * 注释：服务工种
     *
     * @mbggenerated
     */
    private String serviceItemCode;

    /**
     * 表：reserve_orders
     * 字段：ORDER_TIME
     * 注释：下单时间
     *
     * @mbggenerated
     */
    private Date orderTime;

    /**
     * 表：reserve_orders
     * 字段：ORDER_STATUS
     * 注释：预约状态 01待处理 02 已处理 03 已取消
     *
     * @mbggenerated
     */
    private String orderStatus;

    /**
     * 表：reserve_orders
     * 字段：HANDLE_ORDER_TIME
     * 注释：
     *
     * @mbggenerated
     */
    private Date handleOrderTime;

    /**
     * 更新时间／处理时间
     */
    private Date modifyTime;

    private String remark;

    //@zsq
    private Integer staffSerItemId;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStaffSerItemId() {
		return staffSerItemId;
	}

	public void setStaffSerItemId(Integer staffSerItemId) {
		this.staffSerItemId = staffSerItemId;
	}

	public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile == null ? null : memberMobile.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(String memberSex) {
        this.memberSex = memberSex == null ? null : memberSex.trim();
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getServiceItemCode() {
        return serviceItemCode;
    }

    public void setServiceItemCode(String serviceItemCode) {
        this.serviceItemCode = serviceItemCode == null ? null : serviceItemCode.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public Date getHandleOrderTime() {
        return handleOrderTime;
    }

    public void setHandleOrderTime(Date handleOrderTime) {
        this.handleOrderTime = handleOrderTime;
    }
}