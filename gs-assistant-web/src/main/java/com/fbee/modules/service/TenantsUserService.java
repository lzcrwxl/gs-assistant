package com.fbee.modules.service;

import java.util.List;
import java.util.Map;

import com.fbee.modules.form.TenantsUsersForm;
import com.fbee.modules.jsonData.basic.JsonResult;

public interface TenantsUserService {
	
	/**
	 * 根据账号获取登陆用户
	 * @param loginAccount
	 * @return
	 */
	JsonResult getByLoginAccount(String loginAccount);
	/**
	 * 根据注册手机号获取登陆用户
	 * @param telephone
	 * @return
	 */
	int getByTelephone(String telephone);
	/**
	 * 通过用户id获取用户子账号
	 * @param userId
	 * @return
	 */
	JsonResult getTenatsUser(Map<String, Object> map);
	
	/***
	 * 获取用户下拉框
	 * @param map
	 * @return
	 */
	JsonResult getTenatsUserSel(Map<String, Object> map);
	
	/**
	 * 通过id获取
	 */
	JsonResult getById(Integer id); 
	
	/**
	 * 批量删除用户
	 * @param userIds
	 * @return
	 */
	int deleteTenatsUser(List<String> userIds);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	int getAccountCount(Map<String, Object> map);
	
	/**
	 * 添加用户
	 * @param tenantsUsersEntity
	 * @return
	 */
	int addTenatsUser(TenantsUsersForm tenantsUsersEntity, String menuIds);
	
	/**
	 * 修改用户
	 * @param tenantsUsersEntity
	 * @return
	 */
	int updateTenatsUser(TenantsUsersForm tenantsUsersEntity, String menuIds);
	
	/**
	 * 重置密码
	 * @param map
	 * @return
	 */
	int resetPassword(Integer id, String password);

}
