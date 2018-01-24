package com.fbee.modules.mybatis.dao;

import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsFundsEntity;
import com.fbee.modules.mybatis.model.TenantsFunds;

@MyBatisDao
public interface TenantsFundsMapper extends CrudDao<TenantsFundsEntity>{
	/**
	 * 根据租户id查询账户信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> getByTenantsId(Integer id);
		
	TenantsFundsEntity selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TenantsFunds tenantsFunds);
}