package com.fbee.modules.bean;

import java.io.Serializable;

/**
 * @ClassName: ServiceKillsCacheBean
 * @Description: TODO
 * @author 贺章鹏
 * @date 2017年1月20日 下午12:08:10
 * 
 */
public class ServiceKillsCacheBean implements Serializable,Comparable<ServiceKillsCacheBean>{

	private static final long serialVersionUID = 1L;

	private String attrbute;//类型值：服务工种、服务类型、个人特点

	private String itemCode;//下级分类：月嫂等、语言特点

	private String skillsKey;//code值

	private String skillsValue;//中文显示值

	private String parSkillsKey;//可能的父级参数值

	private String sort;//排序值--用于前端排序

	public String getAttrbute() {
		return attrbute;
	}

	public void setAttrbute(String attrbute) {
		this.attrbute = attrbute;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSkillsKey() {
		return skillsKey;
	}

	public void setSkillsKey(String skillsKey) {
		this.skillsKey = skillsKey;
	}

	public String getSkillsValue() {
		return skillsValue;
	}

	public void setSkillsValue(String skillsValue) {
		this.skillsValue = skillsValue;
	}

	public String getParSkillsKey() {
		return parSkillsKey;
	}

	public void setParSkillsKey(String parSkillsKey) {
		this.parSkillsKey = parSkillsKey;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Override
	public int compareTo(ServiceKillsCacheBean o) {
		int thiscode=Integer.parseInt(this.skillsKey);
		if(thiscode>Integer.parseInt(o.getSkillsKey())){
			return 1;
		}else if(thiscode==Integer.parseInt(o.getSkillsKey())){
			return 0;
		}
	    return -1;
	}

}
