package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffBankForm 
* @Description: 员工（阿姨）银行卡信息新增修改
* @author 贺章鹏
* @date 2017年1月3日 下午2:09:24 
*  
*/
public class StaffBankForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private Integer staffId;//阿姨id
	
	private String bankCode;//银行代码
		
	private String cardNo;//卡号
	
	private String accountName;	//账户名称

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Override
	public String toString(){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("staff bank info,{staffId:").append(staffId)
			.append(",bankCode:").append(bankCode).append(",cardNo:").append(cardNo)
			.append(",accountName:" + accountName);
		return stringBuilder.toString();
	}
	
}
