package com.fbee.modules.validate;

import com.fbee.modules.form.TenantsContactBarForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;

public class TenantsContactBarFormValidate {
	/**
	 * qq小於15位数字验证
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDigital(String str) {
		return str.matches("^[0-9]*$") ? (str.length() <= 15 ? true : false) : false;
	}

	/**
	 * 联系方式小于15位字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validContactPhoneLength(String str) {
		return str.length() <= 15 ? true : false;
	}

	public static JsonResult validContactBarInfo(TenantsContactBarForm tenantsContactBarForm) {
		if (tenantsContactBarForm.getContactPhone() != null && tenantsContactBarForm.getContactPhone() != "") {
			if (!validContactPhoneLength(tenantsContactBarForm.getContactPhone())) {
				return JsonResult.failure(ResultCode.PARAMS_ERROR);
			}
		}

		if (tenantsContactBarForm.getQqOne() != null && tenantsContactBarForm.getQqOne() != "") {

			if (!isDigital(tenantsContactBarForm.getQqOne())) {
				return JsonResult.failure(ResultCode.PARAMS_ERROR);
			}
		}
		if (tenantsContactBarForm.getQqTwo() != null && tenantsContactBarForm.getQqTwo() != "") {

			if (!isDigital(tenantsContactBarForm.getQqTwo())) {
				return JsonResult.failure(ResultCode.PARAMS_ERROR);
			}
		}
		if (tenantsContactBarForm.getQqThree() != null && tenantsContactBarForm.getQqThree() != "") {

			if (!isDigital(tenantsContactBarForm.getQqThree())) {
				return JsonResult.failure(ResultCode.PARAMS_ERROR);
			}
		}
		return JsonResult.success(null);

	}
	

}
