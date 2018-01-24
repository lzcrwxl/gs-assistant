package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FinanceRecords implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：finance_records
     * 字段：ID
     * 注释：
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：finance_records
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：finance_records
     * 字段：remarks
     * 注释：备注
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 表：finance_records
     * 字段：is_usable
     * 注释：是否可用
     *
     * @mbggenerated
     */
    private String isUsable;

    /**
     * 表：finance_records
     * 字段：add_time
     * 注释：添加时间(收支时间)
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：finance_records
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：finance_records
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：finance_records
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

    /**
     * 表：finance_records
     * 字段：IN_OUT_CATEGORY
     * 注释：收支类目
     *
     * @mbggenerated
     */
    private String inOutCategory;

    /**
     * 表：finance_records
     * 字段：IN_OUT_TYPE
     * 注释：收支类型
     *
     * @mbggenerated
     */
    private String inOutType;

    /**
     * 表：finance_records
     * 字段：IN_OUT_MANTISSA
     * 注释：收支尾数
     *
     * @mbggenerated
     */
    private String inOutMantissa;

    /**
     * 表：finance_records
     * 字段：IN_OUT_OBJECT
     * 注释：收支对象
     *
     * @mbggenerated
     */
    private String inOutObject;

    /**
     * 表：finance_records
     * 字段：IN_OUT_AMOUNT
     * 注释：收支金额
     *
     * @mbggenerated
     */
    private BigDecimal inOutAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable == null ? null : isUsable.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddAccount() {
        return addAccount;
    }

    public void setAddAccount(String addAccount) {
        this.addAccount = addAccount == null ? null : addAccount.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyAccount() {
        return modifyAccount;
    }

    public void setModifyAccount(String modifyAccount) {
        this.modifyAccount = modifyAccount == null ? null : modifyAccount.trim();
    }

    public String getInOutCategory() {
        return inOutCategory;
    }

    public void setInOutCategory(String inOutCategory) {
        this.inOutCategory = inOutCategory == null ? null : inOutCategory.trim();
    }

    public String getInOutType() {
        return inOutType;
    }

    public void setInOutType(String inOutType) {
        this.inOutType = inOutType == null ? null : inOutType.trim();
    }

    public String getInOutMantissa() {
        return inOutMantissa;
    }

    public void setInOutMantissa(String inOutMantissa) {
        this.inOutMantissa = inOutMantissa == null ? null : inOutMantissa.trim();
    }

    public String getInOutObject() {
        return inOutObject;
    }

    public void setInOutObject(String inOutObject) {
        this.inOutObject = inOutObject == null ? null : inOutObject.trim();
    }

    public BigDecimal getInOutAmount() {
        return inOutAmount;
    }

    public void setInOutAmount(BigDecimal inOutAmount) {
        this.inOutAmount = inOutAmount;
    }
}