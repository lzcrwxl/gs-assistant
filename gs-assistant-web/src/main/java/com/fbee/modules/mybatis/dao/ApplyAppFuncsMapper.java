package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.ApplyAppFuncsEntity;

@MyBatisDao
public interface ApplyAppFuncsMapper extends CrudDao<ApplyAppFuncsEntity>  {
}