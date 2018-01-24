package com.fbee.modules.mybatis.model;

public class SmsTemplates extends SmsTemplatesKey {
    
	private static final long serialVersionUID = 1L;
	
	/**
     * 表：sms_templates
     * 字段：TEMPLATE_CONTENTS
     * 注释：模板内容
     *
     * @mbggenerated
     */
    private String templateContents;

    public String getTemplateContents() {
        return templateContents;
    }

    public void setTemplateContents(String templateContents) {
        this.templateContents = templateContents == null ? null : templateContents.trim();
    }
}