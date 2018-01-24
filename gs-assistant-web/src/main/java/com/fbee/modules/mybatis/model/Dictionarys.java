package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class Dictionarys implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：dictionarys
     * 字段：id
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：dictionarys
     * 字段：parent_id
     * 注释：父级id
     *
     * @mbggenerated
     */
    private Integer parentId;

    /**
     * 表：dictionarys
     * 字段：type_code
     * 注释：字典类型
     *
     * @mbggenerated
     */
    private String typeCode;

    /**
     * 表：dictionarys
     * 字段：code
     * 注释：字典code
     *
     * @mbggenerated
     */
    private String code;

    /**
     * 表：dictionarys
     * 字段：value
     * 注释：字典text
     *
     * @mbggenerated
     */
    private String value;

    /**
     * 表：dictionarys
     * 字段：level
     * 注释：级别
     *
     * @mbggenerated
     */
    private String level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }
}