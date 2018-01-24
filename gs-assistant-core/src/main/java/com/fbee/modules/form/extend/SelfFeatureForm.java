package com.fbee.modules.form.extend;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: SelfFeatureForm 
* @Description: 特点--用于接收参数form
* @author 贺章鹏
* @date 2016年12月29日 下午5:46:41 
*  
*/
public class SelfFeatureForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;

	private String featureKey;//特点键值
	
	private String featureName;//特点名称
	
	private String featureValue;//特点值
	
	public String getFeatureKey() {
		return featureKey;
	}

	public void setFeatureKey(String featureKey) {
		this.featureKey = featureKey;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	
	public String getFeatureValue() {
		return featureValue;
	}

	public void setFeatureValue(String featureValue) {
		this.featureValue = featureValue;
	}

}
