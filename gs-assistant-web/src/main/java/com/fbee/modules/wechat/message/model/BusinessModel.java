package com.fbee.modules.wechat.message.model;

/**
 * YEJ6zYFloJcpsVnQH1YjDtlUJbAUsrdFn3-U9voZNxU
 */
public class BusinessModel extends MessageModel {

    private Integer staffId;
    private Integer userId;
    private Integer jobId;
    private Integer resumeId;
    private String title;
    private String keywordFirst;
    private String keywordSecond;
    private String keywordThird;
    private String remark;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywordFirst() {
        return keywordFirst;
    }

    public void setKeywordFirst(String keywordFirst) {
        this.keywordFirst = keywordFirst;
    }

    public String getKeywordSecond() {
        return keywordSecond;
    }

    public void setKeywordSecond(String keywordSecond) {
        this.keywordSecond = keywordSecond;
    }

    public String getKeywordThird() {
        return keywordThird;
    }

    public void setKeywordThird(String keywordThird) {
        this.keywordThird = keywordThird;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
