package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class TenantsStaffBankKey implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_staff_bank
     * 字段：STAFF_ID
     * 注释：员工id
     *
     * @mbggenerated
     */
    private Integer staffId;

    /**
     * 表：tenants_staff_bank
     * 字段：TENANT_ID
     * 注释：租户ID
     *
     * @mbggenerated
     */
    private Integer tenantId;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }
}