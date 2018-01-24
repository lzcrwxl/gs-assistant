package com.fbee.modules.wechat.bean;

import java.io.Serializable;
import java.util.Arrays;

public class WechatUserinfoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	private String nickname;
	private String sex;
	private String province;
	private String city;
	private String country;
	private String headImgUrl;
	private String unionid;
	private String openid;
	private Object[] privilege;

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public Object[] getPrivilege() {
		return privilege;
	}
	public void setPrivilege(Object[] privilege) {
		this.privilege = privilege;
	}
	@Override
	public String toString() {
		return "WechatUserinfoBean [openId=" + openid + ", nickname="
				+ nickname + ", sex=" + sex + ", province=" + province
				+ ", city=" + city + ", country=" + country + ", headImgUrl="
				+ headImgUrl + ", unionId=" + unionid + ", privilege="
				+ Arrays.toString(privilege) + "]";
	}
}
