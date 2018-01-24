package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.form.TenantsFinanceRecordsForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.FundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(RequestMappingURL.FUNDS_BASE_URL)
public class FundesController {
	@Autowired
	FundsService fundsService;

	/**
	 * 根据租户id查询账户总览信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.GET_TENANTS_FUNDS_INFO, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult fundsGetByID(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			int tenanId = userBean.getTenantId();

			return fundsService.getByTenantsId(tenanId);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}

	}

	/**
	 * 根据租户id查询财务总览信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.GET_TENANTS_FORTUNE_INFO, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult fortuneGetByID(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			int tenanId = userBean.getTenantId();

			return fundsService.getFinanceTotalByTenantsId(tenanId);
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}

	}

	/**
	 * 根据租户id查询财务流水信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.GET_TENANTS_FINANCE_LIST_INFO, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult financeGetByID(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
			TenantsFinanceRecordsForm tenantsFinanceRecordsForm) {
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			int tenanId = userBean.getTenantId();
			tenantsFinanceRecordsForm.setTenantsId(tenanId);
			JsonResult jsonResult= fundsService.getFinanceByTenantsId(tenantsFinanceRecordsForm,pageNumber,pageSize);
			return jsonResult;
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}

	}

}

