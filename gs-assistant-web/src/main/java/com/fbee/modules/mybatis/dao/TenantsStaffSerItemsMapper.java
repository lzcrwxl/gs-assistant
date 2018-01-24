package com.fbee.modules.mybatis.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsStaffSerItemsEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffsInfoEntity;
import com.fbee.modules.mybatis.model.TenantsStaffSerItemsKey;

@MyBatisDao
public interface TenantsStaffSerItemsMapper extends CrudDao<TenantsStaffSerItemsEntity>{

	/**
	 * 根据唯一键值获取员工（阿姨）服务工种对象
	 * @param staffSerItemsKey
	 * @return
	 */
	TenantsStaffSerItemsEntity getStaffServiceItemsByKey(TenantsStaffSerItemsKey staffSerItemsKey);
	
	/**
	 * 根据id查询工种
	 * @param id
	 * @author xiehui
	 * @return
	 */
	TenantsStaffSerItemsEntity getStaffServiceItemsById(Integer id);

	/**
	 * 获取阿姨服务工种
	 * @param staffSerItems
	 * @return
	 */
	List<TenantsStaffSerItemsEntity> getStaffServiceItems(TenantsStaffSerItemsEntity staffSerItems);

	/**
	 * @param staffId
	 */
	List<TenantsStaffSerItemsEntity> getServiceItemsByStaffId(Integer staffId);

	/**
	 * 根据唯一键值获取员工（阿姨）服务工种对象
	 * @return
	 */
	TenantsStaffSerItemsEntity getStaffServiceItemsByIds(Integer stafffSerItemId);
	
	

}