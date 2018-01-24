package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

import java.math.BigDecimal;

public class OrderServiceInfoForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;

	private String orderNo;//订单编号
	
	private String serviceItemCode;//服务工种
	
    private String serviceType;//服务类型
	
    private String serviceStart;//服务开始时间
	
	private String serviceEnd;//服务结束时间
	
	private String isBabyBorn;//宝宝是否已出生
	
	private BigDecimal salary;//薪酬

	private String selfCares; //老人能否自理
	
	private String salaryType;//薪酬类型
	
	private String petRaising;//饲养宠物
	
	private String expectedBirth;//预产期
	
	private String[] skills;//技能特点
	
	private String[] languageRequirements;//语言要求
	
	private String[] cookingRequirements;//烹饪要求
	
	private String[] personalityRequirements;//性格要求
	
	private String wageRequirements;//年龄要求
	
	private String experienceRequirements;//服务经验要求
	
	private String specialNeeds;//特殊要求

	public String getSelfCares() {
		return selfCares;
	}

	public void setSelfCares(String selfCares) {
		this.selfCares = selfCares;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceStart() {
		return serviceStart;
	}

	public void setServiceStart(String serviceStart) {
		this.serviceStart = serviceStart;
	}

	public String getServiceEnd() {
		return serviceEnd;
	}

	public void setServiceEnd(String serviceEnd) {
		this.serviceEnd = serviceEnd;
	}

	public String getIsBabyBorn() {
		return isBabyBorn;
	}

	public void setIsBabyBorn(String isBabyBorn) {
		this.isBabyBorn = isBabyBorn;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}

	public String getPetRaising() {
		return petRaising;
	}

	public void setPetRaising(String petRaising) {
		this.petRaising = petRaising;
	}

	public String[] getLanguageRequirements() {
		return languageRequirements;
	}

	public void setLanguageRequirements(String[] languageRequirements) {
		this.languageRequirements = languageRequirements;
	}
	public String[] getCookingRequirements() {
		return cookingRequirements;
	}

	public void setCookingRequirements(String[] cookingRequirements) {
		this.cookingRequirements = cookingRequirements;
	}

	public String[] getPersonalityRequirements() {
		return personalityRequirements;
	}

	public void setPersonalityRequirements(String[] personalityRequirements) {
		this.personalityRequirements = personalityRequirements;
	}

	public String getSpecialNeeds() {
		return specialNeeds;
	}

	public void setSpecialNeeds(String specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	public String getExpectedBirth() {
		return expectedBirth;
	}

	public void setExpectedBirth(String expectedBirth) {
		this.expectedBirth = expectedBirth;
	}

	public String[] getSkills() {
		return skills;
	}

	public void setSkills(String[] skills) {
		this.skills = skills;
	}

	public String getWageRequirements() {
		return wageRequirements;
	}

	public void setWageRequirements(String wageRequirements) {
		this.wageRequirements = wageRequirements;
	}

	public String getExperienceRequirements() {
		return experienceRequirements;
	}

	public void setExperienceRequirements(String experienceRequirements) {
		this.experienceRequirements = experienceRequirements;
	}
	
	
	
}
