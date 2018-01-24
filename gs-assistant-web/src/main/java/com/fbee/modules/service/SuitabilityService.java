package com.fbee.modules.service;

import com.fbee.modules.bean.OrderMatchBean;

/** 
* @ClassName: SuitabilityService 
* @Description: 匹配度接口处理
* @author 贺章鹏
* @date 2017年2月8日 下午2:58:09 
*  
*/
public interface SuitabilityService {

	/**
	 * 匹配参数orderbean
	 * 家政员id staffId
	 * @param order
	 * @param staffId
	 * @return
	 */
	double getMatchRate(OrderMatchBean order,Integer staffId);
	
}
