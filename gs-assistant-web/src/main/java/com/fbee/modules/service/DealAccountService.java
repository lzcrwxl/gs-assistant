package com.fbee.modules.service;

import java.math.BigDecimal;

import com.fbee.modules.jsonData.basic.JsonResult;

/**
 * 账户处理接口
 * @author ZhangJie
 *
 */
public interface DealAccountService {
	/**
	 * 更新账户总表
	 * @param tenantId
	 * @return
	 */
	JsonResult dealAccount(Integer tenantId,String BussType,String payType,String state,BigDecimal money);
	/**
	 * 处理账户轨迹表
	 * @param tenantId
	 * @param bussType
	 * @param payType
	 * @param state
	 * @param money
	 * @return
	 */
	JsonResult dealAccountTrace(Integer tenantId,String bussType,String payType,String state,BigDecimal money, String orderNo, String remarks);
}
