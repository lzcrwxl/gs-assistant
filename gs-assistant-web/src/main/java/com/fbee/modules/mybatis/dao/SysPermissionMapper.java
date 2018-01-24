package com.fbee.modules.mybatis.dao;

import java.util.List;

import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.SysMenuEntity;
import com.fbee.modules.mybatis.entity.SysPermissionEntity;

@MyBatisDao
public interface SysPermissionMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysPermissionEntity record);

    int insertSelective(SysPermissionEntity record);


    int updateByPrimaryKeySelective(SysPermissionEntity record);

    int updateByPrimaryKey(SysPermissionEntity record);
    
    /**
     * 通过userId获取权限
     * @param userId
     * @return
     */
    List<SysMenuEntity> getByUserId(Integer userId);
    
    /**
     * 通过userId获取没有的权限
     * @param userId
     * @return
     */
    List<SysMenuEntity> getNoPermissionByUserId(Integer userId);
    
    /**
     * 删除用户权限
     * @param userId
     * @return
     */
    int deleteByUserId(List<String> userIds);
    
    /**
     * 批量添加
     * @param list
     * @return
     */
    int insertBatch(List<SysPermissionEntity> list);
}