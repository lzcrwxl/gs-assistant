package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsStaffBankEntity;
import com.fbee.modules.mybatis.model.TenantsStaffBankKey;

@MyBatisDao
public interface TenantsStaffBankMapper  extends CrudDao<TenantsStaffBankEntity>{

	/**
	 * 根据唯一键值获取阿姨银行卡信息
	 * @param staffBankKey
	 * @return
	 */
	TenantsStaffBankEntity getBankInfoByKey(TenantsStaffBankKey staffBankKey);
}