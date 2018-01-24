package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class MembersInfo implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：members_info
     * 字段：ID
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    private String name;

    /**
     * 表：members_info
     * 字段：mobile
     * 注释：手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 表：members_info
     * 字段：salt
     * 注释：加密盐值
     *
     * @mbggenerated
     */
    private String salt;

    /**
     * 表：members_info
     * 字段：login_password
     * 注释：登陆密码
     *
     * @mbggenerated
     */
    private String loginPassword;

    /**
     * 表：members_info
     * 字段：user_status
     * 注释：用户状态 00 正常
     *
     * @mbggenerated
     */
    private String userStatus;

    /**
     * 表：members_info
     * 字段：open_id
     * 注释：微信open_id
     *
     * @mbggenerated
     */
    private String openId;

    /**
     * 表：members_info
     * 字段：register_time
     * 注释：注册时间
     *
     * @mbggenerated
     */
    private Date registerTime;

    /**
     * 表：members_info
     * 字段：pwd_try_count
     * 注释：密码尝试次数
     *
     * @mbggenerated
     */
    private Integer pwdTryCount;

    /**
     * 表：members_info
     * 字段：is_locked
     * 注释：是否锁定 0 未锁定  1锁定
     *
     * @mbggenerated
     */
    private String isLocked;

    /**
     * 表：members_info
     * 字段：lock_time
     * 注释：锁定时间
     *
     * @mbggenerated
     */
    private Date lockTime;

    /**
     * 表：members_info
     * 字段：last_login_time
     * 注释：最后登陆时间
     *
     * @mbggenerated
     */
    private Date lastLoginTime;

    /**
     * 表：members_info
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：members_info
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：members_info
     * 字段：first_wrong_time
     * 注释：第一次出错时间
     *
     * @mbggenerated
     */
    private Date firstWrongTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getPwdTryCount() {
        return pwdTryCount;
    }

    public void setPwdTryCount(Integer pwdTryCount) {
        this.pwdTryCount = pwdTryCount;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked == null ? null : isLocked.trim();
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getFirstWrongTime() {
        return firstWrongTime;
    }

    public void setFirstWrongTime(Date firstWrongTime) {
        this.firstWrongTime = firstWrongTime;
    }
}