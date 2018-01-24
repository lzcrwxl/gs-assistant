package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class ApplyAppFuncs implements Serializable{
  
	private static final long serialVersionUID = 1L;

	/**
     * 表：apply_app_funcs
     * 字段：id
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：apply_app_funcs
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：apply_app_funcs
     * 字段：MODULE_TYPE
     * 注释：申请模块类型
     *
     * @mbggenerated
     */
    private String moduleType;

    /**
     * 表：apply_app_funcs
     * 字段：MODULE_ID
     * 注释：模块ID
     *
     * @mbggenerated
     */
    private Integer moduleId;

    /**
     * 表：apply_app_funcs
     * 字段：FEE_TYPE
     * 注释：收费机制
     *
     * @mbggenerated
     */
    private String feeType;

    /**
     * 表：apply_app_funcs
     * 字段：DUE_TIME
     * 注释：到期时间
     *
     * @mbggenerated
     */
    private Date dueTime;

    /**
     * 表：apply_app_funcs
     * 字段：APPLY_TIME
     * 注释：申请时间
     *
     * @mbggenerated
     */
    private Date applyTime;

    /**
     * 表：apply_app_funcs
     * 字段：STATUS
     * 注释：处理状态
     *
     * @mbggenerated
     */
    private String status;

    /**
     * 表：apply_app_funcs
     * 字段：FAILURE_REASON
     * 注释：处理原因
     *
     * @mbggenerated
     */
    private String failureReason;

    /**
     * 表：apply_app_funcs
     * 字段：remarks
     * 注释：备注
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 表：apply_app_funcs
     * 字段：is_usable
     * 注释：是否可用
     *
     * @mbggenerated
     */
    private String isUsable;

    /**
     * 表：apply_app_funcs
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：apply_app_funcs
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：apply_app_funcs
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：apply_app_funcs
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

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

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType == null ? null : moduleType.trim();
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason == null ? null : failureReason.trim();
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
}