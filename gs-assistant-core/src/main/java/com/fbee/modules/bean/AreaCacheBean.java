package com.fbee.modules.bean;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: AreaCacheBean 
* @Description: TODO
* @author 贺章鹏
* @date 2017年1月18日 上午11:15:44 
*  
*/
public class AreaCacheBean implements ModelSerializable{
	
	private static final long serialVersionUID = 1L;

	private String areaCode;

    private String areaName;

    private String parentCode;

    private String areaLevel;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}
    
}
