package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class TenantsMenusKey implements Serializable{
   
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_menus
     * 字段：id
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：tenants_menus
     * 字段：tenant_id
     * 注释：租户id
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

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }
}