package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class CtlRolesMenusKey implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：ctl_roles_menus
     * 字段：menu_id
     * 注释：菜单id
     *
     * @mbggenerated
     */
    private Integer menuId;

    /**
     * 表：ctl_roles_menus
     * 字段：role_id
     * 注释：角色id
     *
     * @mbggenerated
     */
    private Integer roleId;

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
}