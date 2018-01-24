package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class FormConfigs implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：form_configs
     * 字段：FORM_CODE
     * 注释：表单Code
     *
     * @mbggenerated
     */
    private String formCode;

    /**
     * 表：form_configs
     * 字段：FORM_NAME
     * 注释：表单Code
     *
     * @mbggenerated
     */
    private String formName;

    /**
     * 表：form_configs
     * 字段：VALID_PROPERTY
     * 注释：校验字段
     *
     * @mbggenerated
     */
    private String validProperty;

    /**
     * 表：form_configs
     * 字段：IS_REQUIRED
     * 注释：是否必填
     *
     * @mbggenerated
     */
    private String isRequired;

    /**
     * 表：form_configs
     * 字段：VALID_RULES
     * 注释：校验规则
     *
     * @mbggenerated
     */
    private String validRules;

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode == null ? null : formCode.trim();
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName == null ? null : formName.trim();
    }

    public String getValidProperty() {
        return validProperty;
    }

    public void setValidProperty(String validProperty) {
        this.validProperty = validProperty == null ? null : validProperty.trim();
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired == null ? null : isRequired.trim();
    }

    public String getValidRules() {
        return validRules;
    }

    public void setValidRules(String validRules) {
        this.validRules = validRules == null ? null : validRules.trim();
    }
}