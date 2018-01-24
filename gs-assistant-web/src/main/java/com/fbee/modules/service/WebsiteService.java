package com.fbee.modules.service;

import com.fbee.modules.bean.TenantsContactBarBean;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.form.TenantsContactBarForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.extend.ServiceManageIndexInfoJson;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/** 
* @ClassName: WebsiteService 
* @Description: 网站管理服务接口
* @author 贺章鹏
* @date 2017年1月6日 下午1:30:27 
*  
*/
public interface WebsiteService {

	/**
	 * 
	 * @MethodName:getWebsiteIndexInfo
	 * @Type:WebsiteService
	 * @Description:网站管理--首页
	 * @Return:JsonResult
	 * @Param:@param tenantId
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 19, 2017 4:40:21 PM
	 */
	JsonResult getWebsiteIndexInfo(Integer tenantId);

	/**
	 * 租户招聘管理<br/>
	 * 获取服务工种
	 * @return
	 */
	JsonResult getServiceType();

	/**
	 * 
	 * @MethodName:saveAboutUsInfo
	 * @Type:WebsiteService
	 * @Description:保存关于我们的信息
	 * @Return:JsonResult
	 * @Param:@param content
	 * @Param:@param imgUrl
	 * @Param:@param integer
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 15, 2017 1:48:26 PM
	 */
	JsonResult saveAboutUsInfo(String content,Integer tenantId, String loginAccount);
	
	/**
	 * 
	 * @MethodName:uploadAboutusImg
	 * @Type:WebsiteService
	 * @Description:关于我们--单张图片上传
	 * @Return:JsonResult
	 * @Param:@param tenantId
	 * @Param:@param files
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 19, 2017 11:46:00 AM
	 */
	JsonResult uploadAboutusImg(Integer tenantId, MultipartFile file);
	
	/**
	 * 关于我们管理<br/>
	 * 删除图文信息组
	 * @param id 
	 * @return
	 */
	JsonResult deleteAboutUsInfo(Integer id);
	/**
	 * 关于我们管理<br/>
	 * 获取关于我们图文信息组
	 * @param tenantId
	 * @return
	 */
	JsonResult getAboutUsInfo(Integer tenantId);

	/**
	 * 联系方式
	 * 通过租户的id获取租户
	 * @param tenantId
	 * @return
	 */
	TenantsContactBarBean getContactBarByTenantId(Integer tenantId);
	
	/**
	 * 联系方式
	 * 更新
	 * @param tenantsContactBarForm
	 * @return
	 */
	JsonResult updateTenantsContactBar(UserBean userBean,TenantsContactBarForm tenantsContactBarForm);

	/**
	 * Banner保存<br/>
	 * 图片上传保存
	 * @param file
	 * @return
	 */
	Map<String,String> uploadBannerImg(MultipartFile file);
	
	/**
	 * 联系方式
	 * 二维码图片上传
	 * @param tenantId
	 * @param qRcode
	 * @return
	 */
	JsonResult uploadImg(Integer tenantId,String qRcode);
	/**
	 * 设置为封面
	 * @param userBean
	 * @param id
	 * @return
	 */
	JsonResult setDefaultCover(UserBean userBean, Integer id);

	/**
	 * 
	 * @MethodName:bannerSave
	 * @Type:WebsiteService
	 * @Description:banner 信息保存
	 * @Return:JsonResult
	 * @Param:@param bannerPath 图片服务器地址
	 * @Param:@param tenantId 租户ID
	 * @Param:@param isDefault 是否默认
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 15, 2017 10:46:03 AM
	 */
	JsonResult bannerSave(String bannerPath, Integer tenantId, String isDefault);

	/**
	 * 
	 * @MethodName:getServiceManageIndexInfo
	 * @Type:WebsiteService
	 * @Description:服务管理-详细信息
	 * @Return:JsonResult
	 * @Param:@param tenantId
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 12, 2017 4:57:02 PM
	 */
	JsonResult getServiceManageDetails(Integer tenantId);

	/**
	 * 
	 * @MethodName:updateServiceManageDetailInfo
	 * @Type:WebsiteService
	 * @Description:网站管理--服务管理--保存
	 * @Return:JsonResult
	 * @Param:@param tenantId
	 * @Param:@param serviceManageIndexInfoJson
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 20, 2017 1:44:28 PM
	 */
	JsonResult updateServiceManageDetailInfo(Integer tenantId, List<ServiceManageIndexInfoJson> serviceManageIndexInfoJsons);


	
	
}
