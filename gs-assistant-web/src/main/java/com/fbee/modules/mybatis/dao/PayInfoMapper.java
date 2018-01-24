package com.fbee.modules.mybatis.dao;


import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.model.PayInfo;

/**
 * Created by gaoyan on 05/07/2017.
 */
@MyBatisDao
public interface PayInfoMapper extends CrudDao<PayInfo> {

}

