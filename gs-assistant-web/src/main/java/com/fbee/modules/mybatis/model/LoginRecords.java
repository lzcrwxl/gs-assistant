package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class LoginRecords implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：login_records
     * 字段：id
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：login_records
     * 字段：login_account
     * 注释：登陆账号
     *
     * @mbggenerated
     */
    private String loginAccount;

    /**
     * 表：login_records
     * 字段：login_time
     * 注释：登陆时间
     *
     * @mbggenerated
     */
    private Date loginTime;

    /**
     * 表：login_records
     * 字段：login_ip
     * 注释：登陆ip
     *
     * @mbggenerated
     */
    private String loginIp;

    /**
     * 表：login_records
     * 字段：is_succeed
     * 注释：是否成功
     *
     * @mbggenerated
     */
    private String isSucceed;

    /**
     * 表：login_records
     * 字段：failed_reason
     * 注释：失败原因
     *
     * @mbggenerated
     */
    private String failedReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount == null ? null : loginAccount.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(String isSucceed) {
        this.isSucceed = isSucceed == null ? null : isSucceed.trim();
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason == null ? null : failedReason.trim();
    }
}