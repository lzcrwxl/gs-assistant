package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class TenantsRolesMenusKey implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_roles_menus
     * 字段：menu_id
     * 注释：菜单id
     *
     * @mbggenerated
     */
    private Integer menuId;

    /**
     * 表：tenants_roles_menus
     * 字段：role_id
     * 注释：角色id
     *
     * @mbggenerated
     */
    private Integer roleId;

    /**
     * 表：tenants_roles_menus
     * 字段：tenant_id
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
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