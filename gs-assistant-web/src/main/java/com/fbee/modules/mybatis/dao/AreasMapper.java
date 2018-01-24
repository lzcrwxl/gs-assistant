package com.fbee.modules.mybatis.dao;

import java.util.List;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.AreasEntity;

@MyBatisDao
public interface AreasMapper extends CrudDao<AreasEntity>{

	/**
	 * 获取区域服务
	 * @param proviceLevel
	 * @return
	 */
	List<AreasEntity> getAreaListByLevel(String proviceLevel);
}