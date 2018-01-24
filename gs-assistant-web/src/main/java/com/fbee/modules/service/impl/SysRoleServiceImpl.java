package com.fbee.modules.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fbee.modules.form.SysRoleForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.SysRoleMapper;
import com.fbee.modules.mybatis.entity.SysRoleEntity;
import com.fbee.modules.operation.PermissionOpt;
import com.fbee.modules.service.SysRoleService;
import com.fbee.modules.service.basic.BaseService;


/**
 * 角色service
 * @author Administrator
 *
 */
@Service
public class SysRoleServiceImpl extends BaseService implements SysRoleService {

	@Autowired
	SysRoleMapper roleDao;
	
	@Transactional
	public int deleteByPrimaryKey(Integer id) {
		return roleDao.deleteByPrimaryKey(id);
	}

	@Transactional
	public int insert(SysRoleForm record) {
		SysRoleEntity entity = PermissionOpt.buildSysRoleEntity(record);
		return roleDao.insert(entity);
	}

	@Transactional
	public int insertSelective(SysRoleForm record) {
		SysRoleEntity entity = PermissionOpt.buildSysRoleEntity(record);
		return roleDao.insertSelective(entity);
	}

	@Transactional
	public JsonResult selectByPrimaryKey(Integer id) {
		return JsonResult.success(roleDao.selectByPrimaryKey(id));
	}

	@Transactional
	public int updateByPrimaryKeySelective(SysRoleForm record) {
		SysRoleEntity entity = PermissionOpt.buildSysRoleEntity(record);
		return roleDao.updateByPrimaryKeySelective(entity);
	}

	@Transactional
	public int updateByPrimaryKey(SysRoleForm record) {
		SysRoleEntity entity = PermissionOpt.buildSysRoleEntity(record);
		return roleDao.updateByPrimaryKey(entity);
	}

}
