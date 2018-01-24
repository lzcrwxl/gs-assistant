package com.fbee.modules.jsonData.extend;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import com.fbee.modules.core.persistence.ModelSerializable;
import com.fbee.modules.utils.DictionarysCacheUtils;

/** 
* @ClassName: StaffBaseInfoJson--用于返回json 
* @Description: TODO
* @author 贺章鹏
* @date 2017年1月5日 下午3:33:11 
*  
*/
public class StaffBaseInfoJson implements ModelSerializable{
	private static final long serialVersionUID = 1L;
	
	private Integer staffId;//阿姨id
	private String staffNo;

	private String certNo;//身份证
	
	private String headImage;//头像
	
	private String staffName;//员工姓名
	
	private Integer age;//年龄
	
	private String nation;//民族
	
	private String sex;//性别
	
	private String zodiac;//属相
	
	private String nativePlace;//籍贯
	
	private String constellation;//星座
	
	private String education;//学历
	
	private String specialty;//专业
	
	private String maritalStatus;//婚姻状况
	
	private String fertilitySituation;//生育状况
	
	private String bloodType;//血型
	
	private String mobile;//手机号码
	
	private String houseAddress;//户籍地址
	
	private String contactPhone;//联系电话
	
	private String liveAddress;//现居地
	
	private String workStatus;//工作状态
	
	private Integer expectedSalary;//期望工资
	
	private String onOffShelf;//上下架
	
	private String weight;//体重
	
	private BigDecimal height;//身高

	private Integer isLocalStaff; //是否自己的家政员
	private String resumeContactName; // 所属家政公司联系人
	private String resumeContactPhone;//所属家政公司电话

	public Integer getIsLocalStaff() {
		return isLocalStaff;
	}

	public void setIsLocalStaff(Integer isLocalStaff) {
		this.isLocalStaff = isLocalStaff;
	}


	public String getResumeContactName() {
		return resumeContactName;
	}

	public void setResumeContactName(String resumeContactName) {
		this.resumeContactName = resumeContactName;
	}

	public String getResumeContactPhone() {
		return resumeContactPhone;
	}

	public void setResumeContactPhone(String resumeContactPhone) {
		this.resumeContactPhone = resumeContactPhone;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getZodiac() {
		return zodiac;
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

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
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

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getFertilitySituation() {
		return fertilitySituation;
	}

	public void setFertilitySituation(String fertilitySituation) {
		this.fertilitySituation = fertilitySituation;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public String getWorkStatus() {
		return workStatus;
	}
	public String getWorkStatusValue() {
		return DictionarysCacheUtils.getWorkStatusName(workStatus);
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public Integer getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(Integer expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public String getOnOffShelf() {
		return onOffShelf;
	}

	public void setOnOffShelf(String onOffShelf) {
		this.onOffShelf = onOffShelf;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}
	
}
