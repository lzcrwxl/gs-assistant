package com.fbee.modules.validate;

import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.IndexTenantForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;

/** 
* @ClassName: IndexTenantValidate 
* @Description: TODO
* @author 贺章鹏
* @date 2016年12月29日 下午2:38:15 
*  
*/
public class IndexTenantValidate {

	public static JsonResult requireParams(IndexTenantForm indexTenantForm){
		JsonResult jsonResult =JsonResult.success(null);
		
		if(indexTenantForm==null
				|| StringUtils.isBlank(indexTenantForm.getPropertyName())
				|| StringUtils.isBlank(indexTenantForm.getPropertyValue())){
			return JsonResult.failure(ResultCode.PARAMS_ERROR);
		}
		return jsonResult;
	}
}
