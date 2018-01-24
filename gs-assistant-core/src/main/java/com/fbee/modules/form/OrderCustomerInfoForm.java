package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

public class OrderCustomerInfoForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;

	private String orderNo;//订单编号
	
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

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
