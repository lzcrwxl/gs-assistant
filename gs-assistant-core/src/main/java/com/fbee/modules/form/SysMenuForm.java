package com.fbee.modules.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单
 * @author Administrator
 *
 */
public class SysMenuForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;	//主键

    private Integer parentId;	//父级菜单ID

    private String parentIds;	//所有父级菜单id

    private String name;	//菜单名称

    private String sort;	//排序

    private String href;	//请求链接

    private String target;	//跳转方式

    private String icon;	//菜单图标

    private Integer isShow;	//是否显示(0：不显示 1：显示)

    private String permission;	//权限标识

    private String isBase;		//是否基础模块

    private String remarks;		//备注

    private String isUsable;	//是否可用(0：不可用 1：可用)

    private String addAccount;	//添加人

    private Date modifyTime;	//修改时间

    private String modifyAccount;	//修改人

    private Date createDate;	//创建时间

    private Integer state;		//状态：（0：未删除 1：删除）
    
    private List<SysMenuForm> subMenuList;//属性的集合

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
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getIsBase() {
        return isBase;
    }

    public void setIsBase(String isBase) {
        this.isBase = isBase;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable;
    }

    public String getAddAccount() {
        return addAccount;
    }

    public void setAddAccount(String addAccount) {
        this.addAccount = addAccount;
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
        this.modifyAccount = modifyAccount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	public List<SysMenuForm> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<SysMenuForm> subMenuList) {
		this.subMenuList = subMenuList;
	}
}