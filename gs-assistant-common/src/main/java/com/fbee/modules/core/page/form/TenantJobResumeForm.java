package com.fbee.modules.core.page.form;

import com.fbee.modules.core.page.Page;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 简历查询 筛选条件
 */
public class TenantJobResumeForm extends Page {

    private Integer id;
    private String  orderNo;
    private Integer jobId;
    private Integer jobTenantId;
    private Integer jobTenantUserId;
    private Integer resumeTenantId;
    private Integer resumeTenantUserId;
    private Integer resumeTenantStaffId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date    applyDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date    checkDate;

    /**
     * 1待确认，2待面试  3已拒绝  4已完成  5已取消
     */
    private String  status;

    /**
     * 上下架
     */
    private String  jobStatus;
    private String  remarks;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyEndDate;
    /**
     * 注释：服务工种
     */
    private String serviceType;

    /**
     * 注释：年龄
     */
    private String age;

    private String ageStart;
    private String ageEnd;

    /**
     * 薪酬类型
     */
    private String salaryType;
    private String salaryRange;
    @JsonIgnore
    private BigDecimal salaryMin;
    @JsonIgnore
    private BigDecimal salaryMax;

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

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * 服务区域
     */
    private String serviceProvince;

    private String education;
    private String staffName;
    private String zodiac;
    private String nativePlace;
    private String experience;

    public String getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(String ageStart) {
        this.ageStart = ageStart;
    }

    public String getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(String ageEnd) {
        this.ageEnd = ageEnd;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Date getApplyStartDate() {
        return applyStartDate;
    }

    public void setApplyStartDate(Date applyStartDate) {
        this.applyStartDate = applyStartDate;
    }

    public Date getApplyEndDate() {
        return applyEndDate;
    }

    public void setApplyEndDate(Date applyEndDate) {
        this.applyEndDate = applyEndDate;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getJobTenantId() {
        return jobTenantId;
    }

    public void setJobTenantId(Integer jobTenantId) {
        this.jobTenantId = jobTenantId;
    }

    public Integer getJobTenantUserId() {
        return jobTenantUserId;
    }

    public void setJobTenantUserId(Integer jobTenantUserId) {
        this.jobTenantUserId = jobTenantUserId;
    }

    public Integer getResumeTenantId() {
        return resumeTenantId;
    }

    public void setResumeTenantId(Integer resumeTenantId) {
        this.resumeTenantId = resumeTenantId;
    }

    public Integer getResumeTenantUserId() {
        return resumeTenantUserId;
    }

    public void setResumeTenantUserId(Integer resumeTenantUserId) {
        this.resumeTenantUserId = resumeTenantUserId;
    }

    public Integer getResumeTenantStaffId() {
        return resumeTenantStaffId;
    }

    public void setResumeTenantStaffId(Integer resumeTenantStaffId) {
        this.resumeTenantStaffId = resumeTenantStaffId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
