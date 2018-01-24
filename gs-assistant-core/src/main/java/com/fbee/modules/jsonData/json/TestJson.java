package com.fbee.modules.jsonData.json;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: TestJson 
* @Description: TODO
* @author 贺章鹏
* @date 2016年12月9日 下午5:00:17 
*  
*/
public class TestJson implements ModelSerializable{
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
