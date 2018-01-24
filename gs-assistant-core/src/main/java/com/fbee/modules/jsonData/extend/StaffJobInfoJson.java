package com.fbee.modules.jsonData.extend;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.fbee.modules.bean.ServiceKillsCacheBean;
import com.fbee.modules.core.persistence.ModelSerializable;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.utils.DictionariesUtil;
import com.fbee.modules.utils.DictionarysCacheUtils;

/**
 * @author 贺章鹏
 * @ClassName: StaffJobInfoJson
 * @Description: 求职信息
 * @date 2017年1月5日 下午3:52:16
 */
public class StaffJobInfoJson implements ModelSerializable {

    private static final long serialVersionUID = 1L;


    private Integer id;//id

    private String serviceProvice;//省

    private String serviceCity;//市

    private String serviceCounty;//区

    private String workExperience;//工作经历

    private String selfEvaluation;//自我评价

    private String teacherEvaluation;//老师评价

    private String featureValue;//个人特点

    private BigDecimal price;//服务价格
    private String serviceArea;//服务地域
    private String manageWay;//管理方式
    private String petFeeding;//不做家庭：是否喂养宠物
    private String elderlySupport;//不做家庭：是否有老人
    private String unit;
    private String experience;//从业经验 dictionary

    public String getExperienceValue() {
        if (StringUtils.isBlank(experience)) {
            return "";
        }
        return DictionariesUtil.getExperienceValue(experience);
    }

    public String getUnitValue() {
        if (StringUtils.isBlank(unit)) {
            return "";
        }
        return DictionarysCacheUtils.getServicePriceUnit(unit);
    }

    public String getServiceAreaValue() {
        if (StringUtils.isBlank(serviceArea)) {
            return "";
        }
        String[] sb = serviceArea.split(",");
        for (int i = 0; i < sb.length; i++) {
            sb[i] = DictionarysCacheUtils.getProviceName(sb[i]);
        }
        return StringUtils.strAppend(sb);
    }
    public String getManageWayValue() {
        if (StringUtils.isBlank(manageWay)) {
            return "";
        }
        return DictionarysCacheUtils.getManageWay(manageWay);
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    private List<ServiceKillsCacheBean> languageFeature;//语言特点

    private List<ServiceKillsCacheBean> cookingFeature;//烹饪特点

    private List<ServiceKillsCacheBean> characerFeature;//性格特点

    private List<StaffServiceItemJson> serviceItemList;//服务工种

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getManageWay() {
        return manageWay;
    }

    public void setManageWay(String manageWay) {
        this.manageWay = manageWay;
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

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public String getTeacherEvaluation() {
        return teacherEvaluation;
    }

    public void setTeacherEvaluation(String teacherEvaluation) {
        this.teacherEvaluation = teacherEvaluation;
    }

    public List<StaffServiceItemJson> getServiceItemList() {
        return serviceItemList;
    }

    public void setServiceItemList(List<StaffServiceItemJson> serviceItemList) {
        this.serviceItemList = serviceItemList;
    }

    public String getPetFeeding() {
        return petFeeding;
    }

    public void setPetFeeding(String petFeeding) {
        this.petFeeding = petFeeding;
    }

    public String getElderlySupport() {
        return elderlySupport;
    }

    public void setElderlySupport(String elderlySupport) {
        this.elderlySupport = elderlySupport;
    }

    public List<ServiceKillsCacheBean> getLanguageFeature() {
        return languageFeature;
    }

    public void setLanguageFeature(List<ServiceKillsCacheBean> languageFeature) {
        this.languageFeature = languageFeature;
    }

    public List<ServiceKillsCacheBean> getCookingFeature() {
        return cookingFeature;
    }

    public void setCookingFeature(List<ServiceKillsCacheBean> cookingFeature) {
        this.cookingFeature = cookingFeature;
    }

    public List<ServiceKillsCacheBean> getCharacerFeature() {
        return characerFeature;
    }

    public void setCharacerFeature(List<ServiceKillsCacheBean> characerFeature) {
        this.characerFeature = characerFeature;
    }

    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
