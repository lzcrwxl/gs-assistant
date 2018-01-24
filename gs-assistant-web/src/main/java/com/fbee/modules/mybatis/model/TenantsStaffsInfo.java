package com.fbee.modules.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TenantsStaffsInfo {
    /**
     * 表：tenants_staffs_info
     * 字段：STAFF_ID
     * 注释：员工id
     *
     * @mbggenerated
     */
    private Integer staffId;

    /**
     * 表：tenants_staffs_info
     * 字段：TENANT_ID
     * 注释：租户id
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：tenants_staffs_info
     * 字段：STAFF_NAME
     * 注释：员工姓名
     *
     * @mbggenerated
     */
    private String staffName;

    /**
     * 表：tenants_staffs_info
     * 字段：HEAD_IMAGE
     * 注释：用户头像
     *
     * @mbggenerated
     */
    private String headImage;

    /**
     * 表：tenants_staffs_info
     * 字段：CERT_NO
     * 注释：员工身份证
     *
     * @mbggenerated
     */
    private String certNo;

    /**
     * 表：tenants_staffs_info
     * 字段：CERT_IMAGE
     * 注释：身份证图片
     *
     * @mbggenerated
     */
    private String certImage;

    /**
     * 表：tenants_staffs_info
     * 字段：SEX
     * 注释：性别
     *
     * @mbggenerated
     */
    private String sex;

    /**
     * 表：tenants_staffs_info
     * 字段：NATION
     * 注释：民族
     *
     * @mbggenerated
     */
    private String nation;

    /**
     * 表：tenants_staffs_info
     * 字段：AGE
     * 注释：年龄
     *
     * @mbggenerated
     */
    private Integer age;

    /**
     * 表：tenants_staffs_info
     * 字段：ZODIAC
     * 注释：属性
     *
     * @mbggenerated
     */
    private String zodiac;

    /**
     * 表：tenants_staffs_info
     * 字段：NATIVE_PLACE
     * 注释：籍贯
     *
     * @mbggenerated
     */
    private String nativePlace;

    /**
     * 表：tenants_staffs_info
     * 字段：CONSTELLATION
     * 注释：星座
     *
     * @mbggenerated
     */
    private String constellation;

    /**
     * 表：tenants_staffs_info
     * 字段：HOUSE_ADDRESS
     * 注释：户籍地址
     *
     * @mbggenerated
     */
    private String houseAddress;

    /**
     * 表：tenants_staffs_info
     * 字段：MOBILE
     * 注释：手机号码
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 表：tenants_staffs_info
     * 字段：EDUCARION
     * 注释：学历
     *
     * @mbggenerated
     */
    private String educarion;

    /**
     * 表：tenants_staffs_info
     * 字段：SPECIALTY
     * 注释：专业
     *
     * @mbggenerated
     */
    private String specialty;

    /**
     * 表：tenants_staffs_info
     * 字段：MARITAL_STATUS
     * 注释：婚姻状况
     *
     * @mbggenerated
     */
    private String maritalStatus;

    /**
     * 表：tenants_staffs_info
     * 字段：FERTILITY_SITUATION
     * 注释：生育情况
     *
     * @mbggenerated
     */
    private String fertilitySituation;

    /**
     * 表：tenants_staffs_info
     * 字段：WEIGHT
     * 注释：体重
     *
     * @mbggenerated
     */
    private String weight;

    /**
     * 表：tenants_staffs_info
     * 字段：HEIGHT
     * 注释：身高
     *
     * @mbggenerated
     */
    private BigDecimal height;

    /**
     * 表：tenants_staffs_info
     * 字段：BLOOD_TYPE
     * 注释：血型
     *
     * @mbggenerated
     */
    private String bloodType;

    /**
     * 表：tenants_staffs_info
     * 字段：EXPECTED_SALARY
     * 注释：期望工资
     *
     * @mbggenerated
     */
    private Integer expectedSalary;

    /**
     * 表：tenants_staffs_info
     * 字段：LIVE_ADDRESS
     * 注释：现居住地
     *
     * @mbggenerated
     */
    private String liveAddress;

    /**
     * 表：tenants_staffs_info
     * 字段：CONTACT_PHONE
     * 注释：紧急联系电话
     *
     * @mbggenerated
     */
    private String contactPhone;

    /**
     * 表：tenants_staffs_info
     * 字段：CONTACT_ADDRESS
     * 注释：联系地址
     *
     * @mbggenerated
     */
    private String contactAddress;

    /**
     * 表：tenants_staffs_info
     * 字段：WORK_STATUS
     * 注释：工作状态
     *
     * @mbggenerated
     */
    private String workStatus;

    /**
     * 表：tenants_staffs_info
     * 字段：ON_OFF_SHELF
     * 注释：上下架
     *
     * @mbggenerated
     */
    private String onOffShelf;

    /**
     * 表：tenants_staffs_info
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：tenants_staffs_info
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：tenants_staffs_info
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：tenants_staffs_info
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;

    /**
     * 表：tenants_staffs_info
     * 字段：CV
     * 注释：电子简历
     *
     * @mbggenerated
     */
    private String cv;

    /**
     * 表：tenants_staffs_info
     * 字段：share_status
     * 注释：分享状态:01分享中,02未分享（不显示）
     *
     * @mbggenerated
     */
    private String shareStatus;
    
    private String staffNo;//阿姨編號

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

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage == null ? null : headImage.trim();
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    public String getCertImage() {
        return certImage;
    }

    public void setCertImage(String certImage) {
        this.certImage = certImage == null ? null : certImage.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
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
        this.zodiac = zodiac == null ? null : zodiac.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation == null ? null : constellation.trim();
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress == null ? null : houseAddress.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEducarion() {
        return educarion;
    }

    public void setEducarion(String educarion) {
        this.educarion = educarion == null ? null : educarion.trim();
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty == null ? null : specialty.trim();
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus == null ? null : maritalStatus.trim();
    }

    public String getFertilitySituation() {
        return fertilitySituation;
    }

    public void setFertilitySituation(String fertilitySituation) {
        this.fertilitySituation = fertilitySituation == null ? null : fertilitySituation.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType == null ? null : bloodType.trim();
    }

    public Integer getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(Integer expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress == null ? null : liveAddress.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress == null ? null : contactAddress.trim();
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus == null ? null : workStatus.trim();
    }

    public String getOnOffShelf() {
        return onOffShelf;
    }

    public void setOnOffShelf(String onOffShelf) {
        this.onOffShelf = onOffShelf == null ? null : onOffShelf.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddAccount() {
        return addAccount;
    }

    public void setAddAccount(String addAccount) {
        this.addAccount = addAccount == null ? null : addAccount.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyAccount() {
        return modifyAccount;
    }

    public void setModifyAccount(String modifyAccount) {
        this.modifyAccount = modifyAccount == null ? null : modifyAccount.trim();
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv == null ? null : cv.trim();
    }

    public String getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(String shareStatus) {
        this.shareStatus = shareStatus == null ? null : shareStatus.trim();
    }
}