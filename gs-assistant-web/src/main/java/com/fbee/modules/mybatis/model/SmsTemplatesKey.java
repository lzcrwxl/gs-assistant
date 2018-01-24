package com.fbee.modules.mybatis.model;

import java.io.Serializable;

public class SmsTemplatesKey implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：sms_templates
     * 字段：TEMPLATE_ID
     * 注释：模板id
     *
     * @mbggenerated
     */
    private String templateId;

    /**
     * 表：sms_templates
     * 字段：SP_CODE
     * 注释：渠道
     *
     * @mbggenerated
     */
    private String spCode;

    /**
     * 表：sms_templates
     * 字段：VERSION
     * 注释：版本（预留字段）
     *
     * @mbggenerated
     */
    private Integer version;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode == null ? null : spCode.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}