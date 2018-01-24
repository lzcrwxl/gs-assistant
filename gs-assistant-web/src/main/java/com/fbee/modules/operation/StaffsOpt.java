package com.fbee.modules.operation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.Status;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.StaffBankForm;
import com.fbee.modules.form.StaffBaseInfoForm;
import com.fbee.modules.form.StaffCertForm;
import com.fbee.modules.form.StaffJobForm;
import com.fbee.modules.form.StaffPolicyForm;
import com.fbee.modules.form.extend.SelfFeatureForm;
import com.fbee.modules.form.extend.StaffServiceItemform;
import com.fbee.modules.jsonData.extend.StaffBankInfoJson;
import com.fbee.modules.jsonData.extend.StaffBaseInfoJson;
import com.fbee.modules.jsonData.extend.StaffFinanceInfoJson;
import com.fbee.modules.jsonData.extend.StaffJobInfoJson;
import com.fbee.modules.jsonData.extend.StaffMediaPictureJson;
import com.fbee.modules.jsonData.extend.StaffMediaVideoJson;
import com.fbee.modules.jsonData.extend.StaffPolicyInfoJson;
import com.fbee.modules.jsonData.extend.StaffServiceInfoJson;
import com.fbee.modules.jsonData.extend.StaffServiceItemJson;
import com.fbee.modules.jsonData.extend.StaffWorkInfoJson;
import com.fbee.modules.mybatis.entity.TenantsStaffBankEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffCertsInfoEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffJobInfoEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffPolicyEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffSerItemsEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffsFeaturesEntity;
import com.fbee.modules.mybatis.entity.TenantsStaffsInfoEntity;
import com.fbee.modules.mybatis.model.OrderCustomersInfo;
import com.fbee.modules.mybatis.model.Orders;
import com.fbee.modules.mybatis.model.TenantsFinanceRecords;
import com.fbee.modules.mybatis.model.TenantsStaffsMedia;
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.utils.IdCardInfoUtils;
import com.google.common.collect.Maps;

/** 
* @ClassName: StaffsOpt 
* @Description: 员工（阿姨）管理操作
* @author 贺章鹏
* @date 2017年1月3日 上午11:00:27 
*  
*/
public class StaffsOpt {
	

	/**
	 * 构建刷身份证接口保存信息
	 * @param obj
	 */
	public static TenantsStaffsInfoEntity buildAddBrushCardInfo(TenantsStaffsInfoEntity obj, StaffBaseInfoForm form) {
		IdCardInfoUtils  idCard=new IdCardInfoUtils(form.getCertNo());
		obj.setStaffName(form.getStaffName());
		obj.setAge(idCard.getAge());
		obj.setCertNo(form.getCertNo());
		obj.setNation(DictionarysCacheUtils.getNationCode(form.getNation()));
		obj.setZodiac(idCard.getZodiac());//---BOND
		obj.setConstellation(idCard.getConstellation());
		obj.setSex(idCard.getGender());
		obj.setNativePlace(DictionarysCacheUtils.getNativePlaceCode(form.getNativePlace()));
		obj.setHouseAddress(form.getHouseAddress());
		obj.setAddTime(new Date());
		return obj;
	}
	
	/**
	 * 构建新增阿姨基础信息实体
	 * @param obj
	 * @return
	 */
	public static TenantsStaffsInfoEntity buildAddBaseInfo(TenantsStaffsInfoEntity obj,StaffBaseInfoForm form){
		obj.setStaffId(form.getStaffId());
		obj.setEducarion(form.getEducation());//学历
		obj.setSpecialty(form.getSpecialty());//专业
		obj.setBloodType(form.getBloodType());//血型
		obj.setMaritalStatus(form.getMaritalStatus());//婚姻
		obj.setFertilitySituation(form.getFertilitySituation());//生育情况
		obj.setMobile(form.getMobile());//电话号码
		obj.setContactPhone(form.getContactPhone());//紧急联系人
		obj.setLiveAddress(form.getLiveAddress());//住址
		obj.setWorkStatus(form.getWorkStatus());//工作状态
		obj.setHeight(form.getHeight());//身高
		obj.setWeight(form.getWeight() == null ? null : form.getWeight().toString());//体重
		obj.setOnOffShelf(form.getOnOffShelf());//上下架
		obj.setStaffNo("S"+StringUtils.fillZero(8, form.getStaffId()));//阿姨编号
		return obj;
	}

	/**
	 * 构建阿姨银行卡新增修改实体
	 * @param obj
	 * @param staffBankForm
	 */
	public static TenantsStaffBankEntity buildBankInfo(TenantsStaffBankEntity obj, StaffBankForm staffBankForm) {
		obj.setBankCode(staffBankForm.getBankCode());
		obj.setStaffId(staffBankForm.getStaffId());
		obj.setCardNo(staffBankForm.getCardNo());
		obj.setAccountName(staffBankForm.getAccountName());
		return obj;
	}

	/**
	 *  构建阿姨保单新增修改实体
	 * @param obj
	 * @param staffPolicyForm
	 */
	public static TenantsStaffPolicyEntity buildPolicyInfo(TenantsStaffPolicyEntity obj, StaffPolicyForm staffPolicyForm) {
		obj.setPolicyAgency(staffPolicyForm.getPolicyAgency());
		obj.setPolicyAmount(staffPolicyForm.getPolicyAmount());
		obj.setPolicyName(staffPolicyForm.getPolicyName());
		obj.setPolicyNo(staffPolicyForm.getPolicyNo());
		obj.setStaffId(staffPolicyForm.getStaffId());
		return obj;
	}

	/**
	 * 构建阿姨求职基本新增修改实体
	 * @param staffJob
	 * @param staffJobForm
	 */
	public static TenantsStaffJobInfoEntity buildStaffJobInfo(TenantsStaffJobInfoEntity staffJob, StaffJobForm staffJobForm) {
		staffJob.setManageWay(staffJobForm.getManageWay());
		staffJob.setStaffId(staffJobForm.getStaffId());
		staffJob.setSelfEvaluation(staffJobForm.getSelfEvaluation());
		staffJob.setTeacherEvaluation(staffJobForm.getTeacherEvaluation());
		staffJob.setWorkExperience(staffJobForm.getWorkExperience());
		if(!"".equals(staffJobForm.getLanguageFeature())){
			staffJob.setLanguageFeature(staffJobForm.getLanguageFeature());
		}
		if(!"".equals(staffJobForm.getCharacerFeature())){
			staffJob.setCharacerFeature(staffJobForm.getCharacerFeature());
		}
		if(!"".equals(staffJobForm.getCookingFeature())){
			staffJob.setCookingFeature(staffJobForm.getCookingFeature());
		}
		staffJob.setOther(staffJobForm.getOther());
		staffJob.setPrice(staffJobForm.getPrice());
		staffJob.setPetFeeding(staffJobForm.getPetFeeding());
		staffJob.setElderlySupport(staffJobForm.getElderlySupport());
		staffJob.setServiceArea(staffJobForm.getServiceArea());
		staffJob.setUnit(staffJobForm.getUnit());
		staffJob.setExperience(staffJobForm.getExperience());
		return staffJob;
	}

	/**
	 * 构建阿姨求职-个人特点新增修改实体
	 * @param featuresObj
	 * @param selfFeature
	 */
	public static TenantsStaffsFeaturesEntity buildStaffFeaturesInfo(TenantsStaffsFeaturesEntity featuresObj, SelfFeatureForm selfFeature) {
		featuresObj.setFeatureCode(selfFeature.getFeatureKey());
		featuresObj.setFeatureValue(selfFeature.getFeatureValue());
		return featuresObj;
	}

	/**
	 * 构建阿姨求职-服务工种特点新增修改实体
	 * @param staffServiceItemBean
	 */
	public static TenantsStaffSerItemsEntity buildStaffServiceItemInfo(TenantsStaffSerItemsEntity staffSerItemsObj,
			StaffServiceItemform staffServiceItemBean) {
		staffSerItemsObj.setServiceItemCode(staffServiceItemBean.getServiceItemCode());
		if(staffServiceItemBean.getSkills()!=null){
			staffSerItemsObj.setSkills(staffServiceItemBean.getSkills());
		}else{
			staffSerItemsObj.setSkills("");
		}
		staffSerItemsObj.setServiceNature(staffServiceItemBean.getServiceNature());//服务类型

		return staffSerItemsObj;
	}

	/**
	 * 构建阿姨查询参数
	 * @param tenantId 
	 * @return
	 */
	public static Map<Object, Object> buildQueryMap(Integer tenantId) {
		Map<Object, Object> params=Maps.newHashMap();
		params.put("tenantId", tenantId);
		return params;
	}
	
	/**
	 * 构建返回阿姨详情jsonData中的阿姨基本信息json
	 * @param baseInfoJson
	 * @param staffsInfo
	 * @return
	 */
	public static StaffBaseInfoJson buildStaffDetaillBaseInfo(StaffBaseInfoJson baseInfoJson,
			TenantsStaffsInfoEntity staffsInfo) {
		baseInfoJson.setStaffId(staffsInfo.getStaffId());
		baseInfoJson.setStaffNo(staffsInfo.getStaffNo());
		baseInfoJson.setAge(staffsInfo.getAge());
		baseInfoJson.setCertNo(staffsInfo.getCertNo());
		baseInfoJson.setStaffName(staffsInfo.getStaffName());
		baseInfoJson.setNation(DictionarysCacheUtils.getNationName(staffsInfo.getNation()));
		baseInfoJson.setZodiac(DictionarysCacheUtils.getZodiacName(staffsInfo.getZodiac()));
		baseInfoJson.setSex(DictionarysCacheUtils.getMemberSex(staffsInfo.getSex()));
		baseInfoJson.setNativePlace(DictionarysCacheUtils.getNativePlaceName(staffsInfo.getNativePlace()));
		baseInfoJson.setHouseAddress(staffsInfo.getHouseAddress());
		if(staffsInfo.getHeadImage().startsWith("http") || staffsInfo.getHeadImage().startsWith("data")){
			baseInfoJson.setHeadImage(staffsInfo.getHeadImage());
		}else{
			baseInfoJson.setHeadImage(Constants.IMAGE_URL + staffsInfo.getHeadImage());
		}
		baseInfoJson.setWorkStatus(staffsInfo.getWorkStatus());
		if (StringUtils.isNoneBlank(staffsInfo.getConstellation())) {
			baseInfoJson.setConstellation(DictionarysCacheUtils.getConstellationName(staffsInfo.getConstellation()));
		}else {
			baseInfoJson.setConstellation("");
		}
		if (StringUtils.isNotBlank(staffsInfo.getEducarion())) {
			baseInfoJson.setEducation(staffsInfo.getEducarion()+Constants.COMMA+DictionarysCacheUtils.getEducationName(staffsInfo.getEducarion()));
		}else {
			baseInfoJson.setEducation("");
		}
		baseInfoJson.setSpecialty(staffsInfo.getSpecialty());
		if (StringUtils.isNotBlank(staffsInfo.getMaritalStatus())) {
			baseInfoJson.setMaritalStatus(staffsInfo.getMaritalStatus()+Constants.COMMA+DictionarysCacheUtils.getMaritalStatus(staffsInfo.getMaritalStatus()));
		}else {
			baseInfoJson.setMaritalStatus("");
		}
		if (StringUtils.isNotBlank(staffsInfo.getFertilitySituation())) {
			baseInfoJson.setFertilitySituation(staffsInfo.getFertilitySituation()+Constants.COMMA+DictionarysCacheUtils.getFertilityStatus(staffsInfo.getFertilitySituation()));
		}else {
			baseInfoJson.setFertilitySituation("");
		}
		baseInfoJson.setBloodType(staffsInfo.getBloodType()+Constants.COMMA+DictionarysCacheUtils.getBloodType(staffsInfo.getBloodType()));
		baseInfoJson.setMobile(staffsInfo.getMobile());
		baseInfoJson.setContactPhone(staffsInfo.getContactPhone());
		baseInfoJson.setLiveAddress(staffsInfo.getLiveAddress());
		baseInfoJson.setExpectedSalary(staffsInfo.getExpectedSalary());
		baseInfoJson.setOnOffShelf(staffsInfo.getOnOffShelf());
		baseInfoJson.setHeight(staffsInfo.getHeight());
		baseInfoJson.setWeight(staffsInfo.getWeight());

		return baseInfoJson;
	}
	
	/**
	 * 构建返回阿姨详情jsonData中的阿姨银行信息json
	 * @param bankJson
	 * @param bankInfo
	 * @return
	 */
	public static StaffBankInfoJson buildStaffDetaillBankInfo(StaffBankInfoJson bankJson,
			TenantsStaffBankEntity bankInfo) {
		bankJson.setBankCode(bankInfo.getBankCode());
		bankJson.setBankName(bankInfo.getBankName());
		//bankJson.setBankName(bankInfo.getBankCode());
		bankJson.setCardNo(bankInfo.getCardNo());
		bankJson.setAccountName(bankInfo.getAccountName());
		return bankJson;
	}

	/**
	 * 构建返回阿姨详情jsonData中的阿姨保单信息json
	 * @param policyListJson
	 * @param policyList
	 */
	public static List<StaffPolicyInfoJson> buildStaffDetaillPolicyList(List<StaffPolicyInfoJson> policyListJson,
			List<TenantsStaffPolicyEntity> policyList) {
		StaffPolicyInfoJson staffPolicyInfoJson=null;
		for(TenantsStaffPolicyEntity policyEntity:policyList){
			staffPolicyInfoJson=new StaffPolicyInfoJson();
			staffPolicyInfoJson.setPolicyNo(policyEntity.getPolicyNo());
			staffPolicyInfoJson.setPolicyName(policyEntity.getPolicyName());
			staffPolicyInfoJson.setAmount(policyEntity.getPolicyAmount());
			staffPolicyInfoJson.setPolicyAgency(policyEntity.getPolicyAgency());
			policyListJson.add(staffPolicyInfoJson);
		}
		return policyListJson;
	}
	
	/**
	 * 构建返回阿姨详情jsonData中的阿姨媒体video信息json
	 * @param videoMedia
	 * @param media
	 * @return
	 */
	public static StaffMediaVideoJson buildStaffDetaillVideo(StaffMediaVideoJson videoMedia,
			TenantsStaffsMedia media) {
		videoMedia.setMediaId(media.getId());
		if(StringUtils.isNotBlank(media.getPath()) && !media.getPath().startsWith("http") && !media.getPath().startsWith("data")){
			videoMedia.setPath(Constants.IMAGE_URL+media.getPath());
		}else{
			videoMedia.setPath(media.getPath());
		}
		videoMedia.setStaffId(media.getStaffId());
		return videoMedia;
	}

	/**
	 * 构建返回阿姨详情jsonData中的阿姨媒体图片信息json
	 * @param imageMedia
	 * @param media
	 */
	public static List<StaffMediaPictureJson> buildStaffDetaillPicture(List<StaffMediaPictureJson> imageMedia, TenantsStaffsMedia media) {
		StaffMediaPictureJson picture=new StaffMediaPictureJson();
		picture.setIdDefault(media.getIsDefault());
		picture.setMediaId(media.getId());
		if(StringUtils.isNotBlank(media.getPath()) && !media.getPath().startsWith("http") && !media.getPath().startsWith("data")){
			picture.setPath(Constants.IMAGE_URL+media.getPath());
		}else{
			picture.setPath(media.getPath());
		}
		picture.setStaffId(media.getStaffId());
		imageMedia.add(picture);
		return imageMedia;
	}

	/**
	 * 构建返回阿姨详情jsonData中的阿姨证书信息json
	 * @param serviceList
	 * @param cert
	 */
	public static List<StaffServiceInfoJson> buildStaffDetaillCerts(List<StaffServiceInfoJson> serviceList,
			TenantsStaffCertsInfoEntity cert) {
		StaffServiceInfoJson serviceInfo=new StaffServiceInfoJson();
		serviceInfo.setId(cert.getId());
		if(StringUtils.isNotBlank(cert.getCertifiedStatus())){
			serviceInfo.setCertifiedStatus(DictionarysCacheUtils.getCertStatusName(cert.getCertifiedStatus()));
		}
		if(StringUtils.isNotBlank(cert.getCertType())){
			serviceInfo.setCertName(cert.getCertType()+Constants.COMMA+DictionarysCacheUtils.getCertTypeName(cert.getCertType()));
		}
		if(StringUtils.isNotBlank(cert.getType())){
			serviceInfo.setCertType(DictionarysCacheUtils.getTypeName(cert.getType()));
		}
		//是否可用
		if(StringUtils.isNotBlank(cert.getIsUsable())){
			serviceInfo.setIsUsable(cert.getIsUsable());
		}
		if(StringUtils.isNotBlank(cert.getCertImage())){
			String imageUrl = cert.getCertImage();
			if(StringUtils.isNotBlank(imageUrl)
					&& !imageUrl.startsWith("http")
					&& !imageUrl.startsWith("data")){
				serviceInfo.setCertImage(Constants.IMAGE_URL + cert.getCertImage());
			}else{
				serviceInfo.setCertImage(cert.getCertImage());
			}
		}
		//@xiehui鉴定等级
		if(StringUtils.isNotBlank(cert.getAuthenticateGrade())){
			serviceInfo.setAuthenticateGrade(cert.getAuthenticateGrade()+","+DictionarysCacheUtils.getAuthenticatGrage(cert.getAuthenticateGrade()));
		}
		serviceInfo.setCertNo(cert.getCertNo());

		serviceInfo.setCertificationDate(DateUtils.formatDateTime(cert.getCertificationDate()));
		//serviceInfo.setCertificationDate(cert.getCertificationDate());
		if("03".equals(cert.getCertificationBody())){
			serviceInfo.setCertificationBody(cert.getCertificationBody()+","+DictionarysCacheUtils.getCertAuthority(cert.getCertificationBody()));
			serviceInfo.setOtherCertificationBody(cert.getOtherCertificationBody());
		}else{
			//@xiehui发证机构
			serviceInfo.setCertificationBody(cert.getCertificationBody()+","+DictionarysCacheUtils.getCertAuthority(cert.getCertificationBody()));
			serviceInfo.setOtherCertificationBody("");
		}
		serviceList.add(serviceInfo);
		return serviceList;
	}

	/**
	 * 构建返回阿姨详情jsonData中的阿姨财务信息json
	 * @param financeRecord
	 */
	public static List<StaffFinanceInfoJson> buildStaffDetaillFinance(List<StaffFinanceInfoJson> financeList,
			TenantsFinanceRecords financeRecord) {
		StaffFinanceInfoJson staffFinance=new StaffFinanceInfoJson();
		staffFinance.setInOutNo(financeRecord.getInOutNo());
		staffFinance.setOperator(financeRecord.getAddAccount());
		if(StringUtils.isNotBlank(financeRecord.getType())){
			if (financeRecord.getType().equals("报名费")||financeRecord.getType().equals("住宿费")||financeRecord.getType().equals("佣金费")) {
				staffFinance.setAmount(financeRecord.getInOutAmount().negate());
			}else {
				staffFinance.setAmount(financeRecord.getInOutAmount());
			}
		}
		staffFinance.setRemark(financeRecord.getRemarks());
		staffFinance.setType(financeRecord.getType());
		staffFinance.setAddTime(DateUtils.formatDateTime(financeRecord.getAddTime()));
		staffFinance.setInOutObject(financeRecord.getInOutObject());

		staffFinance.setFinanceTime(DateUtils.formatDateTime(financeRecord.getFinanceTime()));
		financeList.add(staffFinance);
		return financeList;
	}

	/**
	 * 构建返回阿姨详情jsonData中的阿姨派工信息json
	 * @param workList
	 * @param orderEntity
	 * @param orderCustomers
	 */
	public static List<StaffWorkInfoJson> buildStaffDetaillOrders(List<StaffWorkInfoJson> workList, Orders orderEntity,
			OrderCustomersInfo orderCustomers) {
		StaffWorkInfoJson staffWorkInfoJson=new StaffWorkInfoJson();
		staffWorkInfoJson.setBeginWorkTime(DateUtils.formatDateTime(orderCustomers.getServiceStart()));
		staffWorkInfoJson.setEndWorkTime(DateUtils.formatDateTime(orderCustomers.getServiceEnd()));
		staffWorkInfoJson.setOrderStatus(orderEntity.getOrderStatus());
		staffWorkInfoJson.setServiceItem(DictionarysCacheUtils.getServiceTypeName(orderCustomers.getServiceItemCode()));
		staffWorkInfoJson.setSalary(orderEntity.getAmount() == null? new BigDecimal(0) : orderEntity.getAmount());
		staffWorkInfoJson.setCustomer(orderCustomers.getMemberName());
		workList.add(staffWorkInfoJson);
		return workList;
	}

	/**
	 * 构建返回阿姨详情jsonData中的阿姨求职信息json--基本信息
	 * @param jobInfo
	 * @param tenantsStaffJob
	 */
	public static StaffJobInfoJson buildStaffDetaillJobsInfo(StaffJobInfoJson jobInfo, TenantsStaffJobInfoEntity tenantsStaffJob) {
		if(tenantsStaffJob == null){
			return jobInfo;
		}
		jobInfo.setManageWay(tenantsStaffJob.getManageWay());
		jobInfo.setSelfEvaluation(tenantsStaffJob.getSelfEvaluation());
		if(StringUtils.isNotBlank(tenantsStaffJob.getServiceCity())){
			jobInfo.setServiceCity(tenantsStaffJob.getServiceCity()+Constants.COMMA+DictionarysCacheUtils.getCityName(tenantsStaffJob.getServiceCity()));
		}
		if(StringUtils.isNotBlank(tenantsStaffJob.getServiceCounty())){
			jobInfo.setServiceCounty(tenantsStaffJob.getServiceCounty()+Constants.COMMA+DictionarysCacheUtils.getCountyName(tenantsStaffJob.getServiceCounty()));
		}
		if(StringUtils.isNotBlank(tenantsStaffJob.getServiceProvice())){
			jobInfo.setServiceProvice(tenantsStaffJob.getServiceProvice()+Constants.COMMA+DictionarysCacheUtils.getProviceName(tenantsStaffJob.getServiceProvice()));
		}
		jobInfo.setTeacherEvaluation(tenantsStaffJob.getTeacherEvaluation());
		jobInfo.setWorkExperience(tenantsStaffJob.getWorkExperience());
		jobInfo.setElderlySupport(tenantsStaffJob.getElderlySupport());
		jobInfo.setPetFeeding(tenantsStaffJob.getPetFeeding());
		jobInfo.setPrice(tenantsStaffJob.getPrice());//服务价格
		jobInfo.setServiceArea(tenantsStaffJob.getServiceArea());//服务地域
		jobInfo.setUnit(tenantsStaffJob.getUnit());
		jobInfo.setExperience(tenantsStaffJob.getExperience());
		if(StringUtils.isNotBlank(tenantsStaffJob.getLanguageFeature())){
			jobInfo.setLanguageFeature(DictionarysCacheUtils.getFeaturesList("01", tenantsStaffJob.getLanguageFeature()));
		}
		if(StringUtils.isNotBlank(tenantsStaffJob.getCookingFeature())){
			jobInfo.setCookingFeature(DictionarysCacheUtils.getFeaturesList("02", tenantsStaffJob.getCookingFeature()));
		}
		if(StringUtils.isNotBlank(tenantsStaffJob.getCharacerFeature())){
			jobInfo.setCharacerFeature(DictionarysCacheUtils.getFeaturesList("03", tenantsStaffJob.getCharacerFeature()));
		}
		StringBuffer sb=new StringBuffer();
		if(StringUtils.isNotBlank(tenantsStaffJob.getLanguageFeature())){
			sb.append(DictionarysCacheUtils.getFeaturesStr("01", tenantsStaffJob.getLanguageFeature())).append(Constants.COMMA);
		}
		if(StringUtils.isNotBlank(tenantsStaffJob.getCookingFeature())){
			sb.append(DictionarysCacheUtils.getFeaturesStr("02", tenantsStaffJob.getCookingFeature())).append(Constants.COMMA);
		}
		if(StringUtils.isNotBlank(tenantsStaffJob.getCharacerFeature())){
			sb.append(DictionarysCacheUtils.getFeaturesStr("03", tenantsStaffJob.getCharacerFeature()));
		}
		jobInfo.setFeatureValue(sb.toString());

		return jobInfo;
	}

	/**
	 * 构建返回阿姨详情jsonData中的阿姨求职信息json--基本服务工种
	 * @param serviceItemList
	 * @param serItems
	 */
	public static List<StaffServiceItemJson> buildStaffDetaillJobsSerItems(List<StaffServiceItemJson> serviceItemList,
			TenantsStaffSerItemsEntity serItems) {
		StaffServiceItemJson staffServiceItemJson=new StaffServiceItemJson();
		if(StringUtils.isNotBlank(serItems.getServiceItemCode())){
			staffServiceItemJson.setServiceItemCode(serItems.getServiceItemCode()+Constants.COMMA+DictionarysCacheUtils.getServiceTypeName(serItems.getServiceItemCode()));
		}
		if(StringUtils.isNotBlank(serItems.getServiceNature())){
			staffServiceItemJson.setServiceNature(serItems.getServiceNature()+Constants.COMMA+DictionarysCacheUtils.getServiceNatureStr(serItems.getServiceItemCode(),serItems.getServiceNature()));
		}
		if(StringUtils.isNotBlank(serItems.getServiceItemCode()) && StringUtils.isNotBlank(serItems.getSkills())){
			staffServiceItemJson.setSkills(DictionarysCacheUtils.getSkillsList(serItems.getServiceItemCode(), serItems.getSkills()));
		}
		if (serItems.getId() != null) {
			staffServiceItemJson.setId(serItems.getId());
		}
		serviceItemList.add(staffServiceItemJson);
		
		return serviceItemList;
	}

	/**
	 * 构建返回阿姨详情jsonData中的阿姨个人特点信息json--基本服务工种
	 * @param features
	 */
	public static String buildStaffDetaillJobsFeatures(StaffJobInfoJson staffJobInfoJson,
			TenantsStaffsFeaturesEntity features) {
		String s=staffJobInfoJson.getFeatureValue();
		return s;
	}

	/**
	 * @param obj
	 * @param staffCertForm
	 */
	public static TenantsStaffCertsInfoEntity buildCertInfo(TenantsStaffCertsInfoEntity obj, StaffCertForm staffCertForm) {
		obj.setCertNo(staffCertForm.getCertNo());
		obj.setCertType(staffCertForm.getCertType());
		obj.setStaffId(staffCertForm.getStaffId());
		obj.setCertificationBody(staffCertForm.getCertificationBody());
		obj.setCertificationDate(staffCertForm.getCertificationDate());
		obj.setCertExpireDate(staffCertForm.getCertExpireDate());
		obj.setCertifiedStatus(Status.CertifiedStatus.UN_CERTIFIED);
		obj.setLevel("0");
		obj.setIsUsable("01");
		if ("03".equals(staffCertForm.getCertificationBody())) {
			obj.setCertificationBody(staffCertForm.getCertificationBody());// @xiehui
																			// 认证机构
			obj.setOtherCertificationBody(staffCertForm.getOtherCertificationBody());
		} else {
			obj.setCertificationBody(staffCertForm.getCertificationBody());// @xiehui
			obj.setOtherCertificationBody("");																// 认证机构
		}
		if(staffCertForm.getCertType().equals("18") || staffCertForm.getCertType().equals("19")){
			obj.setType("02");
			obj.setAuthenticateGrade("");
		}else {
			obj.setAuthenticateGrade(staffCertForm.getAuthenticateGrade());//@xiehui鉴定等级
			obj.setType("01");
		}
		return obj;
	}

}
