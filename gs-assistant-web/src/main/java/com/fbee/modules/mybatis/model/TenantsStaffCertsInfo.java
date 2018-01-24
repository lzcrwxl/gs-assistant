package com.fbee.modules.mybatis.model;

import java.util.Date;

public class TenantsStaffCertsInfo {
    /**
     * 表：tenants_staff_certs_info
     * 字段：ID
     * 注释：主键ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 表：tenants_staff_certs_info
     * 字段：STAFF_ID
     * 注释：员工id
     *
     * @mbggenerated
     */
    private Integer staffId;

    /**
     * 表：tenants_staff_certs_info
     * 字段：TENANT_ID
     * 注释：租户ID
     *
     * @mbggenerated
     */
    private Integer tenantId;

    /**
     * 表：tenants_staff_certs_info
     * 字段：LEVEL
     * 注释：证书级别 0无级别 1：初级 2 中级 3 高级 4：专项
     *
     * @mbggenerated
     */
    private String level;

    /**
     * 表：tenants_staff_certs_info
     * 字段：CERT_TYPE
     * 注释：01 健康证 02：育婴师证等
     *
     * @mbggenerated
     */
    private String certType;

    /**
     * 表：tenants_staff_certs_info
     * 字段：TYPE
     * 注释：证书类型： 01 技能证书 02 相关证件
     *
     * @mbggenerated
     */
    private String type;

    /**
     * 表：tenants_staff_certs_info
     * 字段：CERT_NO
     * 注释：证件编号
     *
     * @mbggenerated
     */
    private String certNo;

    /**
     * 表：tenants_staff_certs_info
     * 字段：CERTIFICATION_BODY
     * 注释：发证机构
     *
     * @mbggenerated
     */
    private String certificationBody;

    /**
     * 表：tenants_staff_certs_info
     * 字段：CERT_IMAGE
     * 注释：证件图片
     *
     * @mbggenerated
     */
    private String certImage;

    /**
     * 表：tenants_staff_certs_info
     * 字段：CERTIFICATION_DATE
     * 注释：发证日期
     *
     * @mbggenerated
     */
    private Date certificationDate;

    /**
     * 表：tenants_staff_certs_info
     * 字段：PROVINCE
     * 注释：证件归属地-省
     *
     * @mbggenerated
     */
    private String province;

    /**
     * 表：tenants_staff_certs_info
     * 字段：CERT_EXPIRE_DATE
     * 注释：证件有效期
     *
     * @mbggenerated
     */
    private Date certExpireDate;

    /**
     * 表：tenants_staff_certs_info
     * 字段：CERTIFIED_STATUS
     * 注释：认证状态 00:未认证 01:审核通过02:审核不通过
     *
     * @mbggenerated
     */
    private String certifiedStatus;

    /**
     * 表：tenants_staff_certs_info
     * 字段：add_time
     * 注释：添加时间
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * 表：tenants_staff_certs_info
     * 字段：add_account
     * 注释：添加人
     *
     * @mbggenerated
     */
    private String addAccount;

    /**
     * 表：tenants_staff_certs_info
     * 字段：modify_time
     * 注释：修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 表：tenants_staff_certs_info
     * 字段：modify_account
     * 注释：修改人
     *
     * @mbggenerated
     */
    private String modifyAccount;
    
    /**
     * 表：authenticateGrade
     * 字段：authenticate_grade
     * 注释：修改人xiehui
     *
     * @mbggenerated
     */
    private String authenticateGrade;
    
    private String otherCertificationBody;//其他发证机构
    //xiehui
    private String isUsable;//是否可用01是02否
    
   

	public String getIsUsable() {
		return isUsable;
	}

	public void setIsUsable(String isUsable) {
		this.isUsable = isUsable;
	}

	public String getOtherCertificationBody() {
		return otherCertificationBody;
	}

	public void setOtherCertificationBody(String otherCertificationBody) {
		this.otherCertificationBody = otherCertificationBody;
	}

	public String getAuthenticateGrade() {
		return authenticateGrade;
	}

	public void setAuthenticateGrade(String authenticateGrade) {
		this.authenticateGrade = authenticateGrade;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType == null ? null : certType.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    public String getCertificationBody() {
        return certificationBody;
    }

    public void setCertificationBody(String certificationBody) {
        this.certificationBody = certificationBody == null ? null : certificationBody.trim();
    }

    public String getCertImage() {
        return certImage;
    }

    public void setCertImage(String certImage) {
        this.certImage = certImage == null ? null : certImage.trim();
    }

    public Date getCertificationDate() {
        return certificationDate;
    }

    public void setCertificationDate(Date certificationDate) {
        this.certificationDate = certificationDate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public Date getCertExpireDate() {
        return certExpireDate;
    }

    public void setCertExpireDate(Date certExpireDate) {
        this.certExpireDate = certExpireDate;
    }

    public String getCertifiedStatus() {
        return certifiedStatus;
    }

    public void setCertifiedStatus(String certifiedStatus) {
        this.certifiedStatus = certifiedStatus == null ? null : certifiedStatus.trim();
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
}