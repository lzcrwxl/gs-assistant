package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsRecommendEntity;

@MyBatisDao
public interface TenantsRecommendMapper extends CrudDao<TenantsRecommendEntity>{

	/**
	 * 查询商家推荐的家政员数量
	 * @return
	 */
	Integer getCountByTenantId(Integer tenantId);
	
	/**
	 * 更新阿姨推荐
	 * @return
	 */
	int update(TenantsRecommendEntity tenantsRecommendEntity);


	
}