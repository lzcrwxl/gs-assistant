package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffBrushCardForm 
* @Description: TODO
* @author 贺章鹏
* @date 2017年1月11日 下午5:29:14 
*  
*/
public class StaffBrushCardForm implements ModelSerializable{
	
	private static final long serialVersionUID = 1L;

	private Integer tenantId;

	private String certNo;//身份证
	
	private String staffName;//员工姓名
	
	private String nation;//民族
	
	private String nativePlace;//籍贯
	
	private String houseAddress;//户籍地址
	
	private String certImage;//身份证头像编码

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public String getCertImage() {
		return certImage;
	}

	public void setCertImage(String certImage) {
		this.certImage = certImage;
	}
	
}
