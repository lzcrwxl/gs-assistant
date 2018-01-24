package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class ServiceSkills implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：service_skills
     * 字段：ID
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：service_skills
     * 字段：ATTRBUTE
     * 注释：类别 PERSON_FEATURES:个人特点 SERVICE_TYPES:技能特点 SERVICE_NATURE:服务类型
     *
     * @mbggenerated
     */
    private String attrbute;

    /**
     * 表：service_skills
     * 字段：ITEM_CODE
     * 注释：分类值（服务工种：月嫂、个人特点：语言、服务类型）
     *
     * @mbggenerated
     */
    private String itemCode;

    /**
     * 表：service_skills
     * 字段：SKILLS_KEY
     * 注释：code值
     *
     * @mbggenerated
     */
    private String skillsKey;

    /**
     * 表：service_skills
     * 字段：SKILLS_VALUE
     * 注释：中文显示值
     *
     * @mbggenerated
     */
    private String skillsValue;

    /**
     * 表：service_skills
     * 字段：PAR_SKILLS_KEY
     * 注释：父级显示值：例如照看小孩
     *
     * @mbggenerated
     */
    private String parSkillsKey;

    /**
     * 表：service_skills
     * 字段：SORT
     * 注释：排序值
     *
     * @mbggenerated
     */
    private String sort;

    /**
     * 表：service_skills
     * 字段：level
     * 注释：0顶级 1一级 2 二级
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

    public String getAttrbute() {
        return attrbute;
    }

    public void setAttrbute(String attrbute) {
        this.attrbute = attrbute == null ? null : attrbute.trim();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public String getSkillsKey() {
        return skillsKey;
    }

    public void setSkillsKey(String skillsKey) {
        this.skillsKey = skillsKey == null ? null : skillsKey.trim();
    }

    public String getSkillsValue() {
        return skillsValue;
    }

    public void setSkillsValue(String skillsValue) {
        this.skillsValue = skillsValue == null ? null : skillsValue.trim();
    }

    public String getParSkillsKey() {
        return parSkillsKey;
    }

    public void setParSkillsKey(String parSkillsKey) {
        this.parSkillsKey = parSkillsKey == null ? null : parSkillsKey.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }
}