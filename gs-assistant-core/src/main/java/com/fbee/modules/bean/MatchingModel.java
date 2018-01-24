package com.fbee.modules.bean;

import java.util.Map;
import java.util.Set;

/**
 * @ClassName: MatchingModel
 * @Description: TODO
 * @author 贺章鹏
 * @date 2017年2月7日 下午12:26:13
 * 
 */
public class MatchingModel {

	// 服务工种
	private String isHasTrade;// 是否有工种
	private String seviceNature;// 服务类型
	private Set<String> certs;// 证书情况

	// 人员特征
	private String price;// 价格
	private String experience;// 经验
	private String nativePlace;// 籍贯
	private String zodiac;// 属相
	private String age;// 年龄
	private String sex;// 性别
	private String education;// 学历

	// 个人特点
	private String language;// 语言特点
	private String character;// 性格
	private String cooking;// 烹饪
	// 不做家庭
	private String isHasPet;// 是否有宠物
	private String isHasOlder;// 是否有老人

	// 服务内容
	private Map<Integer, String> serviceContents;

	public void setServiceContents(Map<Integer, String> serviceContents) {
		this.serviceContents = serviceContents;
	}

	public Map<Integer, String> getServiceContents() {
		return serviceContents;
	}

	public String getIsHasTrade() {
		return isHasTrade;
	}

	public void setIsHasTrade(String isHasTrade) {
		this.isHasTrade = isHasTrade;
	}

	public String getSeviceNature() {
		return seviceNature;
	}

	public void setSeviceNature(String seviceNature) {
		this.seviceNature = seviceNature;
	}

	public Set<String> getCerts() {
		return certs;
	}

	public void setCerts(Set<String> certs) {
		this.certs = certs;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getZodiac() {
		return zodiac;
	}

	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getCooking() {
		return cooking;
	}

	public void setCooking(String cooking) {
		this.cooking = cooking;
	}

	public String getIsHasPet() {
		return isHasPet;
	}

	public void setIsHasPet(String isHasPet) {
		this.isHasPet = isHasPet;
	}

	public String getIsHasOlder() {
		return isHasOlder;
	}

	public void setIsHasOlder(String isHasOlder) {
		this.isHasOlder = isHasOlder;
	}

	// public String getServiceContents() {
	// return serviceContents;
	// }
	//
	// public void setServiceContents(String serviceContents) {
	// this.serviceContents = serviceContents;
	// }
}
