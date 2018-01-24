package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class TenantsUsersRoles implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_users_roles
     * 字段：user_id
     * 注释：用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 表：tenants_users_roles
     * 字段：role_id
     * 注释：角色id
     *
     * @mbggenerated
     */
    private Integer roleId;

    /**
     * 表：tenants_users_roles
     * 字段：tenant_id
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }
}