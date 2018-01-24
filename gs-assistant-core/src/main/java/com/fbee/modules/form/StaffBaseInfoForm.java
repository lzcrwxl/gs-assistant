package com.fbee.modules.form;

import java.math.BigDecimal;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffBaseInfoForm 
* @Description: 员工（阿姨）基础信息新增
* @author 贺章鹏
* @date 2016年12月30日 上午11:46:23 
*  
*/
public class StaffBaseInfoForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;

	private Integer tenantId;


	/*****  身份证信息  ******/
	private String certNo;//身份证

	private String staffName;//员工姓名

	private String nation;//民族

	private String nativePlace;//籍贯

	private String houseAddress;//户籍地址

	private String certImage;//身份证头像编码


	/*****  个人信息  ******/
	private Integer staffId;//员工id
	
	private String education;//学历
	
	private String specialty;//专业
	
	private String maritalStatus;//婚姻状况
	
	private String fertilitySituation;//生育状况
	
	private String bloodType;//血型
	
	private String mobile;//手机号码
	
	private String contactPhone;//联系电话
	
	private String liveAddress;//现居地
	
	private String workStatus;//工作状态

	@Deprecated
	private Integer expectedSalary;//期望工资
	
	private BigDecimal height;//身高
	
	private BigDecimal weight;//体重
	
	private String onOffShelf;//上下架
	
	private String petFeeding;//不做家庭：是否喂养宠物
	
	private String elderlySupport;//不做家庭：是否有老人
	
	private String staffNo;//阿姨编号

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public String getCertImage() {
		return certImage;
	}

	public void setCertImage(String certImage) {
		this.certImage = certImage;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getPetFeeding() {
		return petFeeding;
	}

	public void setPetFeeding(String petFeeding) {
		this.petFeeding = petFeeding;
	}

	public String getElderlySupport() {
		return elderlySupport;
	}

	public void setElderlySupport(String elderlySupport) {
		this.elderlySupport = elderlySupport;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getFertilitySituation() {
		return fertilitySituation;
	}

	public void setFertilitySituation(String fertilitySituation) {
		this.fertilitySituation = fertilitySituation;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	@Deprecated
	public Integer getExpectedSalary() {
		return expectedSalary;
	}

	@Deprecated
	public void setExpectedSalary(Integer expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
	
	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	
	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	public String getOnOffShelf() {
		return onOffShelf;
	}

	public void setOnOffShelf(String onOffShelf) {
		this.onOffShelf = onOffShelf;
	}

	@Override
	public String toString(){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("staff baseInfo,{staffId:").append(staffId);
		return stringBuilder.toString();
	}
}
