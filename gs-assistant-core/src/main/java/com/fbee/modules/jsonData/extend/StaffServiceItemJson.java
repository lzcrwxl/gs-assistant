package com.fbee.modules.jsonData.extend;

import java.math.BigDecimal;
import java.util.List;

import com.fbee.modules.bean.ServiceKillsCacheBean;
import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffServiceItemJson 
* @Description: TODO
* @author 贺章鹏
* @date 2017年1月5日 下午5:35:00 
*  
*/
public class StaffServiceItemJson implements ModelSerializable{
	private static final long serialVersionUID = 1L;
	
	private String serviceItemCode;//服务工种
	
	private List<ServiceKillsCacheBean> skills;//技能特点
	private String serviceNature;//服务类型
	private Integer id;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public List<ServiceKillsCacheBean> getSkills() {
		return skills;
	}

	public void setSkills(List<ServiceKillsCacheBean> skills) {
		this.skills = skills;
	}

	public String getServiceNature() {
		return serviceNature;
	}

	public void setServiceNature(String serviceNature) {
		this.serviceNature = serviceNature;
	}

}
