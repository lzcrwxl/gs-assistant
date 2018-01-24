package com.fbee.modules.service;

import com.fbee.modules.form.CustomerQueryForm;
import com.fbee.modules.form.CustomerSaveForm;
import com.fbee.modules.form.OrderCustomerInfoForm;
import com.fbee.modules.jsonData.basic.JsonResult;

public interface CustomerService {
	
	/**
	 * 客户管理-客户查询
	 * @param tenantId
	 * @param customerqueryform
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	JsonResult queryCustomer(Integer tenantId, CustomerQueryForm customerqueryform, int pageNumber, int pageSize);
	
	/**
	 * 客户管理-客户详情
	 *
	 */
	JsonResult getCustomer(Integer tenantId, String customerid);
	

	/**
	 * 客户管理-新增客户
	 * @param tenantId
	 * @param customersaveform
	 * @return
	 */
	JsonResult saveCustomer(Integer tenantId, CustomerSaveForm customersaveform, String loginAccount);

	/**
	 * 客户管理-修改客户
	 * @param customersaveform
	 * @param loginAccount
	 * @return
	 */
	JsonResult updateCustomer(String customerid, CustomerSaveForm customersaveform, String loginAccount);
	
	/**
	 * 订单管理保存或更新客户信息
	 * @param customersaveform
	 * @param tenantId
	 * @return
	 */
	JsonResult saveOrUpdateCustomer(OrderCustomerInfoForm form, Integer tenantId);
	
	/**
	 * 客户管理-修改客户
	 * @param customersaveform
	 * @param loginAccount
	 * @return
	 */
	JsonResult updateCustomerHomeInfo(String customerid, CustomerSaveForm customersaveform, String loginAccount);
	
	
}
