package com.fbee.modules.form;

import java.math.BigDecimal;
import java.util.List;
import com.fbee.modules.core.persistence.ModelSerializable;
import com.fbee.modules.form.extend.StaffServiceItemform;

/** 
* @ClassName: StaffJobForm 
* @Description: 员工（阿姨）求职信息信息新增修改
* @author 贺章鹏
* @date 2017年1月3日 下午4:11:29 
*  
*/
public class StaffJobForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;//id主键
	
	private Integer staffId;//阿姨id
	
	private String manageWay;//管理方式
	
	private String serviceProvice;//省
	
	private String serviceCity;//市
	
	private String serviceCounty;//区
	
	private String workExperience;//工作经历
	
	private String selfEvaluation;//自我评价
	
	private String teacherEvaluation;//老师评价
	
	private List<StaffServiceItemform> serviceItems;//服务工种
	
	private String languageFeature;//语言特点
	
	private String characerFeature;//性格特点
	
	private String cookingFeature;//烹饪特点
	
	private String other;//语言其他值
	
	private String petFeeding;//不做家庭：是否喂养宠物
	
	private String elderlySupport;//不做家庭：是否有老人

	private String serviceArea;//服务地域
	
	private BigDecimal price;//期望工资

	private String unit;//工资单位
	private String experience;//从业经验 dictionary

	public void setPet(String pet){
		this.petFeeding = pet;
	}
	public void setElderly(String elderly){
		this.elderlySupport = elderly;
	}
	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void setSalaryType(String salaryType) {
		this.unit = salaryType;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getManageWay() {
		return manageWay;
	}

	public void setManageWay(String manageWay) {
		this.manageWay = manageWay;
	}

	public String getServiceProvice() {
		return serviceProvice;
	}

	public void setServiceProvice(String serviceProvice) {
		this.serviceProvice = serviceProvice;
	}

	public String getServiceCity() {
		return serviceCity;
	}

	public void setServiceCity(String serviceCity) {
		this.serviceCity = serviceCity;
	}

	public String getServiceCounty() {
		return serviceCounty;
	}

	public void setServiceCounty(String serviceCounty) {
		this.serviceCounty = serviceCounty;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getSelfEvaluation() {
		return selfEvaluation;
	}

	public void setSelfEvaluation(String selfEvaluation) {
		this.selfEvaluation = selfEvaluation;
	}

	public String getTeacherEvaluation() {
		return teacherEvaluation;
	}

	public void setTeacherEvaluation(String teacherEvaluation) {
		this.teacherEvaluation = teacherEvaluation;
	}

	public List<StaffServiceItemform> getServiceItems() {
		return serviceItems;
	}

	public void setServiceItems(List<StaffServiceItemform> serviceItems) {
		this.serviceItems = serviceItems;
	}

	public String getLanguageFeature() {
		return languageFeature;
	}

	public void setLanguageFeature(String languageFeature) {
		this.languageFeature = languageFeature;
	}

	public String getCharacerFeature() {
		return characerFeature;
	}

	public void setCharacerFeature(String characerFeature) {
		this.characerFeature = characerFeature;
	}

	public String getCookingFeature() {
		return cookingFeature;
	}

	public void setCookingFeature(String cookingFeature) {
		this.cookingFeature = cookingFeature;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
