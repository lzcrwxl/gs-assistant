package com.fbee.modules.jsonData.extend;

import java.math.BigDecimal;
import java.util.Date;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffFinanceInfoJson 
* @Description: 财务记录
* @author 贺章鹏
* @date 2017年1月5日 下午4:01:43 
*  
*/
public class StaffFinanceInfoJson implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private String inOutNo;//财务流水编号
	
	private String addTime;//添加时间

	private String type;//收支类目
	
	private BigDecimal amount;//收付金额
	
	private String operator;//操作人
	
	private String remark;//备注


	private String inOutObject;

	private String financeTime;

	public String getFinanceTime() {
		return financeTime;
	}

	public void setFinanceTime(String financeTime) {
		this.financeTime = financeTime;
	}

	public String getInOutObject() {
		return inOutObject;
	}

	public void setInOutObject(String inOutObject) {
		this.inOutObject = inOutObject;
	}

	public String getInOutNo() {
		return inOutNo;
	}

	public void setInOutNo(String inOutNo) {
		this.inOutNo = inOutNo;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
