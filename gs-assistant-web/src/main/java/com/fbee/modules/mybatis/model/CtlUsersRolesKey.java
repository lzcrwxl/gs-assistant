package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class CtlUsersRolesKey implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：ctl_users_roles
     * 字段：user_id
     * 注释：用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 表：ctl_users_roles
     * 字段：role_id
     * 注释：角色id
     *
     * @mbggenerated
     */
    private Integer roleId;

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
}