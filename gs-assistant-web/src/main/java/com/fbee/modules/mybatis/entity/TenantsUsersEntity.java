package com.fbee.modules.mybatis.entity;

import java.util.List;

import com.fbee.modules.mybatis.model.SysMenu;
import com.fbee.modules.mybatis.model.TenantsUsers;

/**
 * 门店后台账户
 * @author Administrator
 *
 */
public class TenantsUsersEntity extends TenantsUsers{
    
	private static final long serialVersionUID = 1L;
	
	private List<SysMenu> sysMenuList;

	public List<SysMenu> getSysMenuList() {
		return sysMenuList;
	}

	public void setSysMenuList(List<SysMenu> sysMenuList) {
		this.sysMenuList = sysMenuList;
	}


}