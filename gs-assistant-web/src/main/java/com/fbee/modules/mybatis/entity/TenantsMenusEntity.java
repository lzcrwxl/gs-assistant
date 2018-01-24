package com.fbee.modules.mybatis.entity;

import com.fbee.modules.mybatis.model.TenantsMenus;

public class TenantsMenusEntity extends TenantsMenus {
    
	private static final long serialVersionUID = 1L;
	
	private TenantsMenusEntity parent;	// 父级菜单

	public TenantsMenusEntity getParent() {
		return parent;
	}

	public void setParent(TenantsMenusEntity parent) {
		this.parent = parent;
	}
}