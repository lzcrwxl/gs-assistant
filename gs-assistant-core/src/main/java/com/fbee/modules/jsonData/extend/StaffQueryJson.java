package com.fbee.modules.jsonData.extend;

import java.math.BigDecimal;

import com.fbee.modules.core.persistence.ModelSerializable;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.utils.DictionariesUtil;
import com.fbee.modules.utils.DictionarysCacheUtils;


/**
 * @ClassName: StaffQueryJson
 * @Description: 用于阿姨查询返回实体--用于返回json
 * @author 贺章鹏
 * @date 2017年1月4日 下午4:15:21
 * 
 */
public class StaffQueryJson implements ModelSerializable, Comparable<StaffQueryJson> {

	private static final long serialVersionUID = 1L;

	private Integer stafffSerItemId;//阿姨工种Id
	
	private Integer staffId;// 阿姨id

	private String staffName;// 阿姨姓名

	private String headImage;// 图像

	private String serviceItems;// 服务工种

	private String mobile;// 手机号码

	private String education;// 学历，如：本科(营养学)

	private String specialty;// 专业

	private Integer age;// 年龄

	private String zodiac;// 属相

	private String nativePlace;// 籍贯

	private String workStatus;// 工作状态，如：服务中

	private String onOffShelf;// 上下架

	// zj增加参数
	private String serviceProvice;// 服务省
	private String serviceCity;// 服务市
	private String serviceCounty;// 服务区
	private String serviceType;// 服务工种(只显示一个)
	private String workExperience; // 工作经验
	private String shareStatus; // 分享状态
	private String matchingDegree; // 匹配度

	//qfx增加参数
	private String serviceNature;//服务类型
	private BigDecimal servicePrice;//服务价格
	private String unit;
	private String unitValue;

	private  String  constellation;

	//@zsq 增加服务区域参数 因修改服务区域为不限和指定区域
	private String serviceArea;
	private String experience; //从业经验
	private String experienceValue;
	
	private String staffNo;

	public String getExperienceValue() {
		return experienceValue;
	}

	public void setExperienceValue(String experienceValue) {
		this.experienceValue = experienceValue;
	}

	public String getConstellationValue() {
		return DictionarysCacheUtils.getConstellationName(this.constellation);
	}
	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public BigDecimal getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getServiceNature() {
		return serviceNature;
	}

	public void setServiceNature(String serviceNature) {
		this.serviceNature = serviceNature;
	}

	public String getServiceProvice() {
		return serviceProvice;
	}

	public void setServiceProvice(String serviceProvice) {
		this.serviceProvice = serviceProvice;
	}

	public String getServiceCity() {
		return serviceCity;
	}

	public void setServiceCity(String serviceCity) {
		this.serviceCity = serviceCity;
	}

	public String getServiceCounty() {
		return serviceCounty;
	}

	public void setServiceCounty(String serviceCounty) {
		this.serviceCounty = serviceCounty;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getServiceItems() {
		return serviceItems;
	}

	public void setServiceItems(String serviceItems) {
		this.serviceItems = serviceItems;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getZodiac() {
		return zodiac;
	}

	public Integer getStafffSerItemId() {
		return stafffSerItemId;
	}

	public void setStafffSerItemId(Integer stafffSerItemId) {
		this.stafffSerItemId = stafffSerItemId;
	}

	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getOnOffShelf() {
		return onOffShelf;
	}

	public void setOnOffShelf(String onOffShelf) {
		this.onOffShelf = onOffShelf;
	}

	public String getMatchingDegree() {
		return matchingDegree;
	}

	public void setMatchingDegree(String matchingDegree) {
		this.matchingDegree = matchingDegree;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	@Override
	public int compareTo(StaffQueryJson o) {

		int thiscode = Integer.parseInt(this.matchingDegree);
		if (thiscode > Integer.parseInt(o.getMatchingDegree())) {
			return -1;
		} else if (thiscode == Integer.parseInt(o.getMatchingDegree())) {
			return 0;
		}
		return 1;

	}

//	public static void main(String[] args) {
//		List<StaffQueryJson> list = new ArrayList<StaffQueryJson>();
//
//		StaffQueryJson staffQueryJson1 = new StaffQueryJson();
//		staffQueryJson1.setMatchingDegree("10");
//		StaffQueryJson staffQueryJson2 = new StaffQueryJson();
//		staffQueryJson2.setMatchingDegree("50");
//		StaffQueryJson staffQueryJson3 = new StaffQueryJson();
//		staffQueryJson3.setMatchingDegree("30");
//
//		list.add(staffQueryJson1);
//		list.add(staffQueryJson2);
//		list.add(staffQueryJson3);
//		for (StaffQueryJson staffQueryJson : list) {
//
//			System.out.println(staffQueryJson.getMatchingDegree());
//		}
//		Collections.sort(list);
//		for (StaffQueryJson staffQueryJson : list) {
//
//			System.out.println("========" + staffQueryJson.getMatchingDegree());
//		}
//	}

}
