package com.fbee.modules.jsonData.extend;


import com.fbee.modules.core.persistence.ModelSerializable;

/**
* @Description：预约订单列表查询Json
* @author fry
* @Date 2017年2月6日 下午2:05:50
* 
*/

public class ReserveOrdersJson implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private String orderNo;
	private String memberName;//客户姓名
	private String memberMobile;//客户电话
	private String serviceItemCode;//服务工种
	private String serviceProvice;//服务区域省
	private String serviceCity;//服务区域市
	private String serviceCounty;//服务区域区
	private String staffName;//员工姓名
	private String age;//员工年龄
	private String zodiac;//员工属性
	private String nativePlace;//员工籍贯
	private String headImage;//员工头像
	private String orderTime;//下单时间
	
	
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberMobile() {
		return memberMobile;
	}
	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}
	public String getServiceItemCode() {
		return serviceItemCode;
	}
	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
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
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
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
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
	
	
	
}

