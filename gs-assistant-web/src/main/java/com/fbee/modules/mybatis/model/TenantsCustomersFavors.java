package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class TenantsCustomersFavors implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_customers_favors
     * 字段：CUSTOMER_ID
     * 注释：客户id
     *
     * @mbggenerated
     */
    private Integer customerId;

    /**
     * 表：tenants_customers_favors
     * 字段：FAVOR_CODE
     * 注释：偏好code
     *
     * @mbggenerated
     */
    private String favorCode;

    /**
     * 表：tenants_customers_favors
     * 字段：FAVOR_VALUE
     * 注释：偏好值
     *
     * @mbggenerated
     */
    private String favorValue;

    /**
     * 表：tenants_customers_favors
     * 字段：FAVOR_REMARK
     * 注释：偏好备注
     *
     * @mbggenerated
     */
    private String favorRemark;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFavorCode() {
        return favorCode;
    }

    public void setFavorCode(String favorCode) {
        this.favorCode = favorCode == null ? null : favorCode.trim();
    }

    public String getFavorValue() {
        return favorValue;
    }

    public void setFavorValue(String favorValue) {
        this.favorValue = favorValue == null ? null : favorValue.trim();
    }

    public String getFavorRemark() {
        return favorRemark;
    }

    public void setFavorRemark(String favorRemark) {
        this.favorRemark = favorRemark == null ? null : favorRemark.trim();
    }
}