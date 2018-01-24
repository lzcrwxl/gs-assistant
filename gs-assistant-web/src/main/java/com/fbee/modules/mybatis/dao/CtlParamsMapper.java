package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.CtlParamsEntity;

@MyBatisDao
public interface CtlParamsMapper extends CrudDao<CtlParamsEntity>{
}