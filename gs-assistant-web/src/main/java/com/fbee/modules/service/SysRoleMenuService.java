package com.fbee.modules.service;

import com.fbee.modules.form.SysRoleMenuForm;
import com.fbee.modules.jsonData.basic.JsonResult;

/**
 * 角色菜单service
 * @author Administrator
 *
 */
public interface SysRoleMenuService {
	
	int deleteByPrimaryKey(Integer id);

    int insert(SysRoleMenuForm record);

    int insertSelective(SysRoleMenuForm record);

    JsonResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleMenuForm record);

    int updateByPrimaryKey(SysRoleMenuForm record);

}
