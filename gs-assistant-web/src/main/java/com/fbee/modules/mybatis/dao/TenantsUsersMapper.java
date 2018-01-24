package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.w3c.dom.ls.LSInput;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;
import com.fbee.modules.mybatis.model.TenantsUsers;

@MyBatisDao
public interface TenantsUsersMapper extends CrudDao<TenantsUsersEntity>{

	/**
	 * 根据账号获取登陆用户
	 * @param loginAccount
	 * @return
	 */
	TenantsUsersEntity getByLoginAccount(String loginAccount);
	/**
	 * 根据注册手机号获取登陆用户
	 * @param loginAccount
	 * @return
	 */
	int getByTelephone(String telephone);
	/**
	 * 根据用户id重置密码
	 * @param tenantsUsersEntity
	 * @return
	 */
	int update(TenantsUsersEntity tenantsUsersEntity);
	/**
	 * 根据用户id获取绑定手机号
	 * @param id
	 * @return
	 */
	String getTelephoneByUserId(int id);
	
	/**
	 * 通过用户id获取用户子账号和用户类型
	 * @param userId
	 * @return
	 */
	List<TenantsUsersEntity> getTenatsUser(Map<String, Object> map);
	
	/***
	 * 获取用户下拉框
	 * @param map
	 * @return
	 */
	List<TenantsUsers> getTenatsUserSel(Map<String, Object> map);
	
	
	/**
	 * 通过id获取
	 */
	TenantsUsersEntity getById(Integer id); 
	
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
	int addTenatsUser(TenantsUsersEntity tenantsUsersEntity);

	/**
	 *
	 * @param addAccount
	 * @return
	 */
	int getIdByLoginAccount(String addAccount);
	
	/**
	 * 重置密码
	 * @param map
	 * @return
	 */
	int resetPassword(Map<String, Object> map);
	
	
	Map<String , Object> getTenants(Map<String, Object> map);

	List<TenantsUsersEntity> getByOpenId(String openId);

	List<TenantsUsersEntity> getByTenantId(Integer tenantId);
}