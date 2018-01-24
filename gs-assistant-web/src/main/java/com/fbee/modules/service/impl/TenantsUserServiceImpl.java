package com.fbee.modules.service.impl;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fbee.modules.bean.UserBean;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.core.utils.CookieUtils;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.SysMenuForm;
import com.fbee.modules.form.TenantsUsersForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.dao.SysMenuMapper;
import com.fbee.modules.mybatis.dao.SysPermissionMapper;
import com.fbee.modules.mybatis.dao.TenantsUsersMapper;
import com.fbee.modules.mybatis.entity.SysMenuEntity;
import com.fbee.modules.mybatis.entity.SysPermissionEntity;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;
import com.fbee.modules.mybatis.model.TenantsUsers;
import com.fbee.modules.operation.PermissionOpt;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.redis.consts.RedisKey;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.TenantsUserService;
import com.fbee.modules.utils.EntryptUtils;
import com.fbee.modules.utils.JsonUtils;

/**
 * 门店后台账户service
 * @author Administrator
 *
 */
@Service
public class TenantsUserServiceImpl implements TenantsUserService {
	
	@Autowired
	TenantsUsersMapper tenantsUsersDao;
	
	@Autowired
	SysPermissionMapper sysPermissionDao;
	
	@Autowired
	SysMenuMapper sysMenuDao;
	
	@Autowired
	CommonService commonService;
	
	private JedisTemplate redis = JedisUtils.getJedisTemplate();

	/**
	 * 通过用户id获取用户子账号
	 * @param userId
	 * @return
	 */
	@Transactional
	public JsonResult getTenatsUser(Map<String, Object> map) {
		List<TenantsUsersEntity> tenantsUserList = tenantsUsersDao.getTenatsUser(map);
		return JsonResult.success(tenantsUserList);
	}

	/**
	 * 批量删除用户
	 */
	@Transactional
	public int deleteTenatsUser(List<String> userIds) {
		sysPermissionDao.deleteByUserId(userIds);
		for(String id : userIds){
			String token =  redis.get(RedisKey.User.UA_TOKEN.getKey(id));
			redis.del(RedisKey.User.UA.getKey(token));
			redis.del(RedisKey.User.UA_TOKEN.getKey(id));
		}
		return tenantsUsersDao.deleteTenatsUser(userIds);
	}

	/**
	 * 添加用户
	 */
	@Transactional
	public int addTenatsUser(TenantsUsersForm tenantsUsersForm, String menuIds) {
		TenantsUsersEntity tenantsUsersEntity = PermissionOpt.buildTenantsUsersEntity(tenantsUsersForm);
		tenantsUsersEntity.setAddTime(new Date());
		int result = tenantsUsersDao.addTenatsUser(tenantsUsersEntity);
		List<SysPermissionEntity> sysPermissionList = new ArrayList<>();
		//List<String> allMenuList = sysMenuDao.getAllMenu();
		if (menuIds != null) {
			String[] menuIdArray = menuIds.split(",");
			/*for (String menuId : menuIdArray) {
				if (StringUtils.isNotEmpty(menuId)) {
					allMenuList.remove(menuId);
				}
			}
			for (String menuId : allMenuList) {
				if (StringUtils.isNotEmpty(menuId)) {
					SysPermissionEntity permission = new SysPermissionEntity();
					permission.setMenuId(Integer.valueOf(menuId));
					permission.setUserId(tenantsUsersEntity.getId());
					sysPermissionList.add(permission);
				}
			}*/
			for (String menuId : menuIdArray) {
				if (StringUtils.isNotEmpty(menuId)) {
					SysPermissionEntity permission = new SysPermissionEntity();
					permission.setMenuId(Integer.valueOf(menuId));
					permission.setUserId(tenantsUsersEntity.getId());
					sysPermissionList.add(permission);
				}
			}
			if (sysPermissionList != null && sysPermissionList.size() > 0) {
				sysPermissionDao.insertBatch(sysPermissionList);
			}
		}
		return result;
	}

	/**
	 * 获取子账号数
	 */
	@Transactional
	public int getAccountCount(Map<String, Object> map) {
		return tenantsUsersDao.getAccountCount(map);
	}

	/**
	 * 修改用户
	 */
	@Transactional
	public int updateTenatsUser(TenantsUsersForm tenantsUsersForm, String menuIds) {
		TenantsUsersEntity tenantsUsersEntity = PermissionOpt.buildTenantsUsersEntity(tenantsUsersForm);
		tenantsUsersEntity.setRefresh(Constants.REFRESH_TRUE);
		int result = tenantsUsersDao.update(tenantsUsersEntity);
		
		//删除原有的权限
		List<String> userIdList = new ArrayList<>();
		userIdList.add(String.valueOf(tenantsUsersEntity.getId()));
		sysPermissionDao.deleteByUserId(userIdList);
		//添加权限
		List<SysPermissionEntity> sysPermissionList = new ArrayList<>();
		List<String> allMenuList = sysMenuDao.getAllMenu();
		if (menuIds != null) {
			String[] menuIdArray = menuIds.split(",");
			/*for (String menuId : menuIdArray) {
				if (StringUtils.isNotEmpty(menuId)) {
					allMenuList.remove(menuId);
				}
			}
			for (String menuId : allMenuList) {
				if (StringUtils.isNotEmpty(menuId)) {
					SysPermissionEntity permission = new SysPermissionEntity();
					permission.setMenuId(Integer.valueOf(menuId));
					permission.setUserId(tenantsUsersEntity.getId());
					sysPermissionList.add(permission);
				}
			}*/
			for (String menuId : menuIdArray) {
				if (StringUtils.isNotEmpty(menuId)) {
					SysPermissionEntity permission = new SysPermissionEntity();
					permission.setMenuId(Integer.valueOf(menuId));
					permission.setUserId(tenantsUsersEntity.getId());
					sysPermissionList.add(permission);
				}
			}
			if (sysPermissionList != null && sysPermissionList.size() > 0) {
				sysPermissionDao.insertBatch(sysPermissionList);
			}
		}
		//更新缓存
		commonService.refreshSession();
		return result;
	}

	@Transactional
	public JsonResult getById(Integer id) {
		return JsonResult.success(tenantsUsersDao.getById(id));
	}

	@Transactional
	public JsonResult getByLoginAccount(String loginAccount) {
		return JsonResult.success(tenantsUsersDao.getByLoginAccount(loginAccount));
	}

	@Override
	public int getByTelephone(String telephone) {
		
		return tenantsUsersDao.getByTelephone(telephone);
	}
	
	@Transactional
	public int resetPassword(Integer id, String password) {
		TenantsUsersEntity user=tenantsUsersDao.getById(id);
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("salt", user.getSalt());
		map.put("password", EntryptUtils.entryptUserPassword(password.toUpperCase(), user.getSalt()));
		return tenantsUsersDao.resetPassword(map);
	}

	@Override
	public JsonResult getTenatsUserSel(Map<String, Object> map) {
		List<TenantsUsers> TenantsUsers = tenantsUsersDao.getTenatsUserSel(map);
		return JsonResult.success(TenantsUsers);
	}

	

}
