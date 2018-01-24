package com.fbee.modules.form;

import java.math.BigDecimal;
import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffPolicyForm 
* @Description: 员工（阿姨）保单信息信息新增修改
* @author 贺章鹏
* @date 2017年1月3日 下午3:00:07 
*  
*/
public class StaffPolicyForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private Integer staffId;//阿姨id
	
	private String policyNo;//保单号
	
	private String policyName;//保单名称
	
	private BigDecimal policyAmount;//保单金额
	
	private String policyAgency;//保单机构

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public BigDecimal getPolicyAmount() {
		return policyAmount;
	}

	public void setPolicyAmount(BigDecimal policyAmount) {
		this.policyAmount = policyAmount;
	}

	public String getPolicyAgency() {
		return policyAgency;
	}

	public void setPolicyAgency(String policyAgency) {
		this.policyAgency = policyAgency;
	}
	
	@Override
	public String toString(){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("staff policy info,{staffId:").append(staffId)
			.append(",policyName:").append(policyName).append(",policyAgency:").append(policyAgency)
			.append(",policyNo:").append(policyNo).append(",policyAmount:").append(policyAmount);
		return stringBuilder.toString();
	}
	
}
