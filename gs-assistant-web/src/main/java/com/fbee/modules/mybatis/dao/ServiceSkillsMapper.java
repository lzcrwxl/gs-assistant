package com.fbee.modules.mybatis.dao;

import java.util.List;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.ServiceSkillsEntity;

@MyBatisDao
public interface ServiceSkillsMapper extends CrudDao<ServiceSkillsEntity>{

	/**
	 * 根据类型获取技能特点、个人特点、服务类型
	 * @param personFeatures
	 * @return
	 */
	List<ServiceSkillsEntity> getServiceSkills(String personFeatures);
}