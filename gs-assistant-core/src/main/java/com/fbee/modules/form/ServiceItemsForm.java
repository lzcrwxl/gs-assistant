package com.fbee.modules.form;

import java.math.BigDecimal;

import com.fbee.modules.core.persistence.ModelSerializable;

public class ServiceItemsForm implements ModelSerializable{
	
	private static final long serialVersionUID = 1L;
	
    private Integer tenantId;//租户id

    private String serviceItemCode;//服务项目代码
	
    private String serviceItemName;//服务项目名称
    
    private BigDecimal servicePrice;//服务价格

    private String serviceDesc;//服务描述

    private String isHot;//是否hot

    private String serviceObject;//服务对象

    private String serviceContent;//服务内容
    
    private String servicePriceUnit;//服务单位
    
    private String isShow;//是否显示

    private String imgUrl;//图片路径
    
    private String isDefault;//是否使用默认图片
    
    private String sortNo;//序号

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getServiceItemName() {
		return serviceItemName;
	}

	public void setServiceItemName(String serviceItemName) {
		this.serviceItemName = serviceItemName;
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

	public String getServicePriceUnit() {
		return servicePriceUnit;
	}

	public void setServicePriceUnit(String servicePriceUnit) {
		this.servicePriceUnit = servicePriceUnit;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

}
