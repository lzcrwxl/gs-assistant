package com.fbee.modules.form;

import java.math.BigDecimal;
import java.util.Date;
import com.fbee.modules.core.persistence.ModelSerializable;

/**
 * 客户信息
 * @author Administrator
 *
 */
public class CustomerSaveForm implements ModelSerializable {
	
	private static final long serialVersionUID = 1L;
	
//	/**
//     * 表：tenants_customers_base
//     * 字段：CUSTOMER_ID
//     * 注释：客户id
//     *
//     * @mbggenerated
//     */
//    private Integer customerId;
//
//    /**
//     * 表：tenants_customers_base
//     * 字段：TENANT_ID
//     * 注释：租户id
//     *
//     * @mbggenerated
//     */
//    private Integer tenantId;

    private Integer tenantId;

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 表：tenants_customers_base
     * 字段：CUSTOMER_MOBILE
     * 注释：客户电话
     *
     * @mbggenerated
     */
    private String customerMobile;

    /**
     * 表：tenants_customers_base
     * 字段：CUSTOMER_NAME
     * 注释：客户姓名
     *
     * @mbggenerated
     */
    private String customerName;

    /**
     * 表：tenants_customers_base
     * 字段：SEX
     * 注释：性别
     *
     * @mbggenerated
     */
    private String sex;

    /**
     * 表：tenants_customers_base
     * 字段：CONTACT_MOBILE
     * 注释：紧急联系电话
     *
     * @mbggenerated
     */
    private String contactMobile;

    /**
     * 表：tenants_customers_base
     * 字段：CONTACT_ADDRESS
     * 注释：联系地址
     *
     * @mbggenerated
     */
    private String contactAddress;

    /**
     * 表：tenants_customers_base
     * 字段：AGE_RANGE
     * 注释：年龄段
     *
     * @mbggenerated
     */
    private String ageRange;

    /**
     * 表：tenants_customers_base
     * 字段：remarks
     * 注释：备注
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 表：tenants_customers_base
     * 字段：is_usable
     * 注释：是否可用
     *
     * @mbggenerated
     */
    private String isUsable;

    /**
     * 表：tenants_customers_base
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：tenants_customers_base
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：tenants_customers_base
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：tenants_customers_base
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

    /**
     * 表：tenants_customers_base
     * 字段：HOUSE_TYPE
     * 注释：住宅类型
     *
     * @mbggenerated
     */
    private String houseType;

    /**
     * 表：tenants_customers_base
     * 字段：HOUSE_AREA
     * 注释：住宅面积
     *
     * @mbggenerated
     */
    private BigDecimal houseArea;

    /**
     * 表：tenants_customers_base
     * 字段：FAMILY_COUNT
     * 注释：家庭人数
     *
     * @mbggenerated
     */
    private Integer familyCount;

    /**
     * 表：tenants_customers_base
     * 字段：CHILDREN_COUNT
     * 注释：儿童数
     *
     * @mbggenerated
     */
    private Integer childrenCount;

    /**
     * 表：tenants_customers_base
     * 字段：OLDER_COUNT
     * 注释：老人数
     *
     * @mbggenerated
     */
    private Integer olderCount;

    /**
     * 表：tenants_customers_base
     * 字段：CHILDREN_AGE_RANGE
     * 注释：儿童年龄段
     *
     * @mbggenerated
     */
    private String childrenAgeRange;

    /**
     * 表：tenants_customers_base
     * 字段：OLDER_AGE_RANGE
     * 注释：老人年龄段
     *
     * @mbggenerated
     */
    private String olderAgeRange;

    /**
     * 表：tenants_customers_base
     * 字段：SELF_CARES
     * 注释：老人能都自理
     *
     * @mbggenerated
     */
    private String selfCares;

    /**
     * 表：tenants_customers_base
     * 字段：SERVICE_PROVICE
     * 注释：
     *
     * @mbggenerated
     */
    private String serviceProvice;

    /**
     * 表：tenants_customers_base
     * 字段：SERVICE_CITY
     * 注释：
     *
     * @mbggenerated
     */
    private String serviceCity;

    /**
     * 表：tenants_customers_base
     * 字段：SERVICE_COUNTY
     * 注释：
     *
     * @mbggenerated
     */
    private String serviceCounty;

//    public Integer getCustomerId() {
//        return customerId;
//    }
//
//    public void setCustomerId(Integer customerId) {
//        this.customerId = customerId;
//    }
//
//    public Integer getTenantId() {
//        return tenantId;
//    }
//
//    public void setTenantId(Integer tenantId) {
//        this.tenantId = tenantId;
//    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile == null ? null : customerMobile.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile == null ? null : contactMobile.trim();
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress == null ? null : contactAddress.trim();
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange == null ? null : ageRange.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable == null ? null : isUsable.trim();
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

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType == null ? null : houseType.trim();
    }

    public BigDecimal getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(BigDecimal houseArea) {
        this.houseArea = houseArea;
    }

    public Integer getFamilyCount() {
        return familyCount;
    }

    public void setFamilyCount(Integer familyCount) {
        this.familyCount = familyCount;
    }

    public Integer getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(Integer childrenCount) {
        this.childrenCount = childrenCount;
    }

    public Integer getOlderCount() {
        return olderCount;
    }

    public void setOlderCount(Integer olderCount) {
        this.olderCount = olderCount;
    }

    public String getChildrenAgeRange() {
        return childrenAgeRange;
    }

    public void setChildrenAgeRange(String childrenAgeRange) {
        this.childrenAgeRange = childrenAgeRange == null ? null : childrenAgeRange.trim();
    }

    public String getOlderAgeRange() {
        return olderAgeRange;
    }

    public void setOlderAgeRange(String olderAgeRange) {
        this.olderAgeRange = olderAgeRange == null ? null : olderAgeRange.trim();
    }

    public String getSelfCares() {
        return selfCares;
    }

    public void setSelfCares(String selfCares) {
        this.selfCares = selfCares == null ? null : selfCares.trim();
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
	
}
