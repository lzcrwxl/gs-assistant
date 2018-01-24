package com.fbee.modules.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fbee.modules.form.SysRoleMenuForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.SysRoleMenuMapper;
import com.fbee.modules.mybatis.entity.SysRoleMenuEntity;
import com.fbee.modules.operation.PermissionOpt;
import com.fbee.modules.service.SysRoleMenuService;
import com.fbee.modules.service.basic.BaseService;

/**
 * 角色菜单service
 * @author Administrator
 *
 */
@Service
public class SysRoleMenuServiceImpl extends BaseService implements SysRoleMenuService {
	
	@Autowired
	SysRoleMenuMapper roleMenuDao;

	@Transactional
	public int deleteByPrimaryKey(Integer id) {
		return roleMenuDao.deleteByPrimaryKey(id);
	}

	@Transactional
	public int insert(SysRoleMenuForm record) {
		SysRoleMenuEntity entity = PermissionOpt.buildSysRoleMenuEntity(record);
		return roleMenuDao.insert(entity);
	}

	@Transactional
	public int insertSelective(SysRoleMenuForm record) {
		SysRoleMenuEntity entity = PermissionOpt.buildSysRoleMenuEntity(record);
		return roleMenuDao.insertSelective(entity);
	}

	@Transactional
	public JsonResult selectByPrimaryKey(Integer id) {
		return JsonResult.success(roleMenuDao.selectByPrimaryKey(id));
	}

	@Transactional
	public int updateByPrimaryKeySelective(SysRoleMenuForm record) {
		SysRoleMenuEntity entity = PermissionOpt.buildSysRoleMenuEntity(record);
		return roleMenuDao.updateByPrimaryKeySelective(entity);
	}

	@Transactional
	public int updateByPrimaryKey(SysRoleMenuForm record) {
		SysRoleMenuEntity entity = PermissionOpt.buildSysRoleMenuEntity(record);
		return roleMenuDao.updateByPrimaryKey(entity);
	}

}
