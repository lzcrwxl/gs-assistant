package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsMenusEntity;

@MyBatisDao
public interface TenantsMenusMapper extends CrudDao<TenantsMenusEntity>{

	/**
	 * 获取菜单根据租户id
	 * @param params
	 * @return
	 */
	List<TenantsMenusEntity> getUserMenus(Map<String, Object> params);
}