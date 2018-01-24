package com.fbee.modules.jsonData.extend;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @ClassName:ServiceManageIndexInfoJson.java
 * @Description:服务管理-工种信息实体类
 * @Author:ticahmfock
 * @Date:Sep 13, 2017 2:19:25 PM
 */
public class ServiceManageIndexInfoJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private Integer tenantId;//租户ID 
	
	private String serviceItemName;//服务工种
	
	private BigDecimal servicePrice;//服务价格
	
	private String serviceDesc;//服务描述

	private String imageUrl;//图片

	private String defaultImageUrl;//图片
	
	private String isHot;//是否hot
	 
	private String serviceObject;//服务对象
	
	private String serviceContent;//服务内容
	
	private String isShow;//是否勾选
	
	private String servicePriceUnit;//单位
	
	private String isDefault;//是否为系统默认图：0 系统默认图 1 自定义图

	private int  serialNumber;//排序
	
	private String priceValue;//价格单位
	
	private String serviceItemCode;//服务项目代码
	
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getDefaultImageUrl() {
		return defaultImageUrl;
	}

	public void setDefaultImageUrl(String defaultImageUrl) {
		this.defaultImageUrl = defaultImageUrl;
	}

	public BigDecimal getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public String getServicePriceUnit() {
		return servicePriceUnit;
	}

	public void setServicePriceUnit(String servicePriceUnit) {
		this.servicePriceUnit = servicePriceUnit;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getServiceItemName() {
		return serviceItemName;
	}

	public void setServiceItemName(String serviceItemName) {
		this.serviceItemName = serviceItemName;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}

	public String getServiceObject() {
		return serviceObject;
	}

	public void setServiceObject(String serviceObject) {
		this.serviceObject = serviceObject;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(String priceValue) {
		this.priceValue = priceValue;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public String toString() {
		return "ServiceManageIndexInfoJson [tenantId=" + tenantId + ", serviceItemName=" + serviceItemName
				+ ", servicePrice=" + servicePrice + ", serviceDesc=" + serviceDesc + ", imageUrl=" + imageUrl
				+ ", isHot=" + isHot + ", serviceObject=" + serviceObject + ", serviceContent=" + serviceContent
				+ ", isShow=" + isShow + ", servicePriceUnit=" + servicePriceUnit + ", isDefault=" + isDefault
				+ ", serialNumber=" + serialNumber + ", priceValue=" + priceValue + ", serviceItemCode="
				+ serviceItemCode + "]";
	}
	
	
	
}
