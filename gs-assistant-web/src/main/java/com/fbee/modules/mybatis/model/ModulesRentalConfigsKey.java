package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class ModulesRentalConfigsKey implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：modules_rental_configs
     * 字段：MODULE_TYPE
     * 注释：00:系统应用 01：前端收费模块 02：后端收费模块 03：其他定制
     *
     * @mbggenerated
     */
    private String moduleType;

    /**
     * 表：modules_rental_configs
     * 字段：MODULE_ID
     * 注释：模块id
     *
     * @mbggenerated
     */
    private Integer moduleId;

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType == null ? null : moduleType.trim();
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }
}