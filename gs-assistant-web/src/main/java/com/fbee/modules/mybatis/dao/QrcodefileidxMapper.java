package com.fbee.modules.mybatis.dao;

import java.util.Map;

import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.model.Qrcodefileidx;
@MyBatisDao
public interface QrcodefileidxMapper {
    int deleteByPrimaryKey(Integer qrid);

    int insert(Qrcodefileidx record);

    int insertSelective(Qrcodefileidx record);

    Qrcodefileidx selectByPrimaryKey(Integer qrid);

    int updateByPrimaryKeySelective(Qrcodefileidx record);

    int updateByPrimaryKey(Qrcodefileidx record);
    
    String getDictionaryData(Map map);
}