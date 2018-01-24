package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class CtlMenus implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
     * 表：ctl_menus
     * 字段：id
     * 注释：主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：ctl_menus
     * 字段：parent_id
     * 注释：父级菜单ID
     *
     * @mbggenerated
     */
    private Integer parentId;

    /**
     * 表：ctl_menus
     * 字段：parent_ids
     * 注释：所有父级菜单id
     *
     * @mbggenerated
     */
    private String parentIds;

    /**
     * 表：ctl_menus
     * 字段：name
     * 注释：菜单名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 表：ctl_menus
     * 字段：sort
     * 注释：排序
     *
     * @mbggenerated
     */
    private String sort;

    /**
     * 表：ctl_menus
     * 字段：href
     * 注释：请求链接
     *
     * @mbggenerated
     */
    private String href;

    /**
     * 表：ctl_menus
     * 字段：target
     * 注释：跳转方式
     *
     * @mbggenerated
     */
    private String target;

    /**
     * 表：ctl_menus
     * 字段：icon
     * 注释：菜单图标
     *
     * @mbggenerated
     */
    private String icon;

    /**
     * 表：ctl_menus
     * 字段：is_show
     * 注释：是否显示
     *
     * @mbggenerated
     */
    private String isShow;

    /**
     * 表：ctl_menus
     * 字段：permission
     * 注释：权限标识
     *
     * @mbggenerated
     */
    private String permission;

    /**
     * 表：ctl_menus
     * 字段：remarks
     * 注释：备注
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 表：ctl_menus
     * 字段：is_usable
     * 注释：是否可用
     *
     * @mbggenerated
     */
    private String isUsable;

    /**
     * 表：ctl_menus
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：ctl_menus
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：ctl_menus
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：ctl_menus
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href == null ? null : href.trim();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow == null ? null : isShow.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
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