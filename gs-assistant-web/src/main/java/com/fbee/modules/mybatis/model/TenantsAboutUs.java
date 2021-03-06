package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class TenantsAboutUs implements Serializable{
    
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tenants_about_us.id
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tenants_about_us.TENANT_ID
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	private Integer tenantId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tenants_about_us.content
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	private String content;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tenants_about_us.images
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	private String images;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tenants_about_us.add_time
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	private Date addTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tenants_about_us.add_account
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	private String addAccount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tenants_about_us.modify_time
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tenants_about_us.modify_account
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	private String modifyAccount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tenants_about_us.is_default
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	private String isDefault;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column tenants_about_us.is_usable
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	private String isUsable;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tenants_about_us.id
	 * @return  the value of tenants_about_us.id
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tenants_about_us.id
	 * @param id  the value for tenants_about_us.id
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tenants_about_us.TENANT_ID
	 * @return  the value of tenants_about_us.TENANT_ID
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public Integer getTenantId() {
		return tenantId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tenants_about_us.TENANT_ID
	 * @param tenantId  the value for tenants_about_us.TENANT_ID
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tenants_about_us.content
	 * @return  the value of tenants_about_us.content
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tenants_about_us.content
	 * @param content  the value for tenants_about_us.content
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tenants_about_us.images
	 * @return  the value of tenants_about_us.images
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public String getImages() {
		return images;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tenants_about_us.images
	 * @param images  the value for tenants_about_us.images
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public void setImages(String images) {
		this.images = images;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tenants_about_us.add_time
	 * @return  the value of tenants_about_us.add_time
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tenants_about_us.add_time
	 * @param addTime  the value for tenants_about_us.add_time
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tenants_about_us.add_account
	 * @return  the value of tenants_about_us.add_account
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public String getAddAccount() {
		return addAccount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tenants_about_us.add_account
	 * @param addAccount  the value for tenants_about_us.add_account
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public void setAddAccount(String addAccount) {
		this.addAccount = addAccount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tenants_about_us.modify_time
	 * @return  the value of tenants_about_us.modify_time
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tenants_about_us.modify_time
	 * @param modifyTime  the value for tenants_about_us.modify_time
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tenants_about_us.modify_account
	 * @return  the value of tenants_about_us.modify_account
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public String getModifyAccount() {
		return modifyAccount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tenants_about_us.modify_account
	 * @param modifyAccount  the value for tenants_about_us.modify_account
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public void setModifyAccount(String modifyAccount) {
		this.modifyAccount = modifyAccount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tenants_about_us.is_default
	 * @return  the value of tenants_about_us.is_default
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public String getIsDefault() {
		return isDefault;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tenants_about_us.is_default
	 * @param isDefault  the value for tenants_about_us.is_default
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column tenants_about_us.is_usable
	 * @return  the value of tenants_about_us.is_usable
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public String getIsUsable() {
		return isUsable;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column tenants_about_us.is_usable
	 * @param isUsable  the value for tenants_about_us.is_usable
	 * @mbggenerated  Thu Mar 09 15:06:40 CST 2017
	 */
	public void setIsUsable(String isUsable) {
		this.isUsable = isUsable;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "TenantsAboutUs [id=" + id + ", tenantId=" + tenantId + ", content=" + content + ", images=" + images
				+ ", addTime=" + addTime + ", addAccount=" + addAccount + ", modifyTime=" + modifyTime
				+ ", modifyAccount=" + modifyAccount + ", isDefault=" + isDefault + ", isUsable=" + isUsable + "]";
	}
	
	

}