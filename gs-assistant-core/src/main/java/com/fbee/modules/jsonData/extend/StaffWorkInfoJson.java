package com.fbee.modules.jsonData.extend;

import java.math.BigDecimal;

import com.fbee.modules.core.persistence.ModelSerializable;

/** 
* @ClassName: StaffWorkInfoJson 
* @Description: 派工记录
* @author 贺章鹏
* @date 2017年1月5日 下午4:00:03 
*  
*/
public class StaffWorkInfoJson implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private String serviceItem;//服务工种
	
	private String orderStatus;//订单状态
	
	private BigDecimal salary;//薪资
	
	private String customer;//客户姓名
	
	private String beginWorkTime;//上单时间
	
	private String endWorkTime;//下单时间

	public String getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(String serviceItem) {
		this.serviceItem = serviceItem;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getBeginWorkTime() {
		return beginWorkTime;
	}

	public void setBeginWorkTime(String beginWorkTime) {
		this.beginWorkTime = beginWorkTime;
	}

	public String getEndWorkTime() {
		return endWorkTime;
	}

	public void setEndWorkTime(String endWorkTime) {
		this.endWorkTime = endWorkTime;
	}

}
