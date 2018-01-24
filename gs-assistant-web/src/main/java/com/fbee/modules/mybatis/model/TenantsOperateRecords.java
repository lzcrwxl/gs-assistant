package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class TenantsOperateRecords implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_operate_records
     * 字段：id
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：tenants_operate_records
     * 字段：operate_account
     * 注释：操作人
     *
     * @mbggenerated
     */
    private String operateAccount;

    /**
     * 表：tenants_operate_records
     * 字段：operate_time
     * 注释：操作时间
     *
     * @mbggenerated
     */
    private Date operateTime;

    /**
     * 表：tenants_operate_records
     * 字段：action
     * 注释：操作动作
     *
     * @mbggenerated
     */
    private String action;

    /**
     * 表：tenants_operate_records
     * 字段：tables
     * 注释：操作表
     *
     * @mbggenerated
     */
    private String tables;

    /**
     * 表：tenants_operate_records
     * 字段：UPDATE_CONTENTS
     * 注释：更新内容
     *
     * @mbggenerated
     */
    private String updateContents;

    /**
     * 表：tenants_operate_records
     * 字段：TENANT_ID
     * 注释：租户ID
     *
     * @mbggenerated
     */
    private Integer tenantId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperateAccount() {
        return operateAccount;
    }

    public void setOperateAccount(String operateAccount) {
        this.operateAccount = operateAccount == null ? null : operateAccount.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables == null ? null : tables.trim();
    }

    public String getUpdateContents() {
        return updateContents;
    }

    public void setUpdateContents(String updateContents) {
        this.updateContents = updateContents == null ? null : updateContents.trim();
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }
}