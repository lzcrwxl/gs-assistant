package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class TenantsContactBar implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 表：tenants_contact_bar
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：tenants_contact_bar
     * 字段：CONTACT_PHONE
     * 注释：租户联系电话
     *
     * @mbggenerated
     */
    private String contactPhone;

    /**
     * 表：tenants_contact_bar
     * 字段：QQ_ONE
     * 注释：租户联系QQ1
     *
     * @mbggenerated
     */
    private String qqOne;

    /**
     * 表：tenants_contact_bar
     * 字段：QQ_TWO
     * 注释：租户联系QQ2
     *
     * @mbggenerated
     */
    private String qqTwo;

    /**
     * 表：tenants_contact_bar
     * 字段：QQ_THREE
     * 注释：租户联系QQ3
     *
     * @mbggenerated
     */
    private String qqThree;

    /**
     * 表：tenants_contact_bar
     * 字段：QR_CODE
     * 注释：租户微信二维码
     *
     * @mbggenerated
     */
    private String qrCode;

    /**
     * 表：tenants_contact_bar
     * 字段：IS_OPEN_MOBILE
     * 注释：是否开启电话
     *
     * @mbggenerated
     */
    private String isOpenMobile;

    /**
     * 表：tenants_contact_bar
     * 字段：IS_OPEN_QQ
     * 注释：是否开启QQ
     *
     * @mbggenerated
     */
    private String isOpenQq;

    /**
     * 表：tenants_contact_bar
     * 字段：IS_OPEN_QR_CODE
     * 注释：是否开启二维码
     *
     * @mbggenerated
     */
    private String isOpenQrCode;

    /**
     * 表：tenants_contact_bar
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：tenants_contact_bar
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：tenants_contact_bar
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：tenants_contact_bar
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

    /**
     * 表：tenants_contact_bar
     * 字段：is_usable
     * 注释：
     *
     * @mbggenerated
     */
    private String isUsable;

    /**
     * 表：tenants_contact_bar
     * 字段：IS_OPEN_QQ_ONE
     * 注释：是否展示第一个QQ
     *
     * @mbggenerated
     */
    private String isOpenQqOne;

    /**
     * 表：tenants_contact_bar
     * 字段：IS_OPEN_QQ_TWO
     * 注释：是否展示第二个QQ
     *
     * @mbggenerated
     */
    private String isOpenQqTwo;

    /**
     * 表：tenants_contact_bar
     * 字段：IS_OPEN_QQ_THREE
     * 注释：是否展示第三个QQ
     *
     * @mbggenerated
     */
    private String isOpenQqThree;

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getQqOne() {
        return qqOne;
    }

    public void setQqOne(String qqOne) {
        this.qqOne = qqOne == null ? null : qqOne.trim();
    }

    public String getQqTwo() {
        return qqTwo;
    }

    public void setQqTwo(String qqTwo) {
        this.qqTwo = qqTwo == null ? null : qqTwo.trim();
    }

    public String getQqThree() {
        return qqThree;
    }

    public void setQqThree(String qqThree) {
        this.qqThree = qqThree == null ? null : qqThree.trim();
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }

    public String getIsOpenMobile() {
        return isOpenMobile;
    }

    public void setIsOpenMobile(String isOpenMobile) {
        this.isOpenMobile = isOpenMobile == null ? null : isOpenMobile.trim();
    }

    public String getIsOpenQq() {
        return isOpenQq;
    }

    public void setIsOpenQq(String isOpenQq) {
        this.isOpenQq = isOpenQq == null ? null : isOpenQq.trim();
    }

    public String getIsOpenQrCode() {
        return isOpenQrCode;
    }

    public void setIsOpenQrCode(String isOpenQrCode) {
        this.isOpenQrCode = isOpenQrCode == null ? null : isOpenQrCode.trim();
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

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable == null ? null : isUsable.trim();
    }

    public String getIsOpenQqOne() {
        return isOpenQqOne;
    }

    public void setIsOpenQqOne(String isOpenQqOne) {
        this.isOpenQqOne = isOpenQqOne == null ? null : isOpenQqOne.trim();
    }

    public String getIsOpenQqTwo() {
        return isOpenQqTwo;
    }

    public void setIsOpenQqTwo(String isOpenQqTwo) {
        this.isOpenQqTwo = isOpenQqTwo == null ? null : isOpenQqTwo.trim();
    }

    public String getIsOpenQqThree() {
        return isOpenQqThree;
    }

    public void setIsOpenQqThree(String isOpenQqThree) {
        this.isOpenQqThree = isOpenQqThree == null ? null : isOpenQqThree.trim();
    }
}