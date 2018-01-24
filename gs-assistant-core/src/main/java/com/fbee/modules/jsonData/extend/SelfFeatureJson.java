package com.fbee.modules.jsonData.extend;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: SelfFeatureJson 
* @Description: TODO
* @author 贺章鹏
* @date 2017年1月5日 下午5:37:08 
*  
*/
public class SelfFeatureJson implements ModelSerializable{
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
