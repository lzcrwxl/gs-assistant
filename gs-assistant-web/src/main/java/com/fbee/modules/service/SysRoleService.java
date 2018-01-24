package com.fbee.modules.service;

import com.fbee.modules.form.SysRoleForm;
import com.fbee.modules.jsonData.basic.JsonResult;

/**
 * 角色Service
 * @author Administrator
 *
 */
public interface SysRoleService {
	
	int deleteByPrimaryKey(Integer id);

    int insert(SysRoleForm record);

    int insertSelective(SysRoleForm record);

    JsonResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleForm record);

    int updateByPrimaryKey(SysRoleForm record);

}
