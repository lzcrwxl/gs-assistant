package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.TenantsContactBarBean;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.form.ServiceItemsForm;
import com.fbee.modules.form.TenantsContactBarForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.extend.ServiceManageIndexInfoJson;
import com.fbee.modules.service.StaffsService;
import com.fbee.modules.service.WebsiteService;
import com.fbee.modules.utils.DictionariesUtil;
import com.fbee.modules.validate.TenantsContactBarFormValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: WebsiteController
 * @Description: 网站管理控制器
 * @author 贺章鹏
 * @date 2016年12月28日 下午12:03:19
 * 
 */
@Controller
@RequestMapping(RequestMappingURL.WEBSITE_BASE_URL)
public class WebsiteController {

	@Autowired
	WebsiteService websiteService;//网站
	
	@Autowired
	StaffsService staffsService;//员工
	
	/***
	 * 
	 * @MethodName:getWebsiteIndexInfo
	 * @Type:WebsiteController
	 * @Description:网站管理-首页
	 * @Return:JsonResult
	 * @Param:@param request
	 * @Param:@param response
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 13, 2017 2:11:30 PM
	 */
	@RequestMapping(value=RequestMappingURL.WEBSITE_GET_INDEX_INFO,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getWebsiteIndexInfo(HttpServletRequest request,HttpServletResponse response) {

		try {
			
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return websiteService.getWebsiteIndexInfo(userBean.getTenantId());
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 
	 * @MethodName:getAboutUsInfo
	 * @Type:WebsiteController
	 * @Description:关于我们图片文字
	 * @Return:JsonResult
	 * @Param:@param request
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 20, 2017 7:13:53 AM
	 */
	@RequestMapping(value=RequestMappingURL.GET_ABOUT_US_INFO,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getAboutUsInfo(HttpServletRequest request){

		try {
			
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean==null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			
			return this.websiteService.getAboutUsInfo(userBean.getTenantId());
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 
	 * @MethodName:saveAbuotUsInfo
	 * @Type:WebsiteController
	 * @Description:保存---关于我们的信息
	 * @Return:JsonResult
	 * @Param:@param request
	 * @Param:@param content 以字符串的形式保存 图片路径和文字
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 18, 2017 2:44:23 PM
	 */
	@RequestMapping(value=RequestMappingURL.SAVE_ABOUT_US_INFO,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult saveAbuotUsInfo(HttpServletRequest request,String content){
		
		try {
			
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			
			JsonResult result = websiteService.saveAboutUsInfo(content,userBean.getTenantId(), userBean.getLoginAccount());
			return JsonResult.success(result);
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 
	 * @MethodName:uploadAboutusImg
	 * @Type:WebsiteController
	 * @Description:关于我们--图片上传（单张图片上传）
	 * @Return:JsonResult
	 * @Param:@param request
	 * @Param:@param files
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 19, 2017 11:41:13 AM
	 */
	@RequestMapping(value=RequestMappingURL.ABOUT_US_IMG_PATH,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult  uploadAboutusImg(HttpServletRequest request,@RequestParam("file") MultipartFile file) {
		
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			
			JsonResult result = websiteService.uploadAboutusImg(userBean.getTenantId(), file);
			return JsonResult.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/**
	 *
	 * @MethodName:getServiceManageIndexInfo
	 * @Type:WebsiteController
	 * @Description:服务管理-详情信息
	 * @Return:JsonResult
	 * @Param:@param request
	 * @Param:@param response
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 12, 2017 4:49:41 PM
	 */
	@RequestMapping(value=RequestMappingURL.SERVICE_MANAGE_DETAILS,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getServiceManageIndexInfo(HttpServletRequest request,HttpServletResponse response){

		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}

			JsonResult result = websiteService.getServiceManageDetails(userBean.getTenantId());
			return JsonResult.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}

	/**
	 *
	 * @MethodName:getServiceManageIndexInfo
	 * @Type:WebsiteController
	 * @Description:服务管理-详情信息
	 * @Return:JsonResult
	 * @Param:@param request
	 * @Param:@param response
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 12, 2017 4:49:41 PM
	 */
	@RequestMapping(value=RequestMappingURL.ROOT_SERVICE_DETAILS,method=RequestMethod.GET)
	@ResponseBody
	public JsonResult getRootServiceInfo(){

		try {
			JsonResult result = websiteService.getServiceManageDetails(0);
			return JsonResult.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 
	 * @MethodName:updateServiceManageDetailInfo
	 * @Type:WebsiteController
	 * @Description:网站管理--服务管理--保存
	 * @Return:JsonResult
	 * @Param:@param request
	 * @Param:@param serviceManageIndexInfoJson
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 20, 2017 2:02:23 PM
	 */
	@RequestMapping(value=RequestMappingURL.UPDATE_SERVICE_MANAGE_DETAILS,method=RequestMethod.POST)
	@ResponseBody
	public JsonResult updateServiceManageDetailInfo(HttpServletRequest request,
			@RequestBody ServiceManageIndexInfoJson[] serviceJsons) {
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
			
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			
			List<ServiceManageIndexInfoJson> data = new ArrayList<ServiceManageIndexInfoJson>();
			ServiceManageIndexInfoJson serviceManageIndexInfoJsons=null ;
			for (ServiceManageIndexInfoJson sJson : serviceJsons) {
				serviceManageIndexInfoJsons= new ServiceManageIndexInfoJson();
				serviceManageIndexInfoJsons.setServiceItemName(sJson.getServiceItemName());
				serviceManageIndexInfoJsons.setServicePrice(sJson.getServicePrice());
				serviceManageIndexInfoJsons.setServiceDesc(sJson.getServiceDesc());
				serviceManageIndexInfoJsons.setImageUrl(sJson.getImageUrl());
				serviceManageIndexInfoJsons.setIsHot(sJson.getIsHot());
				serviceManageIndexInfoJsons.setServiceObject(sJson.getServiceObject());
				serviceManageIndexInfoJsons.setServiceContent(sJson.getServiceContent());
				serviceManageIndexInfoJsons.setIsShow(sJson.getIsShow());
				serviceManageIndexInfoJsons.setServicePriceUnit(sJson.getServicePriceUnit());
				serviceManageIndexInfoJsons.setIsDefault(sJson.getIsDefault());
				serviceManageIndexInfoJsons.setSerialNumber(sJson.getSerialNumber());
				serviceManageIndexInfoJsons.setServiceItemCode(sJson.getServiceItemCode());
				data.add(serviceManageIndexInfoJsons);
			}
			return  this.websiteService.updateServiceManageDetailInfo(userBean.getTenantId(),data);
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 联系方式-根据租户id查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.WEBSITE_GET_CONTACT_INFO, method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JsonResult getContactBarByTenantId(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			
			TenantsContactBarBean tenantsContactBarBean = websiteService.getContactBarByTenantId(userBean.getTenantId());
			if (tenantsContactBarBean != null) {
				return JsonResult.success(tenantsContactBarBean);
			} else {
				return JsonResult.failure(ResultCode.DATA_IS_NULL);
			}
		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}

	}

	/**
	 * 联系方式-更新
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.WEBSITE_UPDATE_CONTACT_INFO, method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public JsonResult updateTenantsContactBar(HttpServletRequest request, HttpServletResponse response,TenantsContactBarForm tenantsContactBarForm) {
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if (userBean == null) {
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
				
			JsonResult jsonResult=TenantsContactBarFormValidate.validContactBarInfo(tenantsContactBarForm);
			
			if (!jsonResult.isSuccess()) {
				return jsonResult;
			}
			Log.info(tenantsContactBarForm.toString());
			
			JsonResult result = websiteService.updateTenantsContactBar(userBean, tenantsContactBarForm);
			return JsonResult.success(result);

		} catch (Exception e) {
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
		/**
		 * 
		 * @MethodName:uploadImg
		 * @Type:WebsiteController
		 * @Description:二维码上传 -- 图片上传到图片服务器。数据库只保存url
		 * @Return:JsonResult
		 * @Param:@param request
		 * @Param:@param response
		 * @Param:@param qRcode
		 * @Param:@return
		 * @Param:@throws IOException
		 * @Thrown:
		 * @Date:Sep 15, 2017 9:45:06 AM
		 */
		@RequestMapping(value = RequestMappingURL.WEBSITE_UPLOAD_IMG, method = { RequestMethod.GET,RequestMethod.POST })
		@ResponseBody
		public JsonResult uploadImg(HttpServletRequest request, HttpServletResponse response,String qRcode)  {
			try {
				
				HttpSession session = SessionUtils.getSession(request);
				UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
				if(userBean==null){
					return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
				}
				return this.websiteService.uploadImg(userBean.getTenantId(),qRcode);
			} catch (Exception e) {
				e.printStackTrace();
				Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
				return JsonResult.failure(ResultCode.ERROR);
			}
		
		}
		
	

	/**
	 * 获取服务工种下拉列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.GET_SERVICE_TYPE, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult getServiceType(HttpServletRequest request){
		try {
			
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean=(UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean==null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			
			return this.websiteService.getServiceType();
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/**
	 * 图片设置为封面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.SET_DEFAULT_COVER,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult setDefaultCover(HttpServletRequest request,Integer id){
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean==null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return this.websiteService.setDefaultCover(userBean,id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	
	/**
	 * 删除关于我们图文信息
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.DELETE_ABOUT_US_INFO,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteAboutUsInfo(HttpServletRequest request,Integer id){
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean==null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			return this.websiteService.deleteAboutUsInfo(id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	
	public static void main(String[] args) {
		String imgUrl = "D:"+File.separator+"aboutUs"+File.separator+"20170112165943610.png";
		System.out.println(imgUrl);
		File file = new File(imgUrl);
		System.out.println(file.delete()); //返回false 删除失败  true 删除成功*/
	}
	
	/**
	 * 保存租户服务工种
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.UPDATESERITEMS_URL,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateByPrimaryKeys(HttpServletRequest request,String[] serviceItems){
		try {
			HttpSession session = SessionUtils.getSession(request);
			UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
			if(userBean==null){
				return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
			}
			ServiceItemsForm serviceItemsForm = new ServiceItemsForm();
			serviceItemsForm.setTenantId(userBean.getTenantId());
			List<Map<String, String>> list = staffsService.getStaffServiceItemCodes(userBean.getTenantId());
			for(Map<String, String> map : list){
				String code = map.get("serviceItemCode");
				serviceItemsForm.setServiceItemCode(code);
				serviceItemsForm.setIsShow("0");
				staffsService.updateByPrimaryKeySelective(serviceItemsForm);
			}
			if(serviceItems != null){
				for(int i = 0; i < serviceItems.length; i++){
					serviceItemsForm.setIsShow("1");
					serviceItemsForm.setServiceItemCode(serviceItems[i]);
					staffsService.updateByPrimaryKeySelective(serviceItemsForm);
				}
				return JsonResult.success(null);
			}
		   return JsonResult.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ResultCode.getMsg(ResultCode.ERROR), e);  
			return JsonResult.failure(ResultCode.ERROR);
		}
	}
	
	/***
	 * 获取年龄范围列表
	 * @return
	 */
	@RequestMapping(value=RequestMappingURL.GET_AGE_RANGE,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getAgerange(){
		return new JsonResult(true, "", DictionariesUtil.setAgerange());
	}
	
	/***
	 * 获取薪资范围 列表
	 */
	@RequestMapping(value=RequestMappingURL.GET_SALARY_RANGE,method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getSalaryRange(){
		return new JsonResult(true, "", DictionariesUtil.setSalaryRange());
	}
	
	/***
	 * 获取服务类型列表
	 * @return
	 */
	@RequestMapping(value = RequestMappingURL.GET_SERVICE_MOLD, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResult getServiceMold(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "serverType", defaultValue= "" ) String serverType
			){
		return new JsonResult(true, "", DictionariesUtil.setServiceMold(serverType));
	}
	
}
