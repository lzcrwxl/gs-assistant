package com.fbee.modules.mybatis.dao;



import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.SmsRecordsEntity;

@MyBatisDao
public interface SmsRecordsMapper extends CrudDao<SmsRecordsEntity>{
	/**
	 * 插入短信验证记录
	 * @param entity
	 * @return
	 */
	public int insert(SmsRecordsEntity entity);
}