package com.fbee.modules.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fbee.modules.form.SysPermissionForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.SysPermissionMapper;
import com.fbee.modules.mybatis.entity.SysPermissionEntity;
import com.fbee.modules.operation.PermissionOpt;
import com.fbee.modules.service.SysPermissionService;


@Service
public class SysPermissionServiceImpl implements SysPermissionService {
	
	@Autowired
	SysPermissionMapper permissionDao;

	@Transactional
	public int deleteByPrimaryKey(Integer id) {
		return permissionDao.deleteByPrimaryKey(id);
	}

	@Transactional
	public int insert(SysPermissionForm record) {
		SysPermissionEntity entity = PermissionOpt.buildSysPermissionEntity(record);
		return permissionDao.insert(entity);
	}

	@Transactional
	public int insertSelective(SysPermissionForm record) {
		SysPermissionEntity entity = PermissionOpt.buildSysPermissionEntity(record);
		return permissionDao.insertSelective(entity);
	}

	@Transactional
	public int updateByPrimaryKeySelective(SysPermissionForm record) {
		SysPermissionEntity entity = PermissionOpt.buildSysPermissionEntity(record);
		return permissionDao.updateByPrimaryKeySelective(entity);
	}

	@Transactional
	public int updateByPrimaryKey(SysPermissionForm record) {
		SysPermissionEntity entity = PermissionOpt.buildSysPermissionEntity(record);
		return permissionDao.updateByPrimaryKey(entity);
	}

	@Transactional
	public JsonResult getByUserId(Integer userId) {
		
		return JsonResult.success(permissionDao.getByUserId(userId));
	}

}
