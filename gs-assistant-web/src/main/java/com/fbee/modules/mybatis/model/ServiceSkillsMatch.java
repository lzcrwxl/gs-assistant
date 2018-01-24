package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class ServiceSkillsMatch implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
     * 表：service_skills_match
     * 字段：ID
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：service_skills_match
     * 字段：MATCH_TYPE
     * 注释：匹配类型：基本特征、服务工种、个人特点、服务内容
     *
     * @mbggenerated
     */
    private String matchType;

    /**
     * 表：service_skills_match
     * 字段：FIELD_PRO
     * 注释：字段属性
     *
     * @mbggenerated
     */
    private String fieldPro;

    /**
     * 表：service_skills_match
     * 字段：CODE
     * 注释：代码类型：证书
     *
     * @mbggenerated
     */
    private String code;

    /**
     * 表：service_skills_match
     * 字段：SCORE_TYPE
     * 注释：得分分类0不匹配 1全匹配 2 部分匹配
     *
     * @mbggenerated
     */
    private String scoreType;

    /**
     * 表：service_skills_match
     * 字段：REMARKS
     * 注释：备注解释
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 表：service_skills_match
     * 字段：SCORE
     * 注释：得分
     *
     * @mbggenerated
     */
    private String score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType == null ? null : matchType.trim();
    }

    public String getFieldPro() {
        return fieldPro;
    }

    public void setFieldPro(String fieldPro) {
        this.fieldPro = fieldPro == null ? null : fieldPro.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType == null ? null : scoreType.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }
}