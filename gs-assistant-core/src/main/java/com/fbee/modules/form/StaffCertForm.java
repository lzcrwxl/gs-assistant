package com.fbee.modules.form;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: StaffCertForm 
* @Description: 员工证件表单
* @author 贺章鹏
* @date 2017年1月12日 下午12:18:26 
*  
*/
public class StaffCertForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;//证件id
	
	private Integer staffId;//员工id
	
	private String certType;//证书类型(证书名称)
	
	private String certNo;//证书编号
	
	private Date certificationDate;//发证日期
	
	private String certificationBody;//发证机构

	private Date certExpireDate;//到期时间
	
	private String authenticateGrade;//鉴定等级@xiehui
	
	private String otherCertificationBody;//其他发证机构
	
	private String isUsable;//证书是否可用01是02否

	private String photoUrl;

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

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

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public Date getCertificationDate() {
		return certificationDate;
	}

	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}

	public String getCertificationBody() {
		return certificationBody;
	}

	public void setCertificationBody(String certificationBody) {
		this.certificationBody = certificationBody;
	}

	public Date getCertExpireDate() {
		return certExpireDate;
	}

	public void setCertExpireDate(Date certExpireDate) {
		this.certExpireDate = certExpireDate;
	}

	@Override
	public String toString(){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("staff cert info,{staffId:").append(staffId)
			.append(",certType:").append(certType).append(",certNo:").append(certNo);
		return stringBuilder.toString();
	}
}
