package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.CtlMenusEntity;

@MyBatisDao
public interface CtlMenusMapper extends CrudDao<CtlMenusEntity>{
}