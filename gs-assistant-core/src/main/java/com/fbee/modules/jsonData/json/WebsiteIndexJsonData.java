package com.fbee.modules.jsonData.json;

import java.util.List;

import com.fbee.modules.jsonData.extend.ServiceManageIndexInfoJson;

/** 
* @ClassName: WebsiteIndexJsonData 
* @Description: 网站管理--首页管理
* @author 贺章鹏
* @date 2017年1月6日 下午2:40:06 
*  
*/
public class WebsiteIndexJsonData {
	
	
	private String bannnerImage;//banner图片
	
	private String websiteName;//门店名称
	
	private List<ServiceManageIndexInfoJson> sIndexInfoJsons;//服务
	
	public String getBannnerImage() {
		return bannnerImage;
	}

	public void setBannnerImage(String bannnerImage) {
		this.bannnerImage = bannnerImage;
	}

	public List<ServiceManageIndexInfoJson> getsIndexInfoJsons() {
		return sIndexInfoJsons;
	}

	public void setsIndexInfoJsons(List<ServiceManageIndexInfoJson> sIndexInfoJsons) {
		this.sIndexInfoJsons = sIndexInfoJsons;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}
	
	

}
