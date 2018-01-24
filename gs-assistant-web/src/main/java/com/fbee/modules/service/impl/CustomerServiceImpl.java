package com.fbee.modules.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.form.CustomerQueryForm;
import com.fbee.modules.form.CustomerSaveForm;
import com.fbee.modules.form.OrderCustomerInfoForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.extend.TenantsCustomerJson;
import com.fbee.modules.mybatis.dao.TenantsCustomersBaseMapper;
import com.fbee.modules.mybatis.entity.TenantsCustomersBaseEntity;
import com.fbee.modules.mybatis.model.TenantsCustomersBase;
import com.fbee.modules.operation.CustomerOpt;
import com.fbee.modules.service.CustomerService;
import com.fbee.modules.service.basic.BaseService;
import com.google.common.collect.Lists;

@Service
public class CustomerServiceImpl extends BaseService implements CustomerService {

	@Autowired
	TenantsCustomersBaseMapper tenantsCustomersBaseMapper;
	
	CustomerOpt customeropt = new CustomerOpt();
	
	/**
	 * 客户管理-客户查询
	 */
	@Override
	public JsonResult queryCustomer(Integer tenantId, CustomerQueryForm customerqueryform, int pageNumber,
			int pageSize) {
		try{
			//获取总条数
			Map<String, Object>  map =new HashMap<String, Object>();
			map.put("tenantId", tenantId);
			map.put("customerName",customerqueryform.getCustomerName());
			map.put("customerMobile",customerqueryform.getCustomerMobile());
			map.put("serviceProvice",customerqueryform.getServiceProvice());
			map.put("serviceCity",customerqueryform.getServiceCity());
			map.put("serviceCounty",customerqueryform.getServiceCounty());
			Integer totalCount = tenantsCustomersBaseMapper.getCustomerQueryCount(map);
			//分页实体
			Page<TenantsCustomerJson> page = new Page<TenantsCustomerJson>();
			page.setPage(pageNumber);
	        page.setRowNum(pageSize);
	        if(totalCount==null){
	            return JsonResult.success(page);
	        }
	        //最大页数判断
	        int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
	        if (pageM > 0) {
	            page.setPage(pageM);
	        }
	        map.put("pageNumber", page.getOffset());
	        map.put("pageSize", page.getRowNum());
	        if (totalCount > 0) {
	        	List<TenantsCustomersBase> list=tenantsCustomersBaseMapper.getCustomerQueryList(map);
	        	List<TenantsCustomerJson> resultList=Lists.newArrayList();
	        	
	        	for(TenantsCustomersBase entity:list){
	        		
	        		Map<String, Object>  map2 =new HashMap<String, Object>();
	        		map2.put("tenantId", tenantId);
	        		map2.put("membermobile", entity.getCustomerMobile());
	        		int orderscount = tenantsCustomersBaseMapper.getOrdersCount(map2);//客户订单数
	        		
	        		//添加返回结果集
	        		resultList.add(customeropt.buildtenantscustomerJsons(entity, orderscount));
	        	}
	        	page.setRows(resultList);
	            page.setRecords(totalCount.longValue());
	        }
	        return JsonResult.success(page);
		}catch (Exception e) {
	        	e.printStackTrace();
	        	Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
	            return JsonResult.failure(ResultCode.DATA_ERROR);
	    }
	}
	
	/**
	 * 客户管理-客户详情
	 *
	 */
	public JsonResult getCustomer(Integer tenantId,String customerid){
		
		try{
			Map<String, Object>  map =new HashMap<String, Object>();
			map.put("tenantId", tenantId);
			map.put("customerid", Integer.parseInt(customerid));
			TenantsCustomersBase entity = tenantsCustomersBaseMapper.getCustomer(map);
			return JsonResult.success(customeropt.buildtenantscustomerJson(entity,tenantId));
		}catch(Exception e){
			e.printStackTrace();
        	Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
		}
	}

	/**
	 * 客户管理-新增客户
	 */
	public JsonResult saveCustomer(Integer tenantId, CustomerSaveForm customersaveform,String loginAccount) {
		
		try{
			//判断手机号码是否存在，如果存在，则返回错误码 Baron
			Map<String, Object> map = new HashMap<String, Object>();
			TenantsCustomersBaseEntity tenantsCustomersBaseEntity = customeropt.buildSaveTenantsCustomersBaseEntity(tenantId,customersaveform,loginAccount);
			//根据租户和手机号查询客户信息
			map.put("customerMobile", tenantsCustomersBaseEntity.getCustomerMobile());
			map.put("tenantId", tenantId);
			Integer customerId = tenantsCustomersBaseMapper.getCustomerByCustomerMobile(map);
			if(customerId != null){//客户存在，更新客户
				return JsonResult.failure(ResultCode.DATA_ERROR);
			}

			return JsonResult.success(tenantsCustomersBaseMapper.saveCustomer(tenantsCustomersBaseEntity));
		}catch(Exception e){
			e.printStackTrace();
        	Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
		}
	}

	
	/**
	 * 客户管理-修改客户
	 */
	public JsonResult updateCustomer(String customerid,CustomerSaveForm customersaveform, String loginAccount) {
		
		try{
			//判断手机号码是否存在，如果存在，则返回错误码 Baron
			Map<String, Object> map = new HashMap<String, Object>();
			TenantsCustomersBaseEntity tenantsCustomersBaseEntity = customeropt.buildUpdateTenantsCustomersBaseEntity(customerid,customersaveform,loginAccount);
			//根据租户和手机号查询客户信息
			map.put("customerMobile", tenantsCustomersBaseEntity.getCustomerMobile());
			map.put("tenantId", customersaveform.getTenantId());
			Integer customerId = tenantsCustomersBaseMapper.getCustomerByCustomerMobile(map);
			if(customerId != null&&null != tenantsCustomersBaseEntity.getCustomerId()&&customerId!=tenantsCustomersBaseEntity.getCustomerId()){//客户存在，更新客户
				return JsonResult.failure(ResultCode.DATA_ERROR);
			}
			return JsonResult.success(tenantsCustomersBaseMapper.updateCustomer(tenantsCustomersBaseEntity));
		}catch(Exception e){
			e.printStackTrace();
        	Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
		}
	}

	/**
	 * 订单管理保存或更新客户信息
	 * @param customerId
	 * @param customersaveform
	 * @param tenantId
	 * @return
	 */
	@Override
	public JsonResult saveOrUpdateCustomer(OrderCustomerInfoForm form, Integer tenantId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//根据租户和手机号查询客户信息
		map.put("customerMobile", form.getMemberMobile());
		map.put("tenantId", tenantId);
		Integer customerId = tenantsCustomersBaseMapper.getCustomerByCustomerMobile(map);
		TenantsCustomersBaseEntity tenantsCustomersBaseEntity = new TenantsCustomersBaseEntity();
		tenantsCustomersBaseEntity.setTenantId(tenantId);
		tenantsCustomersBaseEntity.setCustomerName(form.getMemberName());
		tenantsCustomersBaseEntity.setSex(form.getSex());
		tenantsCustomersBaseEntity.setCustomerMobile(form.getMemberMobile());
		tenantsCustomersBaseEntity.setServiceProvice(form.getServiceProvice());
		tenantsCustomersBaseEntity.setServiceCity(form.getServiceCity());
		tenantsCustomersBaseEntity.setServiceCounty(form.getServiceCounty());
		tenantsCustomersBaseEntity.setFamilyCount(form.getFamilyCount());
		tenantsCustomersBaseEntity.setHouseType(form.getHouseType());
		tenantsCustomersBaseEntity.setContactAddress(form.getServiceAddress());
		if(StringUtils.isNotBlank(form.getHouseArea())){
			tenantsCustomersBaseEntity.setHouseArea(BigDecimal.valueOf(Double.parseDouble(form.getHouseArea())));
		}else {
			tenantsCustomersBaseEntity.setHouseArea(null);
		}

		tenantsCustomersBaseEntity.setChildrenCount(form.getChildrenCount());
		tenantsCustomersBaseEntity.setChildrenAgeRange(form.getChildrenAgeRange());
		tenantsCustomersBaseEntity.setOlderCount(form.getOlderCount());
		tenantsCustomersBaseEntity.setOlderAgeRange(form.getOlderAgeRange());
		tenantsCustomersBaseEntity.setSelfCares(form.getSelfCares());
		//判断客户是否存在
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(customerId != null){//客户存在，更新客户
			tenantsCustomersBaseEntity.setCustomerId(customerId);
			tenantsCustomersBaseEntity.setModifyTime(new Date());

			if(null!=tenantsCustomersBaseEntity.getSelfCares()&&"".equals(tenantsCustomersBaseEntity.getSelfCares()))tenantsCustomersBaseEntity.setSelfCares(null);
			if(null!=tenantsCustomersBaseEntity.getHouseType()&&"".equals(tenantsCustomersBaseEntity.getHouseType()))tenantsCustomersBaseEntity.setHouseType(null);
			if(null!=tenantsCustomersBaseEntity.getChildrenAgeRange()&&"".equals(tenantsCustomersBaseEntity.getChildrenAgeRange()))tenantsCustomersBaseEntity.setChildrenAgeRange(null);
			if(null!=tenantsCustomersBaseEntity.getSex()&&"".equals(tenantsCustomersBaseEntity.getSex()))tenantsCustomersBaseEntity.setSex(null);
			if(null!=tenantsCustomersBaseEntity.getContactAddress()&&"".equals(tenantsCustomersBaseEntity.getContactAddress()))tenantsCustomersBaseEntity.setContactAddress(null);
			if(null!=tenantsCustomersBaseEntity.getOlderAgeRange()&&"".equals(tenantsCustomersBaseEntity.getOlderAgeRange()))tenantsCustomersBaseEntity.setOlderAgeRange(null);
			if(null!=tenantsCustomersBaseEntity.getChildrenCount()&&"".equals(tenantsCustomersBaseEntity.getChildrenCount()))tenantsCustomersBaseEntity.setChildrenCount(null);
			tenantsCustomersBaseMapper.updateCustomer(tenantsCustomersBaseEntity);
			resultMap.put("customerId", customerId);
			return JsonResult.success(null);
		}else {//客户不存在，保存客户
			tenantsCustomersBaseEntity.setAddTime(new Date());
			tenantsCustomersBaseMapper.saveCustomer(tenantsCustomersBaseEntity);
			resultMap.put("customerId", tenantsCustomersBaseEntity.getCustomerId());
			return JsonResult.success(null);
		}
	}

	@Override
	public JsonResult updateCustomerHomeInfo(String customerid, CustomerSaveForm customersaveform,
			String loginAccount) {
		try{
			TenantsCustomersBaseEntity tenantsCustomersBaseEntity = customeropt.buildUpdateTenantsCustomersBaseEntity(customerid,customersaveform,loginAccount);
			return JsonResult.success(tenantsCustomersBaseMapper.updateCustomerHomeInfo(tenantsCustomersBaseEntity));
		}catch(Exception e){
			e.printStackTrace();
        	Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
		}
	}
	
}
