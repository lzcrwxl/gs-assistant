package com.fbee.modules.operation;

import com.fbee.modules.form.SysMenuForm;
import com.fbee.modules.form.SysPermissionForm;
import com.fbee.modules.form.SysRoleForm;
import com.fbee.modules.form.SysRoleMenuForm;
import com.fbee.modules.form.TenantsUsersForm;
import com.fbee.modules.mybatis.entity.SysMenuEntity;
import com.fbee.modules.mybatis.entity.SysPermissionEntity;
import com.fbee.modules.mybatis.entity.SysRoleEntity;
import com.fbee.modules.mybatis.entity.SysRoleMenuEntity;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;


/**
 * 权限模块类操作
 * @author Administrator
 *
 */
public class PermissionOpt {
	
	/**
	 * 用户权限类
	 * @param record
	 * @return
	 */
	public static SysPermissionEntity buildSysPermissionEntity(SysPermissionForm record) {
		SysPermissionEntity entity = new SysPermissionEntity();
		entity.setId(record.getId());
		entity.setUserId(record.getUserId());
		entity.setMenuId(record.getMenuId());
		entity.setCreateDate(record.getCreateDate());
		entity.setState(record.getState());
		return entity;
	}
	
	/**
	 * 角色菜单
	 * @param record
	 * @return
	 */
	public static SysRoleMenuEntity buildSysRoleMenuEntity(SysRoleMenuForm record) {
		SysRoleMenuEntity entity = new SysRoleMenuEntity();
		entity.setId(record.getId());
		entity.setMenuId(record.getMenuId());
		entity.setRoleId(record.getRoleId());
		entity.setCreateDate(record.getCreateDate());
		entity.setState(record.getState());
		return entity;
	}
	
	/**
	 * 角色
	 * @param record
	 * @return
	 */
	public static SysRoleEntity buildSysRoleEntity(SysRoleForm record) {
		SysRoleEntity entity = new SysRoleEntity();
		entity.setId(record.getId());
		entity.setName(record.getName());
		entity.setCreateDate(record.getCreateDate());
		entity.setState(record.getState());
		return entity;
	}
	
	/**
	 * 租户用户
	 * @param record
	 * @return
	 */
	public static TenantsUsersEntity buildTenantsUsersEntity(TenantsUsersForm record) {
		TenantsUsersEntity entity = new TenantsUsersEntity();
		entity.setAddAccount(record.getAddAccount());
		entity.setAddTime(record.getAddTime());
		entity.setEmail(record.getEmail());
		entity.setId(record.getId());
		entity.setIsInit(record.getIsInit());
		entity.setIsUsable(record.getIsUsable());
		entity.setJobNo(record.getJobNo());
		entity.setLoginAccount(record.getLoginAccount());
		entity.setLoginFlag(record.getLoginFlag());
		entity.setLoginIp(record.getLoginIp());
		entity.setLoginName(record.getLoginName());
		entity.setLoginTime(record.getLoginTime());
		entity.setModifyAccount(record.getModifyAccount());
		entity.setModifyTime(record.getModifyTime());
		entity.setPassword(record.getPassword());
		entity.setPhoto(record.getPhoto());
		entity.setRemarks(record.getRemarks());
		entity.setSalt(record.getSalt());
		entity.setTelephone(record.getTelephone());
		entity.setTenantId(record.getTenantId());
		entity.setUserType(record.getUserType());
		return entity;
	}
	
	/**
	 * 菜单
	 * @param entity
	 * @return
	 */
	public static SysMenuForm buildSysMenuForm(SysMenuEntity entity) {
		SysMenuForm record = new SysMenuForm();
		record.setAddAccount(entity.getAddAccount());
		record.setCreateDate(entity.getCreateDate());
		record.setHref(entity.getHref());
		record.setIcon(entity.getIcon());
		record.setId(entity.getId());
		record.setIsBase(entity.getIsBase());
		record.setIsShow(entity.getIsShow());
		record.setIsUsable(entity.getIsUsable());
		record.setModifyAccount(entity.getModifyAccount());
		record.setModifyTime(entity.getModifyTime());
		record.setName(entity.getName());
		record.setParentId(entity.getParentId());
		record.setParentIds(entity.getParentIds());
		record.setPermission(entity.getPermission());
		record.setRemarks(entity.getRemarks());
		record.setSort(entity.getSort());
		record.setState(entity.getState());
		record.setTarget(entity.getTarget());
		return record;
	}

}
