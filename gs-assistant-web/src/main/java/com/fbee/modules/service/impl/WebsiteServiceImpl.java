package com.fbee.modules.service.impl;

import com.fbee.modules.basic.WebUtils;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.TenantsContactBarBean;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.TenantsContactBarForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.extend.ServiceManageIndexInfoJson;
import com.fbee.modules.jsonData.json.WebsiteIndexJsonData;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.entity.*;
import com.fbee.modules.mybatis.model.TenantsAboutUs;
import com.fbee.modules.mybatis.model.TenantsAboutUsExample;
import com.fbee.modules.mybatis.model.TenantsContactBar;
import com.fbee.modules.mybatis.model.TenantsServiceItems;
import com.fbee.modules.service.WebsiteService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.service.basic.ServiceException;
import com.fbee.modules.utils.DictionarysCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WebsiteServiceImpl extends BaseService implements WebsiteService {

	@Autowired
	TenantsBannersMapper tenantsBannersDao;
	@Autowired
	ConstantsMapper constantsDao;

	@Autowired
	TenantsContactBarMapper tenantsContactBarMapper;
	@Autowired
	DictionarysMapper dictionarysMapper;
	@Autowired
	TenantsAboutUsMapper tenantsAboutUsMapper;
	@Autowired
	TenantsUsersMapper TenantsUsersDao;
	@Autowired
	TenantsServiceItemsMapper tenantsServiceItemsMapper;
	
	@Autowired
	TenantsAppsMapper tenantsAppsMapper;
	
	
	@Override
	public JsonResult getWebsiteIndexInfo(Integer tenantId) {
		
		WebsiteIndexJsonData websiteIndexJsonData = new WebsiteIndexJsonData();//网站管理首面-实体类
		ServiceManageIndexInfoJson serviceManageIndexInfoJson =null ;
		List<ServiceManageIndexInfoJson> sIndexInfoJsons = new ArrayList<ServiceManageIndexInfoJson>();
		TenantsBannersEntity tenantsBanners = tenantsBannersDao.getBannerByTenantId(tenantId);//Banner信息
		String websiteName= tenantsAppsMapper.getWebSiteById(tenantId);
		List<TenantsServiceItems> list = new ArrayList<TenantsServiceItems>();
		list=tenantsServiceItemsMapper.getServiceManageIndexInfo(tenantId);
		if (tenantsBanners.getBannerUrl() == null) {
			List<TenantsBannersEntity> bnas=tenantsBannersDao.findBannersByTenantId(0);
			websiteIndexJsonData.setBannnerImage(bnas.get(0).getBannerUrl());
		}
		websiteIndexJsonData.setBannnerImage(tenantsBanners.getBannerUrl());
		if (websiteName !=null) {
			websiteIndexJsonData.setWebsiteName(websiteName);
		}
		if (list !=null && list.size() > 0) {
				for (TenantsServiceItems tItems : list) {
					serviceManageIndexInfoJson=new ServiceManageIndexInfoJson();
					serviceManageIndexInfoJson.setTenantId(tenantId);
					serviceManageIndexInfoJson.setImageUrl(tItems.getImageUrl());
					serviceManageIndexInfoJson.setIsDefault(tItems.getIsDefault());
					serviceManageIndexInfoJson.setIsHot(tItems.getIsHot());
					serviceManageIndexInfoJson.setIsShow(tItems.getIsShow());
					serviceManageIndexInfoJson.setServiceContent(tItems.getServiceContent());
					serviceManageIndexInfoJson.setServiceObject(tItems.getServiceObject());
					serviceManageIndexInfoJson.setSerialNumber(tItems.getSortNo());
					serviceManageIndexInfoJson.setServiceDesc(tItems.getServiceDesc());
					serviceManageIndexInfoJson.setServiceItemName(tItems.getServiceItemName());
					serviceManageIndexInfoJson.setServicePrice(tItems.getServicePrice());
					serviceManageIndexInfoJson.setServicePriceUnit(tItems.getServicePriceUnit());
					serviceManageIndexInfoJson.setServiceItemCode(tItems.getServiceItemCode());
					String priceValue=DictionarysCacheUtils.getServicePriceUnit(tItems.getServicePriceUnit());
					serviceManageIndexInfoJson.setPriceValue(priceValue);
					sIndexInfoJsons.add(serviceManageIndexInfoJson);
				}
				
				websiteIndexJsonData.setsIndexInfoJsons(sIndexInfoJsons);
			}
		return JsonResult.success(websiteIndexJsonData);
	}
	
	@Override
	public TenantsContactBarBean getContactBarByTenantId(Integer tenantId) {
		
		TenantsContactBarBean contactBarBean = new TenantsContactBarBean();
		TenantsContactBar tenantsContactBar = tenantsContactBarMapper.getById(tenantId);
		if (tenantsContactBar == null) {
			return null;
		}
		contactBarBean.setTenantId(tenantId);
		contactBarBean.setContactPhone(tenantsContactBar.getContactPhone());
		contactBarBean.setQqOne(tenantsContactBar.getQqOne());
		contactBarBean.setQqTwo(tenantsContactBar.getQqTwo());
		contactBarBean.setQqThree(tenantsContactBar.getQqThree());
		contactBarBean.setQrCode(tenantsContactBar.getQrCode());
		if(StringUtils.isNotBlank(tenantsContactBar.getQrCode())
				&& !tenantsContactBar.getQrCode().startsWith("http")
				&& !tenantsContactBar.getQrCode().startsWith("data")){
			contactBarBean.setQrCode(Constants.IMAGE_URL+tenantsContactBar.getQrCode());
		}
		contactBarBean.setIsOpenMobile(tenantsContactBar.getIsOpenMobile());
		contactBarBean.setIsOpenQq(tenantsContactBar.getIsOpenQq());
		contactBarBean.setIsOpenQrCode(tenantsContactBar.getIsOpenQrCode());
		contactBarBean.setAddAccount(tenantsContactBar.getAddAccount());
		contactBarBean.setAddTime(tenantsContactBar.getAddTime());
		contactBarBean.setModifyAccount(tenantsContactBar.getModifyAccount());
		contactBarBean.setModifyTime(tenantsContactBar.getModifyTime());
		contactBarBean.setIsOpenQqOne(tenantsContactBar.getIsOpenQqOne());
		contactBarBean.setIsOpenQqTwo(tenantsContactBar.getIsOpenQqTwo());
		contactBarBean.setIsOpenQqThree(tenantsContactBar.getIsOpenQqThree());
		return contactBarBean;
	}


	@Override
	@Transactional
	public JsonResult updateTenantsContactBar(UserBean userBean, TenantsContactBarForm tenantsContactBarForm) {

		if (userBean.getTenantId() == null) {
			throw new ServiceException("");
		}
		TenantsContactBar tenantsContactBarEntity = this.tenantsContactBarMapper.getById(userBean.getTenantId());
		if (tenantsContactBarEntity == null) {
			TenantsContactBarEntity tenantsContactBar = new TenantsContactBarEntity();
			tenantsContactBar.setTenantId(userBean.getTenantId());
			tenantsContactBar.setContactPhone(tenantsContactBarForm.getContactPhone());
			tenantsContactBar.setQqOne(tenantsContactBarForm.getQqOne());
			tenantsContactBar.setQqTwo(tenantsContactBarForm.getQqTwo());
			tenantsContactBar.setQqThree(tenantsContactBarForm.getQqThree());
			tenantsContactBar.setQrCode(tenantsContactBarForm.getQrCode());
			tenantsContactBar.setIsOpenMobile(tenantsContactBarForm.getIsOpenMobile());
			tenantsContactBar.setIsOpenQq(tenantsContactBarForm.getIsOpenQq());
			tenantsContactBar.setIsOpenQrCode(tenantsContactBarForm.getIsOpenQrCode());
			tenantsContactBar.setAddAccount(userBean.getLoginAccount());
			tenantsContactBar.setAddTime(new Date());
			tenantsContactBar.setIsOpenQqOne(tenantsContactBarForm.getIsOpenQqOne());
			tenantsContactBar.setIsOpenQqTwo(tenantsContactBarForm.getIsOpenQqTwo());
			tenantsContactBar.setIsOpenQqThree(tenantsContactBarForm.getIsOpenQqThree());
			tenantsContactBarMapper.insert(tenantsContactBar);
			return JsonResult.success(null);
		}
		TenantsContactBarEntity tenantsContactBar = new TenantsContactBarEntity();
		tenantsContactBar.setTenantId(userBean.getTenantId());
		tenantsContactBar.setContactPhone(tenantsContactBarForm.getContactPhone());
		tenantsContactBar.setQqOne(tenantsContactBarForm.getQqOne());
		tenantsContactBar.setQqTwo(tenantsContactBarForm.getQqTwo());
		tenantsContactBar.setQqThree(tenantsContactBarForm.getQqThree());
		tenantsContactBar.setQrCode(tenantsContactBarForm.getQrCode());
		tenantsContactBar.setIsOpenMobile(tenantsContactBarForm.getIsOpenMobile());
		tenantsContactBar.setIsOpenQq(tenantsContactBarForm.getIsOpenQq());
		tenantsContactBar.setIsOpenQrCode(tenantsContactBarForm.getIsOpenQrCode());
		tenantsContactBar.setModifyAccount(userBean.getLoginAccount());
		tenantsContactBar.setModifyTime(new Date());
		tenantsContactBar.setIsOpenQqOne(tenantsContactBarForm.getIsOpenQqOne());
		tenantsContactBar.setIsOpenQqTwo(tenantsContactBarForm.getIsOpenQqTwo());
		tenantsContactBar.setIsOpenQqThree(tenantsContactBarForm.getIsOpenQqThree());
		tenantsContactBarMapper.update(tenantsContactBar);
		return JsonResult.success(null);
	}


	@Override
	public JsonResult getServiceType() {
		List<Map<String, String>> list = this.dictionarysMapper.getServiceTypeZp();
		return JsonResult.success(list);
	}

	private void checkFile(String suffix) {
		
		//校验文件后缀
		if(StringUtils.isBlank(suffix)){
			throw new ServiceException("未知文件类型，上传失败！");
		}
		//校验文件类型
		if(!verifyImageType(suffix)){
			throw new ServiceException("文件类型不符合要求，上传失败！");
		}
	}

	@Override
	public JsonResult setDefaultCover(UserBean userBean, Integer id) {
		if (id == null) {
			throw new ServiceException("未获取到信息,请联系管理员");
		}
		
		//之前设置为封面的先更新为未设置
		TenantsAboutUsExample tenantsAboutUsExample = new TenantsAboutUsExample();
		tenantsAboutUsExample.createCriteria().andTenantIdEqualTo(userBean.getTenantId()).andIsUsableEqualTo("1");
		List<TenantsAboutUs> tenantsAboutUsList = tenantsAboutUsMapper.selectByExample(tenantsAboutUsExample);
		if(tenantsAboutUsList!=null&&tenantsAboutUsList.size()>0){
			for (TenantsAboutUs tenantsAboutUs : tenantsAboutUsList) {
				tenantsAboutUs.setIsUsable("0");
				tenantsAboutUsMapper.updateByPrimaryKeySelective(tenantsAboutUs);
			}
		}
		
		TenantsAboutUs tenantsAboutUs = this.tenantsAboutUsMapper.selectByPrimaryKey(id);
		if (tenantsAboutUs == null) {
			throw new ServiceException("未获取到信息,请联系管理员");
		}
		
		tenantsAboutUs.setIsUsable("1");
		tenantsAboutUs.setModifyTime(new Date());
		tenantsAboutUs.setModifyAccount(userBean.getLoginAccount());
		tenantsAboutUsMapper.updateByPrimaryKeySelective(tenantsAboutUs);
		return JsonResult.success(null);
	}
	
	@Override
	@Transactional
	public JsonResult deleteAboutUsInfo(Integer id) {
		if (id == null) {
			throw new ServiceException("未获取到信息,请联系管理员");
		}
		TenantsAboutUs tenantsAboutUs = this.tenantsAboutUsMapper.selectByPrimaryKey(id);
		if (tenantsAboutUs == null) {
			throw new ServiceException("未获取到信息,请联系管理员");
		}
		// 删除记录
		this.tenantsAboutUsMapper.deleteByPrimaryKey(id);
		//删除图片
		String serverPath = Global.getConfig("website_base_path");//服务器路径 
		String imgPath = serverPath + File.separator + tenantsAboutUs.getImages();
		File file = new File(imgPath);
		boolean flag = file.delete();
		if (!flag) {
			throw new ServiceException("删除图片信息失败,请联系IT人员");
		}
		return JsonResult.success(null);
	}

	/**
	 * 校验文件类型 过滤合法的文件类型
	 * 
	 * @param suffix
	 * @return
	 */
	private boolean verifyImageType(String suffix) {
		suffix = suffix.substring(1);
		String allowSuffixs = "gif,jpg,jpeg,bmp,png,ico";
		if (allowSuffixs.indexOf(suffix) == -1) {
			return false;
		}
		return true;
	}
	

	private String renameImage(String suffix) {
		String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + suffix;
		return fileName;
	}

	@Override
	@Transactional
	public Map<String,String> uploadBannerImg(MultipartFile file){
		String uploadFileName=file.getOriginalFilename();
		String serverPath = Global.getConfig("website_base_path");
		String basePath = Constants.WEB_SITE_IMGAE_BASE_PATH;
		String bannerPath = Constants.BANNER_IMGAE_BASE_PATH;
		String fileSavePath = serverPath + "/" + basePath + "/" + bannerPath + "/";
		String imgName = "/" + basePath + "/" + bannerPath + "/" ;
		if (!file.isEmpty()) {
			try {				
				// 获取文件后缀
				String suffix = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
				checkFile(suffix);
	
				if (suffix == null || "".equals(suffix)) {
					throw new ServiceException("未知文件类型，上传失败！");
				}
				// 校验文件类型
				if (!verifyImageType(suffix)) {
					throw new ServiceException("文件类型不符合要求，上传失败！");
				}
				// 重命名上传后的文件名
				String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + suffix;
				
				File targetFile = new File(fileSavePath, fileName); 
		        if(!targetFile.exists()){  
		            targetFile.mkdirs();  
		        }  			  
		        //保存  
		        try {  
		            file.transferTo(targetFile);  
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }
		        Map<String,String> map = new HashMap<String,String>();
		        map.put("img", fileSavePath + fileName );
		        map.put("imgUrl", fileSavePath+fileName);
		       /* map.put("imgUrl", imgName + fileName );*/
		        return map;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取服务管理详情信息
	 */
	@Override
	public JsonResult getServiceManageDetails(Integer tenantId) {

		if (tenantId == null) {
			return JsonResult.failure(ResultCode.ERROR);
		}
		List<TenantsServiceItems> list = new ArrayList<TenantsServiceItems>();
		list=tenantsServiceItemsMapper.getServiceManageIndexInfo(tenantId);
		List<ServiceManageIndexInfoJson> sIndexInfoJsons = new ArrayList<ServiceManageIndexInfoJson>();
		ServiceManageIndexInfoJson serviceManageIndexInfoJson = null;
		for (TenantsServiceItems tenantsServiceItems : list) {
			serviceManageIndexInfoJson=new ServiceManageIndexInfoJson();
			serviceManageIndexInfoJson.setServiceItemName(tenantsServiceItems.getServiceItemName());
			serviceManageIndexInfoJson.setIsShow(tenantsServiceItems.getIsShow());
			serviceManageIndexInfoJson.setIsHot(tenantsServiceItems.getIsHot());
			serviceManageIndexInfoJson.setSerialNumber(tenantsServiceItems.getSortNo());
			serviceManageIndexInfoJson.setServicePrice(tenantsServiceItems.getServicePrice());
			serviceManageIndexInfoJson.setServicePriceUnit(tenantsServiceItems.getServicePriceUnit());
			serviceManageIndexInfoJson.setServiceItemCode(tenantsServiceItems.getServiceItemCode());
			serviceManageIndexInfoJson.setIsDefault(tenantsServiceItems.getIsDefault());
			
			String priceValue=DictionarysCacheUtils.getServicePriceUnit(tenantsServiceItems.getServicePriceUnit());
			serviceManageIndexInfoJson.setPriceValue(priceValue);
			serviceManageIndexInfoJson.setServiceDesc(tenantsServiceItems.getServiceDesc());
			serviceManageIndexInfoJson.setImageUrl(tenantsServiceItems.getImageUrl());
			serviceManageIndexInfoJson.setServiceObject(tenantsServiceItems.getServiceObject());
			serviceManageIndexInfoJson.setServiceContent(tenantsServiceItems.getServiceContent());

			TenantsServiceItems ts = tenantsServiceItemsMapper.getServiceManageByTenantCode(0, tenantsServiceItems.getServiceItemCode());
			serviceManageIndexInfoJson.setDefaultImageUrl(ts.getImageUrl());

			sIndexInfoJsons.add(serviceManageIndexInfoJson);
			
		}
		return JsonResult.success(sIndexInfoJsons);
	}

	/***
	 * 二维码上传--保存url
	 */
	@Override
	public JsonResult uploadImg(Integer tenantId, String qRcode) {
		
		if (tenantId == null) {
			return JsonResult.failure(ResultCode.ERROR);
					
		}
		if (qRcode==null||qRcode=="") {
			return JsonResult.failure(ResultCode.PARAMS_ERROR);
			
		}
		TenantsContactBar  tenantsContactBar = tenantsContactBarMapper.getById(tenantId);
		tenantsContactBar.setTenantId(tenantId);
		tenantsContactBar.setModifyAccount(WebUtils.getCurrentUser().getUserId()+"");
		tenantsContactBar.setModifyTime(new Date());
		tenantsContactBar.setQrCode(qRcode);
		int result = tenantsContactBarMapper.updateByPrimaryKeySelective(tenantsContactBar);
		if (result <=0) {
			return JsonResult.failure(ResultCode.ERROR);
		}
		return JsonResult.success(ResultCode.SUCCESS);
	}

	
	/**
	 * banner 保存
	 */
	@Override
	public JsonResult bannerSave(String bannerPath, Integer tenantId, String isDefault) {
		
		if (tenantId == null) {
			return JsonResult.failure(ResultCode.ERROR);
		}

		TenantsBannersEntity tenantsBannersBean = new TenantsBannersEntity();
		tenantsBannersBean.setModifyAccount(WebUtils.getCurrentUser().getUserId()+"");
		tenantsBannersBean.setModifyTime(new Date());
		tenantsBannersBean.setTenantId(tenantId);
		tenantsBannersBean.setBannerUrl(bannerPath);
		tenantsBannersBean.setIsDefault(isDefault);
		int customResult=tenantsBannersDao.updateBannerInfoById(tenantsBannersBean);
		if (customResult <= 0) {
			logger.error("1--banner save is failure result = "+customResult);
			return JsonResult.failure(ResultCode.ERROR);
		}
		return JsonResult.success(ResultCode.SUCCESS);
	}

	/***
	 * 关于我们信息--保存
	 */
	@Override
	public JsonResult saveAboutUsInfo(String content,Integer tenantId, String loginAccount) {
		if (tenantId == null) {
			return JsonResult.failure(ResultCode.ERROR);
		}
		TenantsAboutUsEntity tEntity = new TenantsAboutUsEntity();
		tEntity.setTenantId(tenantId);
		tEntity.setModifyAccount(loginAccount);
		tEntity.setContent(content);
		tEntity.setIsUsable("1");
		tEntity.setModifyTime(new Date());
		tEntity.setIsDefault("Y");
		int result =tenantsAboutUsMapper.updateAboutUsInfoByEntity(tEntity); 
		if (result <= 0) {
			logger.error("save aboutUsInfo failure result="+result);
			return JsonResult.failure(ResultCode.PARAMS_ERROR);
		}
		
		return JsonResult.success(ResultCode.SUCCESS);
	}

	/**
	 * 获取关于我们的信息
	 */
	@Override
	public JsonResult getAboutUsInfo(Integer tenantId) {
		if (tenantId == null) {
			logger.error("tenant is null systerm error");
			return JsonResult.failure(ResultCode.ERROR);
		}
		TenantsAboutUs tenantsAboutUs = tenantsAboutUsMapper.getAboutUsInfo(tenantId);
		if (tenantsAboutUs==null) {
			logger.error("get about us info failure result="+tenantsAboutUs);
			return JsonResult.failure(ResultCode.DATA_ERROR);
		}
		return JsonResult.success(tenantsAboutUs);
	}
	
	/**
	 * 关于我们 -- 单张图片上传
	 */
	@Override
	public JsonResult uploadAboutusImg(Integer tenantId, MultipartFile file) {
		if (tenantId == null) {
			logger.error("tenant is null systerm error");
			return JsonResult.failure(ResultCode.ERROR);
		}
		String uploadFileName=file.getOriginalFilename();
		String serverPath = Global.getConfig("website_base_path");//服务器路径	
		String basePath=Constants.WEB_SITE_IMGAE_BASE_PATH;//父路径
		String contactPath = Constants.ABOUT_US_IMGAE_BASE_PATH;//子路径
		
		String fileSavePath = serverPath+"/"+basePath+"/"+contactPath+"/"; //服务器路径
		
		System.out.println(serverPath+"serverPath===========================");
		TenantsAboutUsEntity tenantsAboutUsEntity = new TenantsAboutUsEntity();
		TenantsUsersEntity tenantsUsersEntity = TenantsUsersDao.getById(tenantId);
		
		if (!file.isEmpty()) {
			try {				
				// 获取文件后缀
				String suffix = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
				checkFile(suffix);
				// 校验文件大小
				long size = file.getSize();

				Log.info("====file===" + size);
			
				String fileName = renameImage(suffix);//重置文件名称
				Log.info("fileName:" + fileName);
				Log.info("filePath:"+fileSavePath);
				
				File targetFile = new File(fileSavePath, fileName); 
		        if(!targetFile.exists()){  
		            targetFile.mkdirs();  
		        }  			  
		        //保存  
		        try {  
		            file.transferTo(targetFile);  
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }
		        tenantsAboutUsEntity.setTenantId(tenantId);
		        tenantsAboutUsEntity.setImages(fileSavePath+fileName);
		        tenantsAboutUsEntity.setModifyTime(new Date());
		        tenantsAboutUsEntity.setModifyAccount(tenantsUsersEntity.getLoginAccount());
		        
		        int updateResult = tenantsAboutUsMapper.updateAboutUsInfoByEntity(tenantsAboutUsEntity);
		        if (updateResult <= 0) {
		        	logger.error(" upload aboutus result is failure");
					return JsonResult.failure(ResultCode.ERROR);
				}
		        return JsonResult.success(fileSavePath+fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 更新服务管理详情信息
	 */
	@Override
	public JsonResult updateServiceManageDetailInfo(Integer tenantId,
			List<ServiceManageIndexInfoJson> serviceManageIndexInfoJsons) {
		
		if (tenantId == null) {
			return JsonResult.failure(ResultCode.ERROR);
		}
		
		if (serviceManageIndexInfoJsons == null || serviceManageIndexInfoJsons.size()<=0) {
			return JsonResult.failure(ResultCode.PARAMS_ERROR);
		}
		
		ServiceManageIndexInfoJson serviceManageIndexInfoJson2 = new ServiceManageIndexInfoJson();
		for (ServiceManageIndexInfoJson data : serviceManageIndexInfoJsons) {
			serviceManageIndexInfoJson2.setTenantId(tenantId);
			serviceManageIndexInfoJson2.setServiceItemName(data.getServiceItemName());
			serviceManageIndexInfoJson2.setServicePrice(data.getServicePrice());
			serviceManageIndexInfoJson2.setServiceDesc(data.getServiceDesc());
			serviceManageIndexInfoJson2.setImageUrl(data.getImageUrl());
			serviceManageIndexInfoJson2.setServiceObject(data.getServiceObject());
			serviceManageIndexInfoJson2.setServiceContent(data.getServiceContent());
			serviceManageIndexInfoJson2.setServicePriceUnit(data.getServicePriceUnit());
			serviceManageIndexInfoJson2.setSerialNumber(data.getSerialNumber());
			serviceManageIndexInfoJson2.setServiceItemCode(data.getServiceItemCode());
			serviceManageIndexInfoJson2.setIsDefault(data.getIsDefault());
			serviceManageIndexInfoJson2.setIsHot(data.getIsHot());
			serviceManageIndexInfoJson2.setIsShow(data.getIsShow());

			if (data.getIsHot()==null||""==data.getIsHot()) {
				serviceManageIndexInfoJson2.setIsHot("0");
			}
			if (data.getIsShow()==null||""==data.getIsShow()) {
				serviceManageIndexInfoJson2.setIsShow("0");
			}
			if(StringUtils.isBlank(data.getIsDefault()) || data.getIsDefault().equals("0")){
				TenantsServiceItems ts = tenantsServiceItemsMapper.getServiceManageByTenantCode(0, data.getServiceItemCode());
				serviceManageIndexInfoJson2.setImageUrl(ts.getImageUrl());
				serviceManageIndexInfoJson2.setIsDefault("0");
			}

			int result=tenantsServiceItemsMapper.updateServiceManageDetailInfo(serviceManageIndexInfoJson2);
			if (result <=0) {
				logger.error("times update serviceitem manage is failure because result "+result);
				return JsonResult.failure(ResultCode.ERROR);
			}
		}
		return JsonResult.success(null);
	}

}
