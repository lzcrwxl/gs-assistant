package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class TenantsStaffSerItemsKey implements Serializable{
    
	private static final long serialVersionUID = 1L;

	
	/**
     * 表：tenants_staff_ser_items
     * 字段：id
     * 注释：id
     *
     * @mbggenerated
     */
    private Integer id;
	/**
     * 表：tenants_staff_ser_items
     * 字段：STAFF_ID
     * 注释：员工id
     *
     * @mbggenerated
     */
    private Integer staffId;
    

    /**
     * 表：tenants_staff_ser_items
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：tenants_staff_ser_items
     * 字段：SERVICE_ITEM_CODE
     * 注释：服务工种
     *
     * @mbggenerated
     */
    private String serviceItemCode;
    
    private String isUsable;

    public String getIsUsable() {
		return isUsable;
	}

	public void setIsUsable(String isUsable) {
		this.isUsable = isUsable;
	}

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

    public String getServiceItemCode() {
        return serviceItemCode;
    }

    public void setServiceItemCode(String serviceItemCode) {
        this.serviceItemCode = serviceItemCode == null ? null : serviceItemCode.trim();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}