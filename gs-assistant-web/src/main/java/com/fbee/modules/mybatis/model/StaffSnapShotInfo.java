package com.fbee.modules.mybatis.model;

import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.utils.DictionariesUtil;
import com.fbee.modules.utils.DictionarysCacheUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 家政员快照（核心信息）
 */
public class StaffSnapShotInfo implements Serializable{

    private  Integer id;
    private  String  staffName;
    private  String  headImage;
    private  Integer age;
    private  String  zodiac;
    private  String  education;
    private  String  constellation;
    private  String  nativePlace;
    private  String  sex;
    private  String  mobile;
    private  String  serviceType;
    private  BigDecimal price;
    private  String  unit;
    private  String  workExperience;
    private  String  workStatus;
    private  String  serviceArea;
    private  String  experience;

    public String getExperienceValue(){
        return DictionariesUtil.getExperienceValue(experience);
    }
    public String getZodiacValue(){
        return DictionarysCacheUtils.getZodiacName(zodiac);
    }
    public String getEducationValue(){
        return DictionarysCacheUtils.getEducationName(education);
    }
    public String getConstellationValue(){
        return DictionarysCacheUtils.getConstellationName(constellation);
    }
    public String getNativePlaceValue(){
        return DictionarysCacheUtils.getNativePlaceName(nativePlace);
    }
    public String getSexValue(){
        return DictionarysCacheUtils.getSexName(sex);
    }
    public String getUnitValue(){
        return DictionarysCacheUtils.getServicePriceUnit(unit);
    }
    public String getWorkStatusValue(){return DictionarysCacheUtils.getWorkStatusName(this.workStatus);}
    public String getServiceTypeValue(){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isNotBlank(serviceType)){
            for(String st : serviceType.split(",")){
                sb.append(DictionarysCacheUtils.getServiceTypeName(st)).append("、");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    public String getServiceAreaValue(){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isNotBlank(serviceArea)){
            for(String st : serviceArea.split(",")){
                sb.append(DictionarysCacheUtils.getProviceName(st)).append("、");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }


    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getExperience() {
        return experience;
    }
}
