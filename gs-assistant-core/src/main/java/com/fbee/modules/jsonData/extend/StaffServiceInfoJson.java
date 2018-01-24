package com.fbee.modules.jsonData.extend;

import java.util.Date;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffServiceInfoJson 
* @Description: 服务认证
* @author 贺章鹏
* @date 2017年1月5日 下午4:02:51 
*  
*/
public class StaffServiceInfoJson implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;//证书主键
	
	private String certType;//证书类型
	
	private String certName;//证书名称
	
	private String certifiedStatus;//认证状态 
	
	private String certNo;//证书编号
	
	private String certificationBody;//发证机构
	
	private String certificationDate;//发证日期
	
	private String certImage;//证书图片
	
	private String authenticateGrade;// 鉴定等级@xiehui
	
	private String otherCertificationBody;//xiehui其他发证机构
	
	private String isUsable;//是否可用
	
	
	

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

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getCertifiedStatus() {
		return certifiedStatus;
	}

	public void setCertifiedStatus(String certifiedStatus) {
		this.certifiedStatus = certifiedStatus;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCertificationBody() {
		return certificationBody;
	}

	public void setCertificationBody(String certificationBody) {
		this.certificationBody = certificationBody;
	}

	public String getCertificationDate() {
		return certificationDate;
	}

	public void setCertificationDate(String certificationDate) {
		this.certificationDate = certificationDate;
	}

	public String getCertImage() {
		return certImage;
	}

	public void setCertImage(String certImage) {
		this.certImage = certImage;
	}

}
