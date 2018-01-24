package com.fbee.modules.mybatis.model;

public class TenantsStaffsFeatures extends TenantsStaffsFeaturesKey {
	private static final long serialVersionUID = 1L;
	/**
     * 表：tenants_staffs_features
     * 字段：FEATURE_VALUE
     * 注释：特点值
     *
     * @mbggenerated
     */
    private String featureValue;
    
    /**
     * 表：tenants_staffs_features
     * 字段：OTHER_VALUE
     * 注释：其他值（填写其它时填写的值）
     *
     * @mbggenerated
     */
    private String otherValue;

    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue == null ? null : featureValue.trim();
    }

	public String getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}
    
}