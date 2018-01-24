package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.core.Log;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.DictionarysCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
* @ClassName: CacheController 
* @Description: 缓存管理
* @author 贺章鹏
* @date 2016年12月29日 上午10:25:08 
*  
*/
@Controller
@RequestMapping(RequestMappingURL.CACHE_BASE_URL)
public class CacheController {
	
	@Autowired
	DictionarysCacheService cacheService;
	
	/**
	 * 初始化系统缓存数据
	 * @param request
	 * @param response
	 * @return
	 */
	@Guest
	@RequestMapping(value = RequestMappingURL.INIT_CACHE_DATA, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult initCacheData(HttpServletRequest request, HttpServletResponse response) {
		try {
			return cacheService.initCacheData();
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 初始化系统缓存数据
	 * @param request
	 * @param response
	 * @return
	 */
	@Guest
	@RequestMapping(value = RequestMappingURL.CLEAR_CACHE_DATA, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult clearCacheData(HttpServletRequest request, HttpServletResponse response) {
		try {
			return cacheService.clearCacheData();
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
}
