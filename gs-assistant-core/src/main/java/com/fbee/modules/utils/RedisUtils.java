package com.fbee.modules.utils;

/**
 * 
 * @ClassName:RedisUtils.java
 * @Description:Redis key 值工具
 * @Author:ticahmfock
 * @Date:Sep 18, 2017 5:32:06 PM
 */
public class RedisUtils {


	/**
	 * 
	 * @MethodName:getWebsiteIndexKey
	 * @Type:RedisUtils
	 * @Description: website 首页key值
	 * @Return:String
	 * @Param:@param strKey
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 18, 2017 5:36:52 PM
	 */
	public static String getWebsiteIndexKey(Integer strKey){
		String websiteIndexKey = "WEBSITE_INDEX"+strKey;
		return websiteIndexKey;
	}

	
}
