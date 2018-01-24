package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsOperateRecordsEntity;

@MyBatisDao
public interface TenantsOperateRecordsMapper extends CrudDao<TenantsOperateRecordsEntity>{
}