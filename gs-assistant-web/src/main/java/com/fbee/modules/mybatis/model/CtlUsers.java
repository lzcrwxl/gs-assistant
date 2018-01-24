package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class CtlUsers implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：ctl_users
     * 字段：id
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：ctl_users
     * 字段：login_account
     * 注释：登陆账号
     *
     * @mbggenerated
     */
    private String loginAccount;

    /**
     * 表：ctl_users
     * 字段：login_name
     * 注释：登陆名称
     *
     * @mbggenerated
     */
    private String loginName;

    /**
     * 表：ctl_users
     * 字段：password
     * 注释：密码
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 表：ctl_users
     * 字段：salt
     * 注释：盐值
     *
     * @mbggenerated
     */
    private String salt;

    /**
     * 表：ctl_users
     * 字段：telephone
     * 注释：手机号码
     *
     * @mbggenerated
     */
    private String telephone;

    /**
     * 表：ctl_users
     * 字段：remarks
     * 注释：备注
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 表：ctl_users
     * 字段：email
     * 注释：邮箱
     *
     * @mbggenerated
     */
    private String email;

    /**
     * 表：ctl_users
     * 字段：job_no
     * 注释：工号（预留）
     *
     * @mbggenerated
     */
    private String jobNo;

    /**
     * 表：ctl_users
     * 字段：is_init
     * 注释：是否初始化
     *
     * @mbggenerated
     */
    private String isInit;

    /**
     * 表：ctl_users
     * 字段：photo
     * 注释：图像
     *
     * @mbggenerated
     */
    private String photo;

    /**
     * 表：ctl_users
     * 字段：login_ip
     * 注释：最后登陆ip
     *
     * @mbggenerated
     */
    private String loginIp;

    /**
     * 表：ctl_users
     * 字段：login_flag
     * 注释：登陆标识
     *
     * @mbggenerated
     */
    private String loginFlag;

    /**
     * 表：ctl_users
     * 字段：login_time
     * 注释：最后登陆时间
     *
     * @mbggenerated
     */
    private Date loginTime;

    /**
     * 表：ctl_users
     * 字段：user_type
     * 注释：用户类型（预留字段）
     *
     * @mbggenerated
     */
    private String userType;

    /**
     * 表：ctl_users
     * 字段：is_usable
     * 注释：是否可用
     *
     * @mbggenerated
     */
    private String isUsable;

    /**
     * 表：ctl_users
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：ctl_users
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：ctl_users
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：ctl_users
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo == null ? null : jobNo.trim();
    }

    public String getIsInit() {
        return isInit;
    }

    public void setIsInit(String isInit) {
        this.isInit = isInit == null ? null : isInit.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag == null ? null : loginFlag.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable == null ? null : isUsable.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddAccount() {
        return addAccount;
    }

    public void setAddAccount(String addAccount) {
        this.addAccount = addAccount == null ? null : addAccount.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyAccount() {
        return modifyAccount;
    }

    public void setModifyAccount(String modifyAccount) {
        this.modifyAccount = modifyAccount == null ? null : modifyAccount.trim();
    }
}