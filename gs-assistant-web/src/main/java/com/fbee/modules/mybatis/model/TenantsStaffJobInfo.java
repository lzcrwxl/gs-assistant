package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TenantsStaffJobInfo implements Serializable{
   
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_staff_job_info
     * 字段：STAFF_ID
     * 注释：员工id
     *
     * @mbggenerated
     */
    private Integer staffId;

    /**
     * 表：tenants_staff_job_info
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：tenants_staff_job_info
     * 字段：MANAGE_WAY
     * 注释：管理方式
     *
     * @mbggenerated
     */
    private String manageWay;

    /**
     * 表：tenants_staff_job_info
     * 字段：SERVICE_PROVICE
     * 注释：服务区域省
     *
     * @mbggenerated
     */
    private String serviceProvice;

    /**
     * 表：tenants_staff_job_info
     * 字段：SERVICE_CITY
     * 注释：服务区域市
     *
     * @mbggenerated
     */
    private String serviceCity;

    /**
     * 表：tenants_staff_job_info
     * 字段：SERVICE_COUNTY
     * 注释：服务区域区
     *
     * @mbggenerated
     */
    private String serviceCounty;

    /**
     * 表：tenants_staff_job_info
     * 字段：WORK_EXPERIENCE
     * 注释：工作经历
     *
     * @mbggenerated
     */
    private String workExperience;

    /**
     * 表：tenants_staff_job_info
     * 字段：SELF_EVALUATION
     * 注释：自我评价
     *
     * @mbggenerated
     */
    private String selfEvaluation;

    /**
     * 表：tenants_staff_job_info
     * 字段：TEACHER_EVALUATION
     * 注释：老师评价
     *
     * @mbggenerated
     */
    private String teacherEvaluation;

    /**
     * 表：tenants_staff_job_info
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：tenants_staff_job_info
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：tenants_staff_job_info
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：tenants_staff_job_info
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

    /**
     * 表：tenants_staff_job_info
     * 字段：language_feature
     * 注释：语言特点
     *
     * @mbggenerated
     */
    private String languageFeature;

    /**
     * 表：tenants_staff_job_info
     * 字段：characer_feature
     * 注释：性格特点
     *
     * @mbggenerated
     */
    private String characerFeature;

    /**
     * 表：tenants_staff_job_info
     * 字段：cooking_feature
     * 注释：烹饪特点
     *
     * @mbggenerated
     */
    private String cookingFeature;

    /**
     * 表：tenants_staff_job_info
     * 字段：other
     * 注释：其他（语言）
     *
     * @mbggenerated
     */
    private String other;

    /**
     * 表：tenants_staff_job_info
     * 字段：pet_feeding
     * 注释：是否喂养宠物
     *
     * @mbggenerated
     */
    private String petFeeding;

    /**
     * 表：tenants_staff_job_info
     * 字段：elderly_support
     * 注释：是否赡养老人
     *
     * @mbggenerated
     */
    private String elderlySupport;

    private BigDecimal price;
    private String unit;
    private String serviceArea;//服务地域
    private String experience;//从业经验 dictionary

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getManageWay() {
        return manageWay;
    }

    public void setManageWay(String manageWay) {
        this.manageWay = manageWay == null ? null : manageWay.trim();
    }

    public String getServiceProvice() {
        return serviceProvice;
    }

    public void setServiceProvice(String serviceProvice) {
        this.serviceProvice = serviceProvice == null ? null : serviceProvice.trim();
    }

    public String getServiceCity() {
        return serviceCity;
    }

    public void setServiceCity(String serviceCity) {
        this.serviceCity = serviceCity == null ? null : serviceCity.trim();
    }

    public String getServiceCounty() {
        return serviceCounty;
    }

    public void setServiceCounty(String serviceCounty) {
        this.serviceCounty = serviceCounty == null ? null : serviceCounty.trim();
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience == null ? null : workExperience.trim();
    }

    public String getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation == null ? null : selfEvaluation.trim();
    }

    public String getTeacherEvaluation() {
        return teacherEvaluation;
    }

    public void setTeacherEvaluation(String teacherEvaluation) {
        this.teacherEvaluation = teacherEvaluation == null ? null : teacherEvaluation.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddAccount() {
        return addAccount;
    }

    public void setAddAccount(String addAccount) {
        this.addAccount = addAccount == null ? null : addAccount.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyAccount() {
        return modifyAccount;
    }

    public void setModifyAccount(String modifyAccount) {
        this.modifyAccount = modifyAccount == null ? null : modifyAccount.trim();
    }

    public String getLanguageFeature() {
        return languageFeature;
    }

    public void setLanguageFeature(String languageFeature) {
        this.languageFeature = languageFeature == null ? null : languageFeature.trim();
    }

    public String getCharacerFeature() {
        return characerFeature;
    }

    public void setCharacerFeature(String characerFeature) {
        this.characerFeature = characerFeature == null ? null : characerFeature.trim();
    }

    public String getCookingFeature() {
        return cookingFeature;
    }

    public void setCookingFeature(String cookingFeature) {
        this.cookingFeature = cookingFeature == null ? null : cookingFeature.trim();
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }

    public String getPetFeeding() {
        return petFeeding;
    }

    public void setPetFeeding(String petFeeding) {
        this.petFeeding = petFeeding == null ? null : petFeeding.trim();
    }

    public String getElderlySupport() {
        return elderlySupport;
    }

    public void setElderlySupport(String elderlySupport) {
        this.elderlySupport = elderlySupport == null ? null : elderlySupport.trim();
    }
}