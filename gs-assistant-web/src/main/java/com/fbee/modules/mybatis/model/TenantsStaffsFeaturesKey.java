package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class TenantsStaffsFeaturesKey implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_staffs_features
     * 字段：STAFF_ID
     * 注释：员工id
     *
     * @mbggenerated
     */
    private Integer staffId;

    /**
     * 表：tenants_staffs_features
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：tenants_staffs_features
     * 字段：FEATURE_CODE
     * 注释：特点CODE
     *
     * @mbggenerated
     */
    private String featureCode;

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

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode == null ? null : featureCode.trim();
    }
}