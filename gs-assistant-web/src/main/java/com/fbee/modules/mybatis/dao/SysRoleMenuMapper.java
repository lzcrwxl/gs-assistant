package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.model.SysRoleMenu;

/**
 * 角色菜单mapper
 * @author Administrator
 *
 */
@MyBatisDao
public interface SysRoleMenuMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);
}