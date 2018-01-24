package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class TenantsServiceItemsKey implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_service_items
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：tenants_service_items
     * 字段：SERVICE_ITEM_CODE
     * 注释：服务项目代码
     *
     * @mbggenerated
     */
    private String serviceItemCode;

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
}