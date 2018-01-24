package com.fbee.modules.service;

import com.fbee.modules.form.ResetPasswordForm;
import com.fbee.modules.jsonData.basic.JsonResult;

public interface SystemService {
	/**
	 * 根据用户id修改密码
	 * 
	 * @param resetPasswordForm
	 * @param id
	 * @return
	 */
	public JsonResult resetPassword(ResetPasswordForm resetPasswordForm, int id);
	/**
	 * 根据用户id获取绑定手机号
	 * @param id
	 * @return
	 */
	public JsonResult getTelephoneByUserId(int id);
}
