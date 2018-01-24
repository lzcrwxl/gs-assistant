package com.fbee.modules.mybatis.dao;

import java.util.List;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsStaffCertsInfoEntity;

@MyBatisDao
public interface TenantsStaffCertsInfoMapper extends CrudDao<TenantsStaffCertsInfoEntity>{

	/**
	 * 获取阿姨的所有证书信息
	 * @param staffId
	 * @return
	 */
	List<TenantsStaffCertsInfoEntity> getSatffAllCerts(Integer staffId);
	
	/**
	 * 修改证书信息
	 * @param entity
	 * @return
	 */
	int updateCert(TenantsStaffCertsInfoEntity entity);
	
	/**
	 * 修改证书状态
	 * @param entity
	 * @return
	 */
	int updateIsUsable(TenantsStaffCertsInfoEntity entity);
}