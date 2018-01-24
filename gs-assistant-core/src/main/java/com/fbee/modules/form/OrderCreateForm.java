package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class OrderCreateForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	/*********************** 订单信息 *************************/
	private String orderNo;//订单编号
	
	private Integer tenantId;//租户id
	
	private Integer memberId;//客户id
	
	private String orderTime;//创建时间
	
	private String orderSource;//订单来源
	
	private String orderStatus;//订单状态
	
	private String serviceType;//服务类型
	
	private String serviceItemCode;//服务工种
	
	private String amount;//订单价格
	
	/*********************** 客户信息 *************************/

	private String memberName;//客户姓名
	
	private String sex;//客户性别
	
	private String memberMobile;//手机号
	
	private String serviceProvice;//服务区域省
	
	private String serviceCity;//服务区域市
	
	private String serviceCounty;//服务区域区
	
	private String serviceAddress;//服务地址
	
	private Integer familyCount;//家庭人数
	
	private String houseType;//住宅类型
	
	private String houseArea;//住宅面积
	
	private Integer childrenCount;//儿童数
	
	private String childrenAgeRange;//儿童年龄段
	
	private Integer olderCount;//老人数
	
	private String olderAgeRange;//老人年龄段
	
	private String selfCares;//老人能都自理

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expectedBirth;
	
	/*********************** 服务信息 *************************/
	
	private String serviceStart;//服务开始时间
	
	private String serviceEnd;//服务结束时间
	
	private String isBabyBorn;//宝宝是否已出生
	
	private BigDecimal salary;//薪酬
	
	private String salaryType; //薪酬类型
	
	private String petRaising;//饲养宠物
	
	private String[] languageRequirements;//语言要求
	
	private String[] cookingReqirements;//烹饪要求
	
	private String[] personalityRequirements;//性格要求
	
	private String specialNeeds;//特殊要求
	
	/*********************** 阿姨信息 *************************/
	
	private Integer staffId;//阿姨id
	
	/*********************** 备注 *************************/
	
	private String remark;//备注


	private String serviceCharge;//服务价格


	private String wageRequirements;//年龄要求

	private String experienceRequirements;//服务经验要求

	private String salarySkills ; //服务技能

	private String addAccount;//操作员

	public Date getExpectedBirth() {
		return expectedBirth;
	}

	public void setExpectedBirth(Date expectedBirth) {
		this.expectedBirth = expectedBirth;
	}

	public String getAddAccount() {
		return addAccount;
	}

	public void setAddAccount(String addAccount) {
		this.addAccount = addAccount;
	}

	public String getSalarySkills() {
		return salarySkills;
	}

	public void setSalarySkills(String salarySkills) {
		this.salarySkills = salarySkills;
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

	public String getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
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

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public Integer getFamilyCount() {
		return familyCount;
	}

	public void setFamilyCount(Integer familyCount) {
		this.familyCount = familyCount;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public Integer getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(Integer childrenCount) {
		this.childrenCount = childrenCount;
	}

	public String getChildrenAgeRange() {
		return childrenAgeRange;
	}

	public void setChildrenAgeRange(String childrenAgeRange) {
		this.childrenAgeRange = childrenAgeRange;
	}

	public Integer getOlderCount() {
		return olderCount;
	}

	public void setOlderCount(Integer olderCount) {
		this.olderCount = olderCount;
	}

	public String getOlderAgeRange() {
		return olderAgeRange;
	}

	public void setOlderAgeRange(String olderAgeRange) {
		this.olderAgeRange = olderAgeRange;
	}

	public String getSelfCares() {
		return selfCares;
	}

	public void setSelfCares(String selfCares) {
		this.selfCares = selfCares;
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

	public String getSpecialNeeds() {
		return specialNeeds;
	}

	public void setSpecialNeeds(String specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public String[] getLanguageRequirements() {
		return languageRequirements;
	}

	public void setLanguageRequirements(String[] languageRequirements) {
		this.languageRequirements = languageRequirements;
	}

	public String[] getCookingReqirements() {
		return cookingReqirements;
	}

	public void setCookingReqirements(String[] cookingReqirements) {
		this.cookingReqirements = cookingReqirements;
	}

	public String[] getPersonalityRequirements() {
		return personalityRequirements;
	}

	public void setPersonalityRequirements(String[] personalityRequirements) {
		this.personalityRequirements = personalityRequirements;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
