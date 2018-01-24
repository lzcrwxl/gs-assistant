package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.form.CustomerQueryForm;
import com.fbee.modules.form.CustomerSaveForm;
import com.fbee.modules.form.OrderCustomerInfoForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.CustomerService;
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
* @ClassName: CustomerController 
* @Description: 客户管理控制器
* @author 贺章鹏
* @date 2016年12月28日 下午12:05:14 
*  
*/
@Controller
@RequestMapping(RequestMappingURL.CUSTOMER_BASE_URL)
public class CustomerController {

	@Autowired
	CustomerService customerservice;
	
	/**
	 * 客户管理-客户查询
	 * @param request
	 * @param response
	 * @param pageNumber
	 * @param pageSize
	 * @param customerqueryform
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.CUSTOMER_QUERY_URL, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult queryCustomer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
			CustomerQueryForm customerqueryform){
		try{
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean=(UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean==null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			
			Log.info(customerqueryform.toString());
			
			return customerservice.queryCustomer(userBean.getTenantId(), customerqueryform, pageNumber, pageSize);
		}catch (Exception e){
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
			return JsonResult.failure(ResultCode.ERROR);
		}

	}
	
	/**
	 * 客户管理-客户详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.CUSTOMER_GET_URL, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult getCustomer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("customerid")String customerid){
		
		try{
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean=(UserBean) session.getAttribute(Constants.USER_SESSION);
			
			if(userBean==null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return customerservice.getCustomer(userBean.getTenantId(), customerid);
			
		}catch (Exception e){
				Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
				return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 客户管理-新增客户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.CUSTOMER_SAVE_URL, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult saveCustomer(HttpServletRequest request, HttpServletResponse response,
			CustomerSaveForm customersaveform){
		try{
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean=(UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean==null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return customerservice.saveCustomer(userBean.getTenantId(), customersaveform,userBean.getLoginAccount());
			
		}catch (Exception e){
				Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
				return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 客户管理-修改客户
	 * @param request
	 * @param response
	 * @param customersaveform
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.CUSTOMER_UPDATE_URL, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult updateCustomer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("customerid")String customerid,CustomerSaveForm customersaveform){
		
		try{
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean=(UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean==null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return customerservice.updateCustomer(customerid,customersaveform,userBean.getLoginAccount());
			
		}catch (Exception e){
				Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
				return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 客户管理-修改客户家庭信息
	 * @param request
	 * @param response
	 * @param customersaveform
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.CUSTOMER_HOMEINFO_UPDATE_URL, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult updateCustomerHomeInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("customerid")String customerid,CustomerSaveForm customersaveform){
		
		try{
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean=(UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean==null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return customerservice.updateCustomerHomeInfo(customerid,customersaveform,userBean.getLoginAccount());
			
		}catch (Exception e){
				Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
				return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 客户管理-更新或添加客户
	 * @param request
	 * @param response
	 * @param customersaveform
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.ORDERCUSTOMER_UPDATE_URL, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult saveOrUpdateCustomer(HttpServletRequest request, HttpServletResponse response, OrderCustomerInfoForm form){
		
		try{
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean=(UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean==null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			Integer tenantId = userBean.getTenantId();
			return customerservice.saveOrUpdateCustomer(form, tenantId);
			
		}catch (Exception e){
				Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
				return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
}
