package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class SmsRecords implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：sms_records
     * 字段：id
     * 注释：
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：sms_records
     * 字段：SMS_ID
     * 注释：
     *
     * @mbggenerated
     */
    private String smsId;

    /**
     * 表：sms_records
     * 字段：mobile
     * 注释：
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 表：sms_records
     * 字段：content_type
     * 注释：
     *
     * @mbggenerated
     */
    private String contentType;

    /**
     * 表：sms_records
     * 字段：content
     * 注释：
     *
     * @mbggenerated
     */
    private String content;

    /**
     * 表：sms_records
     * 字段：result
     * 注释：
     *
     * @mbggenerated
     */
    private String result;

    /**
     * 表：sms_records
     * 字段：failed_reason
     * 注释：
     *
     * @mbggenerated
     */
    private String failedReason;

    /**
     * 表：sms_records
     * 字段：sp_code
     * 注释：
     *
     * @mbggenerated
     */
    private String spCode;

    /**
     * 表：sms_records
     * 字段：add_time
     * 注释：
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：sms_records
     * 字段：result_confrim_time
     * 注释：
     *
     * @mbggenerated
     */
    private Date resultConfrimTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId == null ? null : smsId.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason == null ? null : failedReason.trim();
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode == null ? null : spCode.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getResultConfrimTime() {
        return resultConfrimTime;
    }

    public void setResultConfrimTime(Date resultConfrimTime) {
        this.resultConfrimTime = resultConfrimTime;
    }
}