package com.fbee.modules.service;

import org.apache.http.client.methods.HttpPost;

import com.fbee.modules.bean.TenantsBannersBean;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.form.IndexTenantForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.json.IndexJsonData;

import java.math.BigDecimal;

/** 
* @ClassName: TenantService 
* @Description: 租户信息服务接口:首页信息
* @author 贺章鹏
* @date 2016年12月29日 上午11:57:48 
*  
*/
public interface TenantService {

	/**
	 * 根据租户id获取首页信息
	 * @return
	 */
	IndexJsonData getIndexInfo(Integer tenantId);

	/**
	 * @param tenantId
	 * @param indexTenantForm
	 * @return
	 */
	JsonResult modifyIndex(Integer tenantId, IndexTenantForm indexTenantForm);
	/**
	 * 通过用户缓存对象，查找租户banner信息
	 * */
	TenantsBannersBean getTenantsBannersByTenantId(Integer tenantId);
	/**
	 * 将用户缓存的banner信息持久化
	 * */
	void InsertTenantsBanners(TenantsBannersBean tenantsBannersBean);
	/**
	 * 更新用户banner信息
	 * */
	void updateTenantsBanners(TenantsBannersBean tenantsBannersBean);

	/**
	 * 提现
	 * @param counterFee
	 * @param actualArrival
	 */
	JsonResult withdrawals(String counterFee,String actualArrival,Integer tenantId,String inOutObject);
	
	/**
	 * 充值
	 * @param flowNo
	 * @param integer
	 * @return
	 */
	JsonResult recharge(String flowNo, Integer integer,String addAccount);
	
	/**
	 * 查询支付结果
	 * @param flowNo
	 * @return
	 */
	JsonResult getPayResult(String flowNo);
	
	 /** 获取banner信息
	 */
	JsonResult getBannerInfo(Integer tenantId);
	JsonResult findBannerList(Integer tenantId);
	
	/**
	 * 获取租户二维码
	 * @param tenantId
	 * @return
	 */
	JsonResult getQrCode(Integer tenantId);
	
	/**
	 * 获取租户二级域名
	 * @return
	 */
	JsonResult getDomain(UserBean userBean);
	

	/**
	 * 资金变动
	 * @param tenantId
	 * @param payType
	 * @param bussType
	 * @param state
	 * @param money
	 * @return
	 */
	JsonResult dealAccount(Integer tenantId,String payType,String bussType,String state,BigDecimal money);
}
