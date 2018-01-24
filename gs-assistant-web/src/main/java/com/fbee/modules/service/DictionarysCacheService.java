package com.fbee.modules.service;

import com.fbee.modules.jsonData.basic.JsonResult;

/** 
* @ClassName: DictionarysCacheService 
* @Description: 字典形参数和常量参数缓存方法
* @author 贺章鹏
* @date 2017年1月17日 下午1:34:59 
*  
*/
public interface DictionarysCacheService {

	/**
	 * 初始化系统缓存对象
	 * @return
	 */
	JsonResult initCacheData();

	/**
	 * 获取下拉框参数值
	 * @param typeCode
	 * @return
	 */
	JsonResult getDictionaryData(String typeCode);

	/**
	 * 获取省市区
	 * @param typeCode
	 * @param pcode
	 * @return
	 */
	JsonResult getAreaData(String typeCode, String pcode);

	/**
	 * 获取个人特点、服务类型、技能特点
	 * @param typeCode
	 * @param itemCode
	 * @param level 
	 * @return
	 */
	JsonResult getSKillsData(String typeCode, String itemCode, String level);

	/**
	 * 清空数据缓存
	 * @return
	 */
	JsonResult clearCacheData();

}
