package com.fbee.modules.jsonData.extend;

import com.fbee.modules.core.persistence.ModelSerializable;

public class StaffInfoQueryJson implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
    private String staffId;//阿姨id
	
	private String staffName;//阿姨姓名
	
	private String headImage;//图像
	
	private String education;//学历，如：本科(营养学)
	
	private String workStatus;//工作状态，如：服务中
	
    private String baseInfo;//基本信息，如：26/羊/上海人
	
	private String serviceType;//服务工种，如：保姆,陪护,日常保洁

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(String baseInfo) {
		this.baseInfo = baseInfo;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
}
