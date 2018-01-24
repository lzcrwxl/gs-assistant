package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffQueryForm 
* @Description: 员工查询表单
* @author 贺章鹏
* @date 2016年12月29日 下午4:53:29 
*  
*/
public class StaffQueryForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private String staffName;//家政员姓名
	
	private String mobile;//家政员手机号码
	
	private String serviceItemCode;//服务工种
	
	private String experience;//从业经历
	
	private String[] skills;//技能点
	
	private String education;//学历
	
	private String age;//年龄
	
	private String zodiac;//属相
	
	private String nativePlace;//籍贯
	
	private String workStatus;//工作状态
	
	private String[] languageFeature;//语言技能
	
	private String[] cookingFeature;//烹饪技能
	
	private String[] characerFeature;//性格特点
	
	private String petFeeding;//是否饲养宠物
	
	private String elderlySupport;//是否赡养老人
	
	private String onOffShelf;//上下架
	
	private String certType;//证书类型(证书名称)
	
	private String authenticateGrade;//鉴定等级
	
	private String staffNo;//阿姨编号
	
	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getAuthenticateGrade() {
		return authenticateGrade;
	}

	public void setAuthenticateGrade(String authenticateGrade) {
		this.authenticateGrade = authenticateGrade;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String[] getSkills() {
		return skills;
	}

	public void setSkills(String[] skills) {
		this.skills = skills;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getZodiac() {
		return zodiac;
	}

	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String[] getLanguageFeature() {
		return languageFeature;
	}

	public void setLanguageFeature(String[] languageFeature) {
		this.languageFeature = languageFeature;
	}

	public String[] getCookingFeature() {
		return cookingFeature;
	}

	public void setCookingFeature(String[] cookingFeature) {
		this.cookingFeature = cookingFeature;
	}

	public String[] getCharacerFeature() {
		return characerFeature;
	}

	public void setCharacerFeature(String[] characerFeature) {
		this.characerFeature = characerFeature;
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

	public String getOnOffShelf() {
		return onOffShelf;
	}

	public void setOnOffShelf(String onOffShelf) {
		this.onOffShelf = onOffShelf;
	}
	
	//增加查询条件
	//服务区域
	
}
