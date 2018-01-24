package com.fbee.modules.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class Orders {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.ORDER_NO
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String orderNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.TENANT_ID
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private Integer tenantId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.MEMBER_ID
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private Integer memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.AMOUNT
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private BigDecimal amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.ORDER_TIME
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private Date orderTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.ORDER_STATUS
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String orderStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.SERVICE_ITEM_CODE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String serviceItemCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.STAFF_ID
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private Integer staffId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.ORDER_SOURCE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String orderSource;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.ORDER_DEPOSIT
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private BigDecimal orderDeposit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.ORDER_BALANCE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private BigDecimal orderBalance;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.DEPOSIT_DATE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private Date depositDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.IDEPOSIT_OVER
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String idepositOver;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.IS_INTERVIEW
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String isInterview;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.PASS_VIEW_DATE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private Date passViewDate;

    /**
     * 表：orders
     * 字段：BALANCE_DATE
     * 注释：尾款支付时间
     *
     * @mbggenerated
     */
    private Date balanceDate;

    /**
     * 表：orders
     * 字段：IS_LOCK
     * 注释：是否锁定(预留字段)
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.BALANCE_DATE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */

    private String isLock;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.add_time
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.add_account
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String addAccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.modify_time
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.modify_account
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String modifyAccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.RESERVE_ORDER_NO
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String reserveOrderNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.ORDER_TYPE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String orderType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.MEMBER_MOBILE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String memberMobile;

    /**
     * 表：orders
     * 字段：REMARK
     * 注释：
     *
     * @mbggenerated
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.REMARK
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String remark;

    /**
     * 表：orders
     * 字段：BALANCE_OVER
     * 注释：尾款是否支付
     *
     * @mbggenerated
     */
    private String balanceOver;

    private String shareOrderNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.BALANCE_OVER
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.is_usable
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private String isUsable;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.CANCLE_REASON
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    private BigDecimal serviceCharge;

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    private String cancleReason;

    private Integer userId;
    
    private String  userName;
    
    private String  userType;
    
    private String shareName;
    
    private Integer shareTenantId;
    
    //关联服务工种id @zsq
    private Integer stafffSerItemId;

    /**
     * 是否已发布职位1是0否
     */
    private Integer isPublishedJob;

    /**
     * 是否本地阿姨1是0否
     */
    private Integer isLocalStaff;

    public Integer getIsLocalStaff() {
        return isLocalStaff;
    }

    public void setIsLocalStaff(Integer isLocalStaff) {
        this.isLocalStaff = isLocalStaff;
    }

    public Integer getIsPublishedJob() {
        return isPublishedJob;
    }

    public void setIsPublishedJob(Integer isPublishedJob) {
        this.isPublishedJob = isPublishedJob;
    }

    public Integer getStafffSerItemId() {
		return stafffSerItemId;
	}

	public void setStafffSerItemId(Integer stafffSerItemId) {
		this.stafffSerItemId = stafffSerItemId;
	}

	public String getShareName() {
		return shareName;
	}

	public void setShareName(String shareName) {
		this.shareName = shareName;
	}

	public Integer getShareTenantId() {
		return shareTenantId;
	}

	public void setShareTenantId(Integer shareTenantId) {
		this.shareTenantId = shareTenantId;
	}

	// xiehui 新增分享人id
    private Integer shareId;

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.ORDER_NO
     *
     * @return the value of orders.ORDER_NO
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.ORDER_NO
     *
     * @param orderNo the value for orders.ORDER_NO
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.TENANT_ID
     *
     * @return the value of orders.TENANT_ID
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public Integer getTenantId() {
        return tenantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.TENANT_ID
     *
     * @param tenantId the value for orders.TENANT_ID
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.MEMBER_ID
     *
     * @return the value of orders.MEMBER_ID
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.MEMBER_ID
     *
     * @param memberId the value for orders.MEMBER_ID
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.AMOUNT
     *
     * @return the value of orders.AMOUNT
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.AMOUNT
     *
     * @param amount the value for orders.AMOUNT
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.ORDER_TIME
     *
     * @return the value of orders.ORDER_TIME
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.ORDER_TIME
     *
     * @param orderTime the value for orders.ORDER_TIME
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.ORDER_STATUS
     *
     * @return the value of orders.ORDER_STATUS
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.ORDER_STATUS
     *
     * @param orderStatus the value for orders.ORDER_STATUS
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.SERVICE_ITEM_CODE
     *
     * @return the value of orders.SERVICE_ITEM_CODE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getServiceItemCode() {
        return serviceItemCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.SERVICE_ITEM_CODE
     *
     * @param serviceItemCode the value for orders.SERVICE_ITEM_CODE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setServiceItemCode(String serviceItemCode) {
        this.serviceItemCode = serviceItemCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.STAFF_ID
     *
     * @return the value of orders.STAFF_ID
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public Integer getStaffId() {
        return staffId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.STAFF_ID
     *
     * @param staffId the value for orders.STAFF_ID
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.ORDER_SOURCE
     *
     * @return the value of orders.ORDER_SOURCE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getOrderSource() {
        return orderSource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.ORDER_SOURCE
     *
     * @param orderSource the value for orders.ORDER_SOURCE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.ORDER_DEPOSIT
     *
     * @return the value of orders.ORDER_DEPOSIT
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public BigDecimal getOrderDeposit() {
        return orderDeposit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.ORDER_DEPOSIT
     *
     * @param orderDeposit the value for orders.ORDER_DEPOSIT
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setOrderDeposit(BigDecimal orderDeposit) {
        this.orderDeposit = orderDeposit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.ORDER_BALANCE
     *
     * @return the value of orders.ORDER_BALANCE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public BigDecimal getOrderBalance() {
        return orderBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.ORDER_BALANCE
     *
     * @param orderBalance the value for orders.ORDER_BALANCE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setOrderBalance(BigDecimal orderBalance) {
        this.orderBalance = orderBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.DEPOSIT_DATE
     *
     * @return the value of orders.DEPOSIT_DATE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public Date getDepositDate() {
        return depositDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.DEPOSIT_DATE
     *
     * @param depositDate the value for orders.DEPOSIT_DATE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setDepositDate(Date depositDate) {
        this.depositDate = depositDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.IDEPOSIT_OVER
     *
     * @return the value of orders.IDEPOSIT_OVER
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getIdepositOver() {
        return idepositOver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.IDEPOSIT_OVER
     *
     * @param idepositOver the value for orders.IDEPOSIT_OVER
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setIdepositOver(String idepositOver) {
        this.idepositOver = idepositOver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.IS_INTERVIEW
     *
     * @return the value of orders.IS_INTERVIEW
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getIsInterview() {
        return isInterview;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.IS_INTERVIEW
     *
     * @param isInterview the value for orders.IS_INTERVIEW
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setIsInterview(String isInterview) {
        this.isInterview = isInterview;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.PASS_VIEW_DATE
     *
     * @return the value of orders.PASS_VIEW_DATE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public Date getPassViewDate() {
        return passViewDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.PASS_VIEW_DATE
     *
     * @param passViewDate the value for orders.PASS_VIEW_DATE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setPassViewDate(Date passViewDate) {
        this.passViewDate = passViewDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.BALANCE_DATE
     *
     * @return the value of orders.BALANCE_DATE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public Date getBalanceDate() {
        return balanceDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.BALANCE_DATE
     *
     * @param balanceDate the value for orders.BALANCE_DATE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.IS_LOCK
     *
     * @return the value of orders.IS_LOCK
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getIsLock() {
        return isLock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.IS_LOCK
     *
     * @param isLock the value for orders.IS_LOCK
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.add_time
     *
     * @return the value of orders.add_time
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.add_time
     *
     * @param addTime the value for orders.add_time
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.add_account
     *
     * @return the value of orders.add_account
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getAddAccount() {
        return addAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.add_account
     *
     * @param addAccount the value for orders.add_account
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setAddAccount(String addAccount) {
        this.addAccount = addAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.modify_time
     *
     * @return the value of orders.modify_time
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.modify_time
     *
     * @param modifyTime the value for orders.modify_time
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.modify_account
     *
     * @return the value of orders.modify_account
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getModifyAccount() {
        return modifyAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.modify_account
     *
     * @param modifyAccount the value for orders.modify_account
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setModifyAccount(String modifyAccount) {
        this.modifyAccount = modifyAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.RESERVE_ORDER_NO
     *
     * @return the value of orders.RESERVE_ORDER_NO
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getReserveOrderNo() {
        return reserveOrderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.RESERVE_ORDER_NO
     *
     * @param reserveOrderNo the value for orders.RESERVE_ORDER_NO
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setReserveOrderNo(String reserveOrderNo) {
        this.reserveOrderNo = reserveOrderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.ORDER_TYPE
     *
     * @return the value of orders.ORDER_TYPE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.ORDER_TYPE
     *
     * @param orderType the value for orders.ORDER_TYPE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.MEMBER_MOBILE
     *
     * @return the value of orders.MEMBER_MOBILE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getMemberMobile() {
        return memberMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.MEMBER_MOBILE
     *
     * @param memberMobile the value for orders.MEMBER_MOBILE
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.REMARK
     *
     * @return the value of orders.REMARK
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.REMARK
     *
     * @param remark the value for orders.REMARK
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.SHARE_ORDER_NO
     *
     * @return the value of orders.SHARE_ORDER_NO
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getShareOrderNo() {
        return shareOrderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.SHARE_ORDER_NO
     *
     * @param shareOrderNo the value for orders.SHARE_ORDER_NO
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setShareOrderNo(String shareOrderNo) {
        this.shareOrderNo = shareOrderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.BALANCE_OVER
     *
     * @return the value of orders.BALANCE_OVER
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getBalanceOver() {
        return balanceOver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.BALANCE_OVER
     *
     * @param balanceOver the value for orders.BALANCE_OVER
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setBalanceOver(String balanceOver) {
        this.balanceOver = balanceOver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.is_usable
     *
     * @return the value of orders.is_usable
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getIsUsable() {
        return isUsable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.is_usable
     *
     * @param isUsable the value for orders.is_usable
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.CANCLE_REASON
     *
     * @return the value of orders.CANCLE_REASON
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public String getCancleReason() {
        return cancleReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.CANCLE_REASON
     *
     * @param cancleReason the value for orders.CANCLE_REASON
     *
     * @mbggenerated Tue Feb 28 17:31:39 CST 2017
     */
    public void setCancleReason(String cancleReason) {
        this.cancleReason = cancleReason;
    }


}