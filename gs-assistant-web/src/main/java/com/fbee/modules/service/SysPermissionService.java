package com.fbee.modules.service;

import com.fbee.modules.form.SysPermissionForm;
import com.fbee.modules.jsonData.basic.JsonResult;

public interface SysPermissionService {
	
	int deleteByPrimaryKey(Integer id);

    int insert(SysPermissionForm record);

    int insertSelective(SysPermissionForm record);

    int updateByPrimaryKeySelective(SysPermissionForm record);

    int updateByPrimaryKey(SysPermissionForm record);
    
    /**
     * 通过userId获取权限
     * @param userId
     * @return
     */
    JsonResult getByUserId(Integer userId);

}
