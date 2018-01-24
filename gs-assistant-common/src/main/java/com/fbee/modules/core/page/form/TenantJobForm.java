package com.fbee.modules.core.page.form;

import com.fbee.modules.core.page.Page;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class TenantJobForm extends Page {

    /**
     * 表：tenants_jobs
     * 字段：id
     * 注释：主键ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：tenants_jobs
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /***
     * tenants_users 关联ID
     */
    private Integer tenantUserId;

    /**
     * 注释：服务工种
     */
    private String serviceType;

    /**
     * 注释：年龄
     */
    private String age;

    /**
     * 薪酬类型
     */
    private String salaryType;

    /**
     * 服务区域
     */
    private String serviceProvince;


    /**
     * 1上架  0下架
     */
    private String jobStatus;

    /**
     * 订单编号
     */
    private String orderNo;


    /**
     * resume 1待确认，2待面试  3已拒绝  4已完成  5已取消
     */
    private String resumeStatus;

    /**
     * 排除自己，1是0否
     */
    private Integer withOutSelf;

    /**
     * 只查自己发布的招聘(1是0否)
     */
    private Integer onlySelf;

    /**
     * 查询可抢单 1是0否
     */
    private Integer isApplyable;

    private String visualExtend;

    /**
     * 是否管理员
     */
    private String isAdmin;
    /**
     * 如果是管理员， 可筛选用户
     */
    private String queryUserId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyTime;

    /**
     * 价格区间
     */
    private String salaryRange;
    @JsonIgnore
    private BigDecimal salaryMin;
    @JsonIgnore
    private BigDecimal salaryMax;
    /**
     * 微信端我的发布跳转页面
     */
    private String toUrl;

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public BigDecimal getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(BigDecimal salaryMin) {
        this.salaryMin = salaryMin;
    }

    public BigDecimal getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(BigDecimal salaryMax) {
        this.salaryMax = salaryMax;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getQueryUserId() {
        return queryUserId;
    }

    public void setQueryUserId(String queryUserId) {
        this.queryUserId = queryUserId;
    }

    public String getVisualExtend() {
        return visualExtend;
    }

    public void setVisualExtend(String visualExtend) {
        this.visualExtend = visualExtend;
    }

    public Integer getIsApplyable() {
        return isApplyable;
    }

    public void setIsApplyable(Integer isApplyable) {
        this.isApplyable = isApplyable;
    }

    public Integer getOnlySelf() {
        return onlySelf;
    }

    public void setOnlySelf(Integer onlySelf) {
        this.onlySelf = onlySelf;
    }

    public Integer getWithOutSelf() {
        return withOutSelf;
    }

    public void setWithOutSelf(Integer withOutSelf) {
        this.withOutSelf = withOutSelf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getTenantUserId() {
        return tenantUserId;
    }

    public void setTenantUserId(Integer tenantUserId) {
        this.tenantUserId = tenantUserId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public String getServiceProvince() {
        return serviceProvince;
    }

    public void setServiceProvince(String serviceProvince) {
        this.serviceProvince = serviceProvince;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getResumeStatus() {
        return resumeStatus;
    }

    public void setResumeStatus(String resumeStatus) {
        this.resumeStatus = resumeStatus;
    }

	public String getToUrl() {
		return toUrl;
	}

	public void setToUrl(String toUrl) {
		this.toUrl = toUrl;
	}
}
