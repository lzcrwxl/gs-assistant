package com.fbee.modules.jsonData.extend;

import java.math.BigDecimal;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffPolicyJson 
* @Description: 保单信息
* @author 贺章鹏
* @date 2017年1月5日 下午3:50:44 
*  
*/
public class StaffPolicyInfoJson implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private String policyName;//保险名称
	
	private String policyNo;//保险号
	
	private BigDecimal amount;//保险金额
	
	private String policyAgency;//保单机构

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPolicyAgency() {
		return policyAgency;
	}

	public void setPolicyAgency(String policyAgency) {
		this.policyAgency = policyAgency;
	}
	
}
