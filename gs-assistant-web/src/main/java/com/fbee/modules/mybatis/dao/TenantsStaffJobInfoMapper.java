package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsStaffJobInfoEntity;

@MyBatisDao
public interface TenantsStaffJobInfoMapper extends CrudDao<TenantsStaffJobInfoEntity>{

	/**
	 * 根据租户id和员工（阿姨）id获取员工求职基本信息
	 * @param tenantsStaffJob
	 * @return
	 */
	TenantsStaffJobInfoEntity getStaffJobInfo(TenantsStaffJobInfoEntity tenantsStaffJob);
	TenantsStaffJobInfoEntity selectByPrimaryKey(Integer staffId);
}