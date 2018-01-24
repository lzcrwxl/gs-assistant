package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** 
* @ClassName: WithdrawalsController 
* @Description: 提现控制器
* @author 赵利壮
* @date 2016年12月28日 下午12:04:07 
*  
*/
@Controller
@RequestMapping(RequestMappingURL.Withdrawals_BASE_URL)
public class WithdrawalsController {

	@Autowired
	TenantService tenantService;
	
	@RequestMapping(value = RequestMappingURL.Withdrawals_URL,method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public JsonResult Withdrawals(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("counterFee")String counterFee,@RequestParam("actualArrival")String actualArrival){
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean=(UserBean) session.getAttribute(Constants.USER_SESSION);

			return JsonResult.success(tenantService.withdrawals(counterFee, actualArrival,userBean.getTenantId(),userBean.getUserName()));

		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
}
