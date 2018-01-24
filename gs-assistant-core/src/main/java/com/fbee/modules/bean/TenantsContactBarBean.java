package com.fbee.modules.bean;

import java.util.Date;

public class TenantsContactBarBean{
	private Integer tenantId;// 租户id
	private String contactPhone;// 联系方式
	private String qqOne;// 第一个qq
	private String qqTwo;// 第二个qq
	private String qqThree;// 第三个qq
	private String qrCode;// 微信你二维码
	private String isOpenMobile;// 前端是否显示电话
	private String isOpenQq;// 前端是否显示qq
	private String isOpenQrCode;// 前端是否显示微信二维码
	private Date addTime;// 添加时间
	private String addAccount;// 添加人
	private Date modifyTime;// 修改时间
	private String modifyAccount;// 修改人

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
		this.contactPhone = contactPhone;
	}

	public String getQqOne() {
		return qqOne;
	}

	public void setQqOne(String qqOne) {
		this.qqOne = qqOne;
	}

	public String getQqTwo() {
		return qqTwo;
	}

	public void setQqTwo(String qqTwo) {
		this.qqTwo = qqTwo;
	}

	public String getQqThree() {
		return qqThree;
	}

	public void setQqThree(String qqThree) {
		this.qqThree = qqThree;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getIsOpenMobile() {
		return isOpenMobile;
	}

	public void setIsOpenMobile(String isOpenMobile) {
		this.isOpenMobile = isOpenMobile;
	}

	public String getIsOpenQq() {
		return isOpenQq;
	}

	public void setIsOpenQq(String isOpenQq) {
		this.isOpenQq = isOpenQq;
	}

	public String getIsOpenQrCode() {
		return isOpenQrCode;
	}

	public void setIsOpenQrCode(String isOpenQrCode) {
		this.isOpenQrCode = isOpenQrCode;
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

	public String getIsOpenQqOne() {
		return isOpenQqOne;
	}

	public void setIsOpenQqOne(String isOpenQqOne) {
		this.isOpenQqOne = isOpenQqOne;
	}

	public String getIsOpenQqTwo() {
		return isOpenQqTwo;
	}

	public void setIsOpenQqTwo(String isOpenQqTwo) {
		this.isOpenQqTwo = isOpenQqTwo;
	}

	public String getIsOpenQqThree() {
		return isOpenQqThree;
	}

	public void setIsOpenQqThree(String isOpenQqThree) {
		this.isOpenQqThree = isOpenQqThree;
	}
	
}
