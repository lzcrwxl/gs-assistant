package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

public class CustomerQueryForm implements ModelSerializable {

	/**
	 * 查询客户的条件
	 */
	private static final long serialVersionUID = 1L;

	private String customerName;//客户姓名
	
	private String customerMobile;//联系电话
	
	private String serviceProvice;//服务省
	
	private String serviceCity;//服务市
	
	private String serviceCounty;//服务县

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
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
	 
}
