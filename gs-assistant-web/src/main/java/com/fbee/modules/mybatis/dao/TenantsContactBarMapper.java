package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsContactBarEntity;
import com.fbee.modules.mybatis.model.TenantsContactBar;

@MyBatisDao
public interface TenantsContactBarMapper extends CrudDao<TenantsContactBarEntity> {
	int deleteByPrimaryKey(Integer tenantId);

	int insert(TenantsContactBar record);

	int insertSelective(TenantsContactBar record);

	TenantsContactBar selectByPrimaryKey(Integer tenantId);

	int updateByPrimaryKeySelective(TenantsContactBar record);

	int updateByPrimaryKey(TenantsContactBar record);
	/**
	 * @param tenantId
	 * @return
	 * 
	 * 		TenantsContactBarEntity getContactBarByTenantId(Integer
	 *         tenantId);
	 */
	/**
	 * 
	 * @param tenantId
	 * @param tenantsContactBarEntity
	 * @return
	 * 
	 * 		int updateTenantsContactBar(TenantsContactBarEntity
	 *         tenantsContactBarEntity);
	 */

}