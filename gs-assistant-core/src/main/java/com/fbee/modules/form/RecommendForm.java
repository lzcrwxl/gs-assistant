package com.fbee.modules.form;

import java.util.Date;

import com.fbee.modules.core.persistence.ModelSerializable;

public class RecommendForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
    private Integer id;//ID

    private Integer tenantId;//租户Id

    private Integer staffId;//租户阿姨Id

    private Date addTime;//添加时间

    private String addAccount;//添加人

    private Date modifyTime;//修改时间

    private String modifyAccount;//改人

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

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
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
	
}
