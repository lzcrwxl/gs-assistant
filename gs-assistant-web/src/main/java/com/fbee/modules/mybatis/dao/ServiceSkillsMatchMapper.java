package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.ServiceSkillsMatchEntity;

@MyBatisDao
public interface ServiceSkillsMatchMapper  extends CrudDao<ServiceSkillsMatchEntity>{
}