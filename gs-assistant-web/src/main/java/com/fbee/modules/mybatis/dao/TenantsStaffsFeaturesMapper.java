package com.fbee.modules.mybatis.dao;

import java.util.List;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsStaffsFeaturesEntity;
import com.fbee.modules.mybatis.model.TenantsStaffsFeaturesKey;

@MyBatisDao
public interface TenantsStaffsFeaturesMapper extends CrudDao<TenantsStaffsFeaturesEntity>{

	/**
	 * 根据唯一键值获取员工（阿姨）个人特点对象
	 * @param staffsFeaturesKey
	 * @return
	 */
	TenantsStaffsFeaturesEntity getStaffFeatureByKey(TenantsStaffsFeaturesKey staffsFeaturesKey);

	/**
	 * @param staffId
	 * @return
	 */
	List<TenantsStaffsFeaturesEntity> getStaffFeatureByStaffId(Integer staffId);
}