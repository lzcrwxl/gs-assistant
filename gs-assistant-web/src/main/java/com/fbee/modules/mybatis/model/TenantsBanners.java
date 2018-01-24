package com.fbee.modules.mybatis.model;

import java.util.Date;

public class TenantsBanners {
    /**
     * 表：tenants_banners
     * 字段：ID
     * 注释：主键ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：tenants_banners
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：tenants_banners
     * 字段：BANNER_NAME
     * 注释：banner名称
     *
     * @mbggenerated
     */
    private String bannerName;

    /**
     * 表：tenants_banners
     * 字段：BANNER_URL
     * 注释：banner图片路径
     *
     * @mbggenerated
     */
    private String bannerUrl;

    /**
     * 表：tenants_banners
     * 字段：SORT_NO
     * 注释：排序(预留字段)
     *
     * @mbggenerated
     */
    private Integer sortNo;

    /**
     * 表：tenants_banners
     * 字段：STATUS
     * 注释：状态 00:上架
     *
     * @mbggenerated
     */
    private String status;

    /**
     * 表：tenants_banners
     * 字段：SHELF_TYPE
     * 注释：上架类型 00 直接上架 01 指定时间上架
     *
     * @mbggenerated
     */
    private String shelfType;

    /**
     * 表：tenants_banners
     * 字段：SHELF_TIME
     * 注释：上架时间(预留字段)
     *
     * @mbggenerated
     */
    private Date shelfTime;

    /**
     * 表：tenants_banners
     * 字段：IS_DEFAULT
     * 注释：是否为系统默认图：0 系统默认图 1 自定义图
     *
     * @mbggenerated
     */
    private String isDefault;

    /**
     * 表：tenants_banners
     * 字段：remarks
     * 注释：备注(预留字段)
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 表：tenants_banners
     * 字段：is_usable
     * 注释：是否可用
     *
     * @mbggenerated
     */
    private String isUsable;

    /**
     * 表：tenants_banners
     * 字段：add_time
     * 注释：添加时间(预留字段)
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：tenants_banners
     * 字段：add_account
     * 注释：添加人(预留字段)
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：tenants_banners
     * 字段：modify_time
     * 注释：修改时间(预留字段)
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：tenants_banners
     * 字段：modify_account
     * 注释：修改人(预留字段)
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

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName == null ? null : bannerName.trim();
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl == null ? null : bannerUrl.trim();
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getShelfType() {
        return shelfType;
    }

    public void setShelfType(String shelfType) {
        this.shelfType = shelfType == null ? null : shelfType.trim();
    }

    public Date getShelfTime() {
        return shelfTime;
    }

    public void setShelfTime(Date shelfTime) {
        this.shelfTime = shelfTime;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
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