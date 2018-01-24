package com.fbee.modules.form.extend;

import java.math.BigDecimal;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffServiceItemform 
* @Description: 员工（阿姨）服务工种--用于接收参数form
* @author 贺章鹏
* @date 2017年1月3日 下午4:16:33 
*  
*/
public class StaffServiceItemform implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private String serviceItemCode;//服务工种
	
	private String skills;//技能特点
	
	private String serviceNature;//服务类型

	private Integer id;//添加工种id

	public void setServiceItemCodeId(Integer serviceItemCodeId){
		this.id = serviceItemCodeId;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getServiceNature() {
		return serviceNature;
	}

	public void setServiceNature(String serviceNature) {
		this.serviceNature = serviceNature;
	}

}
