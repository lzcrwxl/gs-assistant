package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsBannersEntity;

import java.util.List;

@MyBatisDao
public interface TenantsBannersMapper extends CrudDao<TenantsBannersEntity>{

	/**
	 * @param tenantId
	 * @return
	 */
	TenantsBannersEntity getBannerByTenantId(Integer tenantId);

	List<TenantsBannersEntity> findBannersByTenantId(Integer tenantId);
	
	/**
	 * 
	 * @MethodName:updateBannerInfoById
	 * @Type:TenantsBannersMapper
	 * @Description:更新banner信息
	 * @Return:int
	 * @Param:@param tenantsBannersBean
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 15, 2017 11:24:33 AM
	 */
	int updateBannerInfoById(TenantsBannersEntity tenantsBannersBean);

}