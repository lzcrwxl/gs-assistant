package com.fbee.modules.operation;

import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.CustomerSaveForm;
import com.fbee.modules.jsonData.extend.TenantsCustomerJson;
import com.fbee.modules.mybatis.entity.TenantsCustomersBaseEntity;
import com.fbee.modules.mybatis.model.TenantsCustomersBase;
import com.fbee.modules.utils.DictionarysCacheUtils;

import java.util.Date;

public class CustomerOpt {
	
	DictionarysCacheUtils dictionarysCacheUtils = new DictionarysCacheUtils();
	
	/**
	 * 构建返回json
	 * @param entity
	 * @return
	 */
	public TenantsCustomerJson buildtenantscustomerJson(TenantsCustomersBase entity,int tenantId){
		
		TenantsCustomerJson tenantscustomerJson = new TenantsCustomerJson();
		tenantscustomerJson.setTenantId(tenantId);//租户id
		tenantscustomerJson.setCustomerId(entity.getCustomerId());//客户id
		tenantscustomerJson.setCustomerName(entity.getCustomerName());//客户姓名
		if(StringUtils.isNotBlank(StringUtils.IsNull(entity.getSex()))){
			tenantscustomerJson.setSex(entity.getSex()+","+dictionarysCacheUtils.getSexName(entity.getSex()));//客户性别
		}else{
			tenantscustomerJson.setSex("");//客户性别
		}
		tenantscustomerJson.setCustomerMobile(entity.getCustomerMobile());//客户手机号
		if(StringUtils.isNotBlank(StringUtils.IsNull(entity.getServiceCity()))){ //客户服务区域
			tenantscustomerJson.setServiceCity(entity.getServiceCity()+","+dictionarysCacheUtils.getCityName(entity.getServiceCity()));//客户服务区域
		}else{
			tenantscustomerJson.setServiceCity("");
		}
		if(StringUtils.isNotBlank(StringUtils.IsNull(entity.getServiceCounty()))){
			tenantscustomerJson.setServiceCounty(entity.getServiceCounty()+","+dictionarysCacheUtils.getCountyName(entity.getServiceCounty()));
		}else{
			tenantscustomerJson.setServiceCounty("");
		}
		if(StringUtils.isNotBlank(StringUtils.IsNull(entity.getServiceProvice()))){
			tenantscustomerJson.setServiceProvice(entity.getServiceProvice()+","+dictionarysCacheUtils.getProviceName(entity.getServiceProvice()));
		}else{
			tenantscustomerJson.setServiceProvice("");
		}
		tenantscustomerJson.setContactAddress(StringUtils.IsNull(entity.getContactAddress()));//客户地址
		tenantscustomerJson.setFamilyCount(StringUtils.IsNull(entity.getFamilyCount()));//家庭人数
		if(StringUtils.isNotBlank(entity.getHouseType())){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>"+entity.getHouseType()+"---------getHouseType---"+dictionarysCacheUtils.getHouseType(entity.getHouseType())+"----------------");
			tenantscustomerJson.setHouseType(entity.getHouseType()+","+dictionarysCacheUtils.getHouseType(entity.getHouseType()));//住宅类型
		}else{
			tenantscustomerJson.setHouseType("");
		}
		tenantscustomerJson.setHouseArea(StringUtils.IsNull(entity.getHouseArea()));//住宅面积
		tenantscustomerJson.setChildrenCount(StringUtils.IsNull(entity.getChildrenCount()));//儿童数
		if(StringUtils.isNotBlank(entity.getChildrenAgeRange())){
			tenantscustomerJson.setChildrenAgeRange(entity.getChildrenAgeRange()+","+dictionarysCacheUtils.getChildrenAge(entity.getChildrenAgeRange()));//儿童年龄段
		}else{
			tenantscustomerJson.setChildrenAgeRange("");
		}
		if(StringUtils.isNotBlank(entity.getOlderAgeRange())){
			tenantscustomerJson.setOlderAgeRange(entity.getOlderAgeRange()+","+dictionarysCacheUtils.getOldAge(entity.getOlderAgeRange()));//老人年龄段
		}else{
			tenantscustomerJson.setOlderAgeRange("");
		}
		tenantscustomerJson.setOlderCount(StringUtils.IsNull(entity.getOlderCount()));//老人数
		if(StringUtils.isNotBlank(entity.getSelfCares())){
			tenantscustomerJson.setSelfCares(entity.getSelfCares()+","+dictionarysCacheUtils.getSelfCares(entity.getSelfCares()));//老人能否自理
		}else{
			tenantscustomerJson.setSelfCares("");
		}
		tenantscustomerJson.setAddTime(DateUtils.dateToStr(entity.getAddTime(), "yyyy-MM-dd HH:mm:ss"));//创建时间
		tenantscustomerJson.setModifyTime(StringUtils.IsNull(entity.getModifyTime()));//修改时间
		return tenantscustomerJson;
	}
	/**
	 * 构建返回用户集合的Json
	 * @param entity
	 * @param orderscount
	 * @return
	 */
	public TenantsCustomerJson buildtenantscustomerJsons(TenantsCustomersBase entity,int orderscount){
		
		TenantsCustomerJson tenantscustomerJson = new TenantsCustomerJson();
		tenantscustomerJson.setCustomerId(entity.getCustomerId());//客户id
		tenantscustomerJson.setCustomerName(entity.getCustomerName());//客户姓名
		if(StringUtils.isNotBlank(StringUtils.IsNull(entity.getSex()))){
			tenantscustomerJson.setSex(dictionarysCacheUtils.getSexName(entity.getSex()));//客户性别
		}else{
			tenantscustomerJson.setSex("");//客户性别
		}
		tenantscustomerJson.setCustomerMobile(entity.getCustomerMobile());//客户手机号
		if(StringUtils.isNotBlank(StringUtils.IsNull(entity.getServiceCity()))){ //客户服务区域
			tenantscustomerJson.setServiceCity(dictionarysCacheUtils.getCityName(entity.getServiceCity()));
		}else{
			tenantscustomerJson.setServiceCity("");
		}
		if(StringUtils.isNotBlank(StringUtils.IsNull(entity.getServiceCounty()))){
			tenantscustomerJson.setServiceCounty(dictionarysCacheUtils.getCountyName(entity.getServiceCounty()));
		}else{
			tenantscustomerJson.setServiceCounty("");
		}
		if(StringUtils.isNotBlank(StringUtils.IsNull(entity.getServiceProvice()))){
			tenantscustomerJson.setServiceProvice(dictionarysCacheUtils.getProviceName(entity.getServiceProvice()));
		}else{
			tenantscustomerJson.setServiceProvice("");
		}
		tenantscustomerJson.setContactAddress(StringUtils.IsNull(entity.getContactAddress()));//客户地址
		tenantscustomerJson.setOrderscount(orderscount);
		return tenantscustomerJson;
	}

	
	/**
	 * 构建新增Entity
	 * @param tenantId
	 * @param customersaveform
	 */
	public TenantsCustomersBaseEntity buildSaveTenantsCustomersBaseEntity(Integer tenantId, CustomerSaveForm customersaveform,String loginAccount) {
		
		TenantsCustomersBaseEntity tenantsCustomersBaseEntity = new TenantsCustomersBaseEntity();
		tenantsCustomersBaseEntity.setTenantId(tenantId);//租户id
		tenantsCustomersBaseEntity.setCustomerName(customersaveform.getCustomerName());//客户姓名
		tenantsCustomersBaseEntity.setSex(customersaveform.getSex());//客户性别
		tenantsCustomersBaseEntity.setCustomerMobile(customersaveform.getCustomerMobile());//客户手机号
		tenantsCustomersBaseEntity.setServiceCity(customersaveform.getServiceCity());//客户服务区域
		tenantsCustomersBaseEntity.setServiceCounty(customersaveform.getServiceCounty());
		tenantsCustomersBaseEntity.setServiceProvice(customersaveform.getServiceProvice());
		tenantsCustomersBaseEntity.setContactAddress(customersaveform.getContactAddress());//客户地址
		tenantsCustomersBaseEntity.setAddAccount(loginAccount);//添加人
		tenantsCustomersBaseEntity.setAddTime(new Date());//添加时间
		tenantsCustomersBaseEntity.setChildrenAgeRange(customersaveform.getChildrenAgeRange());//儿童年龄段
		tenantsCustomersBaseEntity.setChildrenCount(customersaveform.getChildrenCount());//儿童数
		tenantsCustomersBaseEntity.setContactMobile(customersaveform.getContactMobile());//紧急联系电话
		tenantsCustomersBaseEntity.setFamilyCount(customersaveform.getFamilyCount());//家庭人数
		tenantsCustomersBaseEntity.setHouseArea(customersaveform.getHouseArea());//住宅面积
		tenantsCustomersBaseEntity.setHouseType(customersaveform.getHouseType());//住宅类型
		tenantsCustomersBaseEntity.setOlderAgeRange(customersaveform.getOlderAgeRange());//老人年龄段
		tenantsCustomersBaseEntity.setOlderCount(customersaveform.getOlderCount());//老人数
		tenantsCustomersBaseEntity.setSelfCares(customersaveform.getSelfCares());//老人能否自理
		tenantsCustomersBaseEntity.setIsUsable("1");//是否可用,默认可用
		tenantsCustomersBaseEntity.setRemarks(customersaveform.getRemarks());//备注
		
		return tenantsCustomersBaseEntity;
	}
	
	/**
	 * 构建修改Entity
	 * @param customersaveform
	 * @param loginAccount
	 * @return
	 */
	public TenantsCustomersBaseEntity buildUpdateTenantsCustomersBaseEntity(String customerid,CustomerSaveForm customersaveform,String loginAccount) {
		
		TenantsCustomersBaseEntity tenantsCustomersBaseEntity = new TenantsCustomersBaseEntity();
		tenantsCustomersBaseEntity.setCustomerId(Integer.parseInt(customerid));//客户id
		tenantsCustomersBaseEntity.setCustomerName(customersaveform.getCustomerName());//客户姓名
		tenantsCustomersBaseEntity.setSex(customersaveform.getSex());//客户性别
		tenantsCustomersBaseEntity.setCustomerMobile(customersaveform.getCustomerMobile());//客户手机号
		tenantsCustomersBaseEntity.setServiceCity(customersaveform.getServiceCity());//客户服务区域
		tenantsCustomersBaseEntity.setServiceCounty(customersaveform.getServiceCounty());
		tenantsCustomersBaseEntity.setServiceProvice(customersaveform.getServiceProvice());
		tenantsCustomersBaseEntity.setContactAddress(customersaveform.getContactAddress());//客户地址
		tenantsCustomersBaseEntity.setModifyAccount(loginAccount);//修改人
		tenantsCustomersBaseEntity.setModifyTime(new Date());//修改时间
		tenantsCustomersBaseEntity.setFamilyCount(customersaveform.getFamilyCount());//家庭人数
		tenantsCustomersBaseEntity.setHouseArea(customersaveform.getHouseArea());//住宅面积
		tenantsCustomersBaseEntity.setHouseType(customersaveform.getHouseType());//住宅类型
		tenantsCustomersBaseEntity.setOlderAgeRange(customersaveform.getOlderAgeRange());//老人年龄段
		tenantsCustomersBaseEntity.setOlderCount(customersaveform.getOlderCount());//老人数
		tenantsCustomersBaseEntity.setSelfCares(customersaveform.getSelfCares());//老人能否自理
		tenantsCustomersBaseEntity.setChildrenAgeRange(customersaveform.getChildrenAgeRange());//儿童年龄段
		tenantsCustomersBaseEntity.setChildrenCount(customersaveform.getChildrenCount());//儿童数
		
		return tenantsCustomersBaseEntity;
	}

}
