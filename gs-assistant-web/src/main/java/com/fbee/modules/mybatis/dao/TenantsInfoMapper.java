package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsInfoEntity;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface TenantsInfoMapper extends CrudDao<TenantsInfoEntity>{

    Integer updateJobCount(TenantsInfoEntity te);

    Integer updateResumeCount(TenantsInfoEntity te);

    void resetJobResumeCount(@Param("jobCount") Integer jobCount, @Param("resumeCount")Integer resumeCount);

}