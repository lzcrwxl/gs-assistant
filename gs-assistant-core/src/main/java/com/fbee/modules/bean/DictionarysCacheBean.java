package com.fbee.modules.bean;

import java.util.List;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: DictionarysCacheBean 
* @Description: TODO
* @author 贺章鹏
* @date 2017年1月17日 下午2:16:55 
*  
*/
public class DictionarysCacheBean implements ModelSerializable,Comparable<DictionarysCacheBean>{

	private static final long serialVersionUID = 1L;
	
	private Integer pid;//父级代码
	
	private String code;//代码
	
	private String name;//中文
	
	private List<DictionarysCacheBean> children;//自己参数例如个人特点的展示上：语言技能
	
	public Integer getPid() {
		return pid;
	}

	public void setPcode(Integer pid) {
		this.pid = pid;
	}

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
	
	public List<DictionarysCacheBean> getChildren() {
		return children;
	}

	public void setChildren(List<DictionarysCacheBean> children) {
		this.children = children;
	}

	@Override
	public int compareTo(DictionarysCacheBean o) {
		int thiscode=Integer.parseInt(this.code);
		if(thiscode>Integer.parseInt(o.getCode())){
			return 1;
		}else if(thiscode==Integer.parseInt(o.getCode())){
			return 0;
		}
	    return -1;
	}
}
