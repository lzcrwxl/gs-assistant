package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class AppsModules implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
     * 表：apps_modules
     * 字段：id
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：apps_modules
     * 字段：type
     * 注释：模块类别 0:系统后台模块 1：系统前端模块 2 ：其他模块
     *
     * @mbggenerated
     */
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}