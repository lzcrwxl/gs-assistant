package com.fbee.modules.mybatis.model;

public class ModulesRentalConfigs extends ModulesRentalConfigsKey {
    
	private static final long serialVersionUID = 1L;
	
	/**
     * 表：modules_rental_configs
     * 字段：FEE_TYPE
     * 注释：00：免费 01：存在到期时间
     *
     * @mbggenerated
     */
    private String feeType;

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }
}