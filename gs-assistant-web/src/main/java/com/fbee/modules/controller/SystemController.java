package com.fbee.modules.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.bean.CaptchaCode;
import com.fbee.modules.core.bean.SmsCode;
import com.fbee.modules.core.utils.GenerateCaptcha;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.ResetPasswordForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.json.CaptchaJsonData;
import com.fbee.modules.mybatis.entity.SmsRecordsEntity;
import com.fbee.modules.service.SmsService;
import com.fbee.modules.service.SystemService;

@Controller
@RequestMapping(value = RequestMappingURL.SYSTEM_BASE_URL)
public class SystemController {
	@Autowired
	SystemService systemService;
	@Autowired
	SmsService smsService;

	/**
	 * 根据用户id查询绑定手机号
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.GET_USER_TELEPHONE, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult fundsGetByID(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			int userId = userBean.getUserId();

			return systemService.getTelephoneByUserId(userId);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}

	}
	/**
	 * 获取图片验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.CAPTCHA_URL, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getCaptcha(HttpServletRequest request, HttpServletResponse response) {
		CaptchaJsonData jsonData = new CaptchaJsonData();
		try {
			GenerateCaptcha randomCode = new GenerateCaptcha();
			String captcha = randomCode.getRandResetcodeBase64(request, response, 4, null);

			if (StringUtils.isNotBlank(captcha)) {
				jsonData.setCaptcha(captcha);
				return JsonResult.success(jsonData);
			} else {
				return JsonResult.failure(ResultCode.User.CAPTCHA_FAILURE);
			}
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/**
	 * 系统用户注册时，获取短信验证码
	 * 
	 * @param request
	 * @param response
	 * @param telNum
	 * @return
	 */

	@RequestMapping(value = RequestMappingURL.TENANTS_USER_ADD_CHECK, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getAddCheckMes(HttpServletRequest request, HttpServletResponse response, String telephone) {
		try {
			Map<String, Object> map = (Map<String, Object>) smsService.sendAddCheckMes(telephone);
			SmsCode smsCode0=(SmsCode) map.get("smsCode");
			SmsRecordsEntity entity=(SmsRecordsEntity) map.get("smsRecordsEntity");
			HttpSession session = request.getSession();
			session.removeAttribute(SmsCode.REG_ASS_SMS_CODE_KEY);
			if (smsCode0 != null) {
				session.setAttribute(SmsCode.REG_ASS_SMS_CODE_KEY, smsCode0);
				return JsonResult.success(ResultCode.getMsg(ResultCode.SUCCESS));
			}//ResultCode.User.SMSSEND_FAILURE
			return JsonResult.failure(ResultCode.User.SMSSEND_FAILURE,entity.getFailedReason());
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	/**
	 * 获取短信验证码
	 * 
	 * @param request
	 * @param response
	 * @param telNum
	 * @return
	 */

	@RequestMapping(value = RequestMappingURL.MSG_CAPTCHA_URL, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getMesCaptcha(HttpServletRequest request, HttpServletResponse response, String telNum) {
		try {
			SmsCode smsCode = smsService.sendResetPwdSmsCode(telNum);
			HttpSession session = request.getSession();
			session.removeAttribute(SmsCode.RESET_PWD_SMS_CODE);
			if (smsCode != null) {
				session.setAttribute(SmsCode.RESET_PWD_SMS_CODE, smsCode);
				return JsonResult.success(ResultCode.getMsg(ResultCode.SUCCESS));
			}
			return JsonResult.failure(ResultCode.User.SMSSEND_FAILURE);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/**
	 * 根据用户id修改密码
	 * 
	 * @param request
	 * @param response
	 * @param resetPasswordForm
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.UPDATE_USER_PASSWORD_INFO, method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JsonResult resetPassword(HttpServletRequest request, HttpServletResponse response,
			ResetPasswordForm resetPasswordForm) {
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			int id = userBean.getUserId();
			CaptchaCode captchaObj = (CaptchaCode) session.getAttribute(GenerateCaptcha.RANDOMCODEKEY);
			session.removeAttribute(GenerateCaptcha.RANDOMCODEKEY);
			if (captchaObj == null || captchaObj.isExpired()) { // 验证码过期
				return JsonResult.failure(ResultCode.User.CAPTCHA_TIMEOUT);
			} else if (!captchaObj.getCode().toUpperCase().equals(resetPasswordForm.getCaptcha().toUpperCase())) {// 验证码不正确
				return JsonResult.failure(ResultCode.User.CAPTCHA_ERROR);
			}

			JsonResult jsonResult = systemService.resetPassword(resetPasswordForm, id);

			return jsonResult;
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
}
