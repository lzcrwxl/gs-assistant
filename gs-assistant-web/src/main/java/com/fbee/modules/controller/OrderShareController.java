package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.form.StaffListForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.OrderShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description:淘蜂享管理控制器
 * @author ZhangJie
 */
@Controller
@RequestMapping(RequestMappingURL.ORDER_SHARE_BASE_URL)
public class OrderShareController {
	
	@Autowired
	OrderShareService OrderShareService;
	
	/**
	 * 获取蜂享池信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.GET_ORDER_SHARE_INFO_LIST,method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult getOrderShareInfoList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize){
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return OrderShareService.getOrderShareInfoList(pageNumber, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 蜂享池-查询订单详情<br>
	 * 非订单分享方<br/>
	 * 订单分享方<br/>
	 * @param orderNo 主键
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.GET_ORDER_DETAIL_INFO,method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult getOrderDetailInfo(HttpServletRequest request,String orderNo){
		try {
			
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			
			return OrderShareService.getOrderDetailInfo(userBean,orderNo);
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
		
	}
	
	/**
	 * 点击"选择阿姨"</br>
	 * 校验通过</br>获取阿姨列表信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.GET_STAFFS_INFO,method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult getStaffsInfo(HttpServletRequest request,StaffListForm form, 
			@RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize){
		try {
			
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return OrderShareService.getStaffsInfo(form,userBean,pageNumber,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
		
	}
	
	/**
	 * 选中阿姨-->确认提交
	 * @param request
	 * @param orderNo 分享池中的订单号
	 * @param staffId 提交阿姨id
	 * @param matchingDegree 匹配度
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.SUBMIT_STAFFS_INFO,method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult submitStaffInfo(HttpServletRequest request,String orderNo ,Integer staffId,String matchingDegree){
		
		try {
			
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return OrderShareService.submitStaffInfo(userBean,orderNo ,staffId,matchingDegree);
		} catch (Exception e){
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	//////////////////////////////投递箱//////////////////////////
	/**
	 * 获取投递箱信息列表
	 * @param request
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.GET_DELIVERY_BOX_INFO_LIST ,method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult getDeliveryBoxInfoList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize){
		
		try {
			
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return OrderShareService.getDeliveryBoxInfoList(userBean,pageNumber,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 获取阿姨明细信息
	 * @param request
	 * @param 主键id
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.GET_STAFF_DETAIL_INFO,method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult getStaffDetailInfo(HttpServletRequest request,Integer id){
		
		try {
			
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return OrderShareService.getStaffDetailInfo(userBean,id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 阿姨通过
	 * @param request
	 * @param staffId
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.STAFF_PASS,method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult staffPass(HttpServletRequest request,Integer id){
		
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return OrderShareService.staffPass(userBean,id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
		
	}
	
	/**
	 * 拒绝阿姨
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.STAFF_REJECT,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult rejectStaff(HttpServletRequest request,Integer id){
		try {
			
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return OrderShareService.rejectStaff(userBean,id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 阿姨退回
	 * @param request
	 * @param id 投递箱主键
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.STAFF_RETURN,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult staffReturn(HttpServletRequest request,String reason,Integer id){
		try {
			
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return OrderShareService.staffReturn(userBean,reason,id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 阿姨面试通过
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.STAFF_INTERVIEW_PASS,method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult staffInterviewPass(HttpServletRequest request,Integer id){
		
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean == null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return OrderShareService.staffInterviewPass(userBean,id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
		
	}
}
