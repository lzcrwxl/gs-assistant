package com.fbee.modules.form;

import com.fbee.modules.core.persistence.ModelSerializable;

/**
* @Description：预约订单查询条件Form
* @author fry
* @date 2017年2月6日 下午1:45:25
* 
*/
public class ReserveOrdersForm implements ModelSerializable{

	private static final long serialVersionUID = 1L;
	
	private String orderStatus="01";//订单状态
	
	private String orderNo;//预约订单编号
	
	private String memberMobile;//客户电话
	
	private String memberName;//客户姓名
	
	private String serviceItemCode;//服务工种
	
	private String orderTimeLeft;//下单时间 左
	
	private String orderTimeRight;//下单时间右
	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getOrderTimeLeft() {
		return orderTimeLeft;
	}

	public void setOrderTimeLeft(String orderTimeLeft) {
		this.orderTimeLeft = orderTimeLeft;
	}

	public String getOrderTimeRight() {
		return orderTimeRight;
	}

	public void setOrderTimeRight(String orderTimeRight) {
		this.orderTimeRight = orderTimeRight;
	}
	
	
	
	
}

