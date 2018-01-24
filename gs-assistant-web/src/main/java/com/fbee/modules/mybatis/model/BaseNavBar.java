package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class BaseNavBar implements Serializable{
   
	private static final long serialVersionUID = 1L;

	/**
     * 表：base_nav_bar
     * 字段：ID
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：base_nav_bar
     * 字段：BAR_NAME
     * 注释：导航名称
     *
     * @mbggenerated
     */
    private String barName;

    /**
     * 表：base_nav_bar
     * 字段：BAR_ICON
     * 注释：导航图标
     *
     * @mbggenerated
     */
    private String barIcon;

    /**
     * 表：base_nav_bar
     * 字段：BAR_URL
     * 注释：链接地址
     *
     * @mbggenerated
     */
    private String barUrl;

    /**
     * 表：base_nav_bar
     * 字段：IS_BASE
     * 注释：是否基础模块
     *
     * @mbggenerated
     */
    private String isBase;

    /**
     * 表：base_nav_bar
     * 字段：remarks
     * 注释：备注
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 表：base_nav_bar
     * 字段：is_usable
     * 注释：是否可用
     *
     * @mbggenerated
     */
    private String isUsable;

    /**
     * 表：base_nav_bar
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：base_nav_bar
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：base_nav_bar
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：base_nav_bar
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

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName == null ? null : barName.trim();
    }

    public String getBarIcon() {
        return barIcon;
    }

    public void setBarIcon(String barIcon) {
        this.barIcon = barIcon == null ? null : barIcon.trim();
    }

    public String getBarUrl() {
        return barUrl;
    }

    public void setBarUrl(String barUrl) {
        this.barUrl = barUrl == null ? null : barUrl.trim();
    }

    public String getIsBase() {
        return isBase;
    }

    public void setIsBase(String isBase) {
        this.isBase = isBase == null ? null : isBase.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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