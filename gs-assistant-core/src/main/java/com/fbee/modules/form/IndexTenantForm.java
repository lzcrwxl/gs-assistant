package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: IndexTenantForm 
* @Description: 首页修改信息表单页面
* @author 贺章鹏
* @date 2016年12月29日 下午1:49:00 
*  
*/
public class IndexTenantForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;

	public String propertyName;//属性名称
	
	public String propertyValue;//属性值

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	
}
