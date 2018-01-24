package com.fbee.modules.mybatis.model;

import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.utils.DictionariesUtil;
import com.fbee.modules.utils.DictionarysCacheUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TenantsJobs implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 岗位名称
     */
    private String positionName;

    /**
     * 表：tenants_jobs
     * 字段：SERVICE_TYPE
     * 注释：服务工种
     *
     * @mbggenerated
     */
    private String serviceType;

    /**
     * 表：tenants_jobs
     * 字段：SERVICE_MOLD
     * 注释：服务类型
     *
     * @mbggenerated
     */
    private String serviceMold;

    /**
     * 表：tenants_jobs
     * 字段：AGE
     * 注释：年龄
     *
     * @mbggenerated
     */
    private String age;

    private BigDecimal salary;
    private String salaryType;

    /**
     * 表：tenants_jobs
     * 字段：DEMAND
     * 注释：要求
     *
     * @mbggenerated
     */
    private String demand;

    /**
     * 表：tenants_jobs
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * 表：tenants_jobs
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：tenants_jobs
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    /**
     * 表：tenants_jobs
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

    private String serviceProvince;
    private String serviceCity;
    private String serviceArea;

    /**
     * 技能要求
     */
    private String skillRequirements;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 服务开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serviceStartTime;
    /**
     * 服务结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serviceEndTime;

    /**
     * 可见范围
     */
    private String visualExtend;

    /**
     * 可应聘总人数
     */
    private Integer totalNum;

    /**
     * 已应聘人数
     */
    private Integer usedNum;

    /**
     * 是否上架(1是0否)
     */
    private String status;

    /**
     * 跟随order状态（待处理／待面试／已完成／已取消）
     */
    private String orderStatus;

    /**
     * 加急
     */
    private String emsSign;

    /**
     * 是否更新过 1是0否
     */
    private Integer isRefreshed;

    /**
     * 保证金
     */
    private BigDecimal deposit;

    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 结单时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishedTime;

    /**
     * 订单来源 家政公司名称
     */
    private String jobTenantName;

    public String getJobTenantName() {
        return jobTenantName;
    }

    public void setJobTenantName(String jobTenantName) {
        this.jobTenantName = jobTenantName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getIsRefreshed() {
        return isRefreshed;
    }

    public void setIsRefreshed(Integer isRefreshed) {
        this.isRefreshed = isRefreshed;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getEmsSign() {
        return emsSign;
    }

    public void setEmsSign(String emsSign) {
        this.emsSign = emsSign;
    }

    public String getSkillRequirements() {
        return skillRequirements;
    }

    /**
     * 技能
     *
     * @return
     */
    public String getSkillRequirementsValue() {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(skillRequirements)) {
            String[] sks = skillRequirements.split(",");
            for (String sk : sks) {
                sb.append(DictionarysCacheUtils.getSkillsStr(this.getServiceType(), sk)).append("、");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    public String getServiceProvinceValue(){
        return DictionarysCacheUtils.getProviceName(this.getServiceProvince());
    }
    public String getServiceCityValue() {
        return DictionarysCacheUtils.getCityName(this.getServiceCity());
    }
    public String getServiceAreaValue() {
        return DictionarysCacheUtils.getCountyName(this.getServiceArea());
    }

    /**
     * 服务类型
     *
     * @return
     */
    public String getServiceTypeValue() {
        return DictionarysCacheUtils.getServiceTypeName(this.getServiceType());
    }

    /**
     * 服务内容
     *
     * @return
     */
    public String getServiceMoldValue() {
        return DictionarysCacheUtils.getServiceNatureStr(this.getServiceType(), this.getServiceMold());
    }

    public String getAgeValue(){
        return DictionariesUtil.getAgerange(this.getAge());
    }

    public String getSalaryTypeValue() {
        return DictionarysCacheUtils.getServicePriceUnit(salaryType);
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
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

    public String getServiceMold() {
        return serviceMold;
    }

    public void setServiceMold(String serviceMold) {
        this.serviceMold = serviceMold;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Date getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(Date serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public Date getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(Date serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
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
        this.addAccount = addAccount;
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
        this.modifyAccount = modifyAccount;
    }

    public String getServiceProvince() {
        return serviceProvince;
    }

    public void setServiceProvince(String serviceProvince) {
        this.serviceProvince = serviceProvince;
    }

    public String getServiceCity() {
        return serviceCity;
    }

    public void setServiceCity(String serviceCity) {
        this.serviceCity = serviceCity;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public void setSkillRequirements(String skillRequirements) {
        this.skillRequirements = skillRequirements;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getVisualExtend() {
        return visualExtend;
    }

    public void setVisualExtend(String visualExtend) {
        this.visualExtend = visualExtend;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}