package com.fbee.modules.wechat.message.model;


/**
 * -pp5mrTw-u8wTvG6gHUSJS5FOGenXQFH6qhJuc66_7U
 */
public class ResumeMessageModel extends MessageModel{

    private String title;

    private Integer jobId;
    private Integer resumeId;

    private Integer jobTenantUserId;
    /**
     * 家政员ID
     */
    private Integer staffId;

    /**
     * 职位名称
     */
    private String jobName;
    /**
     * 个人姓名
     */
    private String name;
    /**
     * 工作经验
     */
    private String experience;
    /**
     * remark
     */
    private String remark;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public Integer getJobTenantUserId() {
        return jobTenantUserId;
    }

    public void setJobTenantUserId(Integer jobTenantUserId) {
        this.jobTenantUserId = jobTenantUserId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
