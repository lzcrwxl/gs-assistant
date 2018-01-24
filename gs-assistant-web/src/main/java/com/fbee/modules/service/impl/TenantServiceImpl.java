package com.fbee.modules.service.impl;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.bean.TenantsBannersBean;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.utils.Reflections;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.IndexTenantForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.json.IndexJsonData;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.entity.TenantsBankCardsEntity;
import com.fbee.modules.mybatis.entity.TenantsBannersEntity;
import com.fbee.modules.mybatis.entity.TenantsFundsEntity;
import com.fbee.modules.mybatis.model.*;
import com.fbee.modules.operation.PayRecordesOpt;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.TenantService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.utils.DictionarysCacheUtils;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/** 
* @ClassName: TenantServiceImpl 
* @Description: 租户信息服务实现类
* @author 贺章鹏
* @date 2016年12月29日 上午11:58:55 
*  
*/
@Service
public class TenantServiceImpl extends BaseService implements TenantService{
	
	@Autowired
	TenantsAppsMapper tenantsAppsDao;
	
	@Autowired
	TenantsBankCardsMapper bankCardDao;
	
	@Autowired
	TenantsFundsMapper tenantsFundsDao;
	
	@Autowired
	TenantsBannersMapper tenantsBannersDao;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	TenantsTradeRecordsMapper tenantsTradeRecordsDao; //租户余额表
	
	@Autowired
	TenantsFinanceRecordsMapper tenantsFinanceRecordsDao; //租户交易流水表

	@Autowired
	TenantsFlowMapper tenantsFlowDao; //存储租户充值时支付流水和租户id对应关系

	@Autowired
	TenantsContactBarMapper tenantsContactBarMapper;

	@Autowired
	TradeRecordsMapper tradeRecordsDao; // 支付总表
	
	PayRecordesOpt payRecordesOpt = new PayRecordesOpt();
	
	@Override
	public IndexJsonData getIndexInfo(Integer tenantId) {
		IndexJsonData indexJsonData=new IndexJsonData();
		TenantsApps tenantsApps=(TenantsApps) tenantsAppsDao.getById(tenantId);
		if(tenantsApps!=null){

			String hostUrl = (String) Global.getConfig("webUrl");
			String imageUrl =(String) Global.getConfig("imageUrl");

			indexJsonData.setDomain(tenantsApps.getDomain());
			if(!hostUrl.equals("")){
				indexJsonData.setWebsiteUrl(hostUrl.split("://")[1]+"/"+tenantsApps.getDomain());	
			}else{
				indexJsonData.setWebsiteUrl("");	
			}
			indexJsonData.setStoreName(tenantsApps.getWebsiteName());
			if(!imageUrl.equals("")){
				indexJsonData.setMoblieQrUrl(imageUrl.split("://")[1]);
			}else{
				indexJsonData.setMoblieQrUrl("");
			}
			String privance ="";
			if (StringUtils.isNotEmpty(tenantsApps.getPrivince())) {
				 privance = DictionarysCacheUtils.getProviceName(tenantsApps.getPrivince());
			}
			String city = "";
			if (StringUtils.isNotEmpty(tenantsApps.getCity())) {
				city = DictionarysCacheUtils.getCityName(tenantsApps.getCity());
			}
			String area = "";
			if (StringUtils.isNotEmpty(tenantsApps.getArea())) {
				area = DictionarysCacheUtils.getCountyName(tenantsApps.getArea());
			}
			indexJsonData.setContactAddress(privance+city+area+tenantsApps.getContactAddress());
			indexJsonData.setTenantsPhone(tenantsApps.getTenantsPhone());
			indexJsonData.setDueDate(tenantsApps.getDueDate());
		}
		TenantsBankCardsEntity tenantsBankCards=(TenantsBankCardsEntity) bankCardDao.getById(tenantId);
		if(tenantsBankCards!=null){
			indexJsonData.setBankName(tenantsBankCards.getBankName());
			indexJsonData.setBankLogo(tenantsBankCards.getBankCode());
			indexJsonData.setBankTailNumber(StringUtils.leftCardsNo(tenantsBankCards.getCardNo(), 4));
		}
		TenantsFundsEntity tenantsFunds=(TenantsFundsEntity) tenantsFundsDao.getById(tenantId);
		if(tenantsFunds!=null){
			indexJsonData.setAvailableAmount(tenantsFunds.getAvailableAmount());
			indexJsonData.setGrandTotalAmount(tenantsFunds.getGrandTotalAmount());
			indexJsonData.setFrozenAmount(tenantsFunds.getFrozenAmount());
			indexJsonData.setTotalAmount(tenantsFunds.getTotalAmount());
		}
		return indexJsonData;
	}

	@Override
	public JsonResult modifyIndex(Integer tenantId, IndexTenantForm indexTenantForm) {
		TenantsApps tenantsApps=(TenantsApps) tenantsAppsDao.getById(tenantId);
		if(tenantsApps==null){
			return JsonResult.failure(ResultCode.ERROR);
		}
		Reflections.setFieldValue(tenantsApps,indexTenantForm.getPropertyName(),indexTenantForm.getPropertyValue());
		tenantsAppsDao.updateByPrimaryKey(tenantsApps);
		return JsonResult.success(Reflections.getFieldValue(tenantsApps, indexTenantForm.getPropertyName()));
	}

	@Override
	public TenantsBannersBean getTenantsBannersByTenantId(Integer tenantId) {
		// TODO Auto-generated method stub
		TenantsBannersBean bannerBean=new TenantsBannersBean();
		TenantsBannersEntity tenantsBanners=tenantsBannersDao.getBannerByTenantId(tenantId);		
		if (tenantsBanners==null) {
			return null;
		}
		bannerBean.setBannerId(tenantsBanners.getId());
		bannerBean.setTenantId(tenantId);
		bannerBean.setAddAccount(tenantsBanners.getAddAccount());
		bannerBean.setAddTime(tenantsBanners.getAddTime());
		bannerBean.setBannerName(tenantsBanners.getBannerName());
		bannerBean.setBannerUrl(tenantsBanners.getBannerUrl());
		bannerBean.setIsUsable(tenantsBanners.getIsUsable());
		bannerBean.setModifyAccount(tenantsBanners.getModifyAccount());
		bannerBean.setModifyTime(tenantsBanners.getModifyTime());
		bannerBean.setRemarks(tenantsBanners.getRemarks());
		bannerBean.setShelfTime(tenantsBanners.getShelfTime());
		bannerBean.setShelfType(tenantsBanners.getShelfType());
		bannerBean.setSortNo(tenantsBanners.getSortNo());
		bannerBean.setStatus(tenantsBanners.getStatus());
		
		return bannerBean;
	}

	@Override
	@Transactional
	public void InsertTenantsBanners(TenantsBannersBean banners) {
		// TODO Auto-generated method stub
		TenantsBannersEntity tenantsBanners=new TenantsBannersEntity();
		
		tenantsBanners.setId(banners.getBannerId());
		tenantsBanners.setTenantId(banners.getTenantId());
		tenantsBanners.setAddAccount(banners.getAddAccount());
		tenantsBanners.setAddTime(banners.getAddTime());
		tenantsBanners.setBannerName(banners.getBannerName());
		tenantsBanners.setBannerUrl(banners.getBannerUrl());
		tenantsBanners.setIsUsable(banners.getIsUsable());
		tenantsBanners.setModifyAccount(banners.getModifyAccount());
		tenantsBanners.setModifyTime(banners.getModifyTime());
		tenantsBanners.setRemarks(banners.getRemarks());
		tenantsBanners.setShelfTime(banners.getShelfTime());
		tenantsBanners.setShelfType(banners.getShelfType());
		tenantsBanners.setSortNo(banners.getSortNo());
		tenantsBanners.setStatus(banners.getStatus());
		
		tenantsBannersDao.insert(tenantsBanners);
	}

	@Override
	@Transactional
	public void updateTenantsBanners(TenantsBannersBean banners) {
		// TODO Auto-generated method stub
		TenantsBannersEntity tenantsBanners=new TenantsBannersEntity();
		
		tenantsBanners.setId(banners.getBannerId());
		tenantsBanners.setTenantId(banners.getTenantId());
		tenantsBanners.setAddAccount(banners.getAddAccount());
		tenantsBanners.setAddTime(banners.getAddTime());
		tenantsBanners.setBannerName(banners.getBannerName());
		tenantsBanners.setBannerUrl(banners.getBannerUrl());
		tenantsBanners.setIsUsable(banners.getIsUsable());
		tenantsBanners.setModifyAccount(banners.getModifyAccount());
		tenantsBanners.setModifyTime(banners.getModifyTime());
		tenantsBanners.setRemarks(banners.getRemarks());
		tenantsBanners.setShelfTime(banners.getShelfTime());
		tenantsBanners.setShelfType(banners.getShelfType());
		tenantsBanners.setSortNo(banners.getSortNo());
		tenantsBanners.setStatus(banners.getStatus());
		
		tenantsBannersDao.update(tenantsBanners);
	}
	
	/**
	 * 提现
	 */
	@Override
	public JsonResult withdrawals(String counterFee, String actualArrival,Integer tenantId ,String inOutObject) {
		try{
			//生成实际到账流水号  @zsq   手动生成支付流水号
			String   tradeFlowNo = commonService.createOrderNo(Constants.ZF);//支付流水号 06
			TradeRecords tradeRecords = new TradeRecords();
			tradeRecords.setTradeFlowNo(tradeFlowNo); //支付流水号
			tradeRecords.setTradeType("02");//支付类型 01充值 02提现 03退款
			BigDecimal total_fee = new BigDecimal(actualArrival);
			tradeRecords.setTradeAmount(total_fee);//支付金额(单位换算)
			tradeRecords.setTradeTime(new Date());//支付时间
			tradeRecords.setTradeStatis("01");//支付状态 01处理中 02支付成功 03支付失败
			tradeRecords.setInitiatorType("02");//发起方类型 01客户 02租户
			tradeRecords.setOrderNo("");//订单号
			tradeRecords.setTradeChannel("05");//银行卡
			tradeRecords.setAddTime(new Date());//添加时间
			tradeRecords.setTradeChannelNo(null);//支付渠道流水号
			tradeRecords.setIsUsable("1");//1代表可用 0代表逻辑删除
			tradeRecordsDao.insertSelective(tradeRecords);// 插入支付总表交易记录
			
			TenantsFlow tenantsFlow = new TenantsFlow();
			tenantsFlow.setTradeFlowNo(tradeFlowNo);//支付流水号
			tenantsFlow.setOrderNo("");//订单编号
			tenantsFlow.setTenantId(tenantId);
			tenantsFlow.setPayresult("");
			tenantsFlow.setAddTime(new Date());
			tenantsFlowDao.insertSelective(tenantsFlow);
			
			//总金额
			BigDecimal InOutAmount = new BigDecimal(counterFee).add(new BigDecimal(actualArrival));
			dealAccount(tenantId,"02","03","01",InOutAmount); //冻结交易总额

			//实际到账金额
			String inOutNoAa = commonService.createOrderNo("08");//生成交易流水号 08
			BigDecimal InOutAmountAa = new BigDecimal(actualArrival);
			TenantsFinanceRecords recordsAa = payRecordesOpt.buildWithdrawalsTenantsFinanceRecords(InOutAmountAa, tenantId, inOutNoAa);
			recordsAa.setInOutObject(inOutObject);//收支对象
			recordsAa.setRemarks("账户提现，实际到账金额");
			//recordsAa.setDraweeId(tenantId);//付款方id
			recordsAa.setDraweeType("03");//付款方类型01客户02家政机构03业务员04家政员05平台
			recordsAa.setPayeeId(tenantId);//收款方id
			recordsAa.setPayeeType("05");//收款方类型01客户02家政机构03业务员04家政员05平台
			recordsAa.setPayNo(tradeFlowNo);
			tenantsFinanceRecordsDao.insertSelective(recordsAa);//租户交易总表

			String actualTradeNo = commonService.createOrderNo("07");//生成账户流水号
			TenantsTradeRecords actualTenantsTradeRecords = payRecordesOpt.bulidWithdrawalsTenantsTradeRecords(new BigDecimal(actualArrival),tenantId,actualTradeNo,inOutNoAa);
			tenantsTradeRecordsDao.insertSelective(actualTenantsTradeRecords);//插入提现租户资金变动轨迹表

			//手续费
			String inOutNoCf = commonService.createOrderNo("08");//生成交易流水号
			BigDecimal InOutAmountCf = new BigDecimal(counterFee);
			//手续费不用清算     财务流水状态已处理03
			TenantsFinanceRecords recordsCf = payRecordesOpt.buildCounterTenantsFinanceRecords(InOutAmountCf, tenantId, inOutNoCf);
			recordsCf.setInOutObject("平台");//收支对象
			recordsCf.setRemarks("账户提现，手续费");
			recordsCf.setDraweeType("05");//付款方类型01客户02家政机构03业务员04家政员05平台
			recordsCf.setPayeeId(tenantId);//收款方id
			recordsCf.setPayeeType("02");//收款方类型01客户02家政机构03业务员04家政员05平台
			recordsCf.setRelatedTrans(inOutNoAa);//关联流水号 ： 实际提现金额的财务流水号
			//关联流水号
			tenantsFinanceRecordsDao.insertSelective(recordsCf);//租户交易总表

			String counterTradeNo = commonService.createOrderNo("07");//生成账户流水号
			//手续费不用清算    轨迹表状态已处理03
			TenantsTradeRecords counterTenantsTradeRecords = payRecordesOpt.bulidCounterTenantsTradeRecords(new BigDecimal(counterFee),tenantId,counterTradeNo,inOutNoCf);
			tenantsTradeRecordsDao.insertSelective(counterTenantsTradeRecords);//插入手续费租户资金变动轨迹表

			return JsonResult.success(ResultCode.SUCCESS);
		}catch (Exception e) {
        	e.printStackTrace();
        	Log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
	}
	
	/**
	 * 更新租户账户资金表
	 * @param tenantId
	 * @param payType
	 * @param bussType
	 * @param state
	 * @param money
	 * @return
	 */
	@Override
	public JsonResult dealAccount(Integer tenantId,String payType,String bussType,String state,BigDecimal money){
		try {
			//查询表中是否有该租户资金
			TenantsFunds tenantsFunds = tenantsFundsDao.selectByPrimaryKey(tenantId);
			//如果没有创建租户资金
			if(tenantsFunds == null){
				TenantsFundsEntity tenantsFundsEntity = new TenantsFundsEntity();
				BigDecimal m = BigDecimal.valueOf(0.00);
				tenantsFundsEntity.setAvailableAmount(m);
				tenantsFundsEntity.setFrozenAmount(m);
				tenantsFundsEntity.setGrandTotalAmount(m);
				tenantsFundsEntity.setTotalAmount(m);
				tenantsFundsEntity.setTenantId(tenantId);
				tenantsFundsEntity.setAddTime(new Date());
				if(payType.equals("01")){//收入
					if(bussType.equals("04")){//解冻
						return JsonResult.failure(ResultCode.Funds.FROZEN_AMOUNT_NOT_ENOUGH);
					}else{//其他收入
						if(state.equals("01")){//处理中
							//冻结+
							tenantsFundsEntity.setFrozenAmount(m.add(money));
							//租户总额+
							tenantsFundsEntity.setTotalAmount(m.add(money));
							tenantsFundsDao.insert(tenantsFundsEntity);
						}
						if(state.equals("02")){//已处理
							//可用+
							tenantsFundsEntity.setAvailableAmount(m.add(money));
							//租户总额+
							tenantsFundsEntity.setTotalAmount(m.add(money));
							tenantsFundsDao.insert(tenantsFundsEntity);
						}
					}
				}
				if(payType.equals("02")){//支出
					return JsonResult.failure(ResultCode.Funds.AVAILABLE_AMOUNT_NOT_ENOUGH);
				}
				return JsonResult.success(null);
			}
			if(payType.equals("01")){//收入
				if(bussType.equals("04")){//解冻
					//冻结-
					tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().subtract(money));
					//可用+
					tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().add(money));
					tenantsFunds.setTenantId(tenantId);
					tenantsFunds.setModifyTime(new Date());
					tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
				}else{//其他收入
					if(state.equals("01")){//处理中
						//冻结+
						tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().add(money));
						//租户总额+
						tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().add(money));
						tenantsFunds.setTenantId(tenantId);
						tenantsFunds.setModifyTime(new Date());
						tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
					}
					if(state.equals("02")){//已处理
						//可用+
						tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().add(money));
						//租户总额+
						tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().add(money));
						tenantsFunds.setTenantId(tenantId);
						tenantsFunds.setModifyTime(new Date());
						tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
					}
				}
			}
			if(payType.equals("02")){//支出
				if(bussType.equals("03")){//冻结
					//冻结+
					tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().add(money));
					//可用-
					tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().subtract(money));
					tenantsFunds.setTenantId(tenantId);
					tenantsFunds.setModifyTime(new Date());
					tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
				}else{//其他收支出
					if(state.equals("01")){//处理中
						//余额不足
						if(tenantsFunds.getAvailableAmount().compareTo(money) == -1){
							return JsonResult.failure(ResultCode.Funds.AVAILABLE_AMOUNT_NOT_ENOUGH);
						}
						//冻结-
						tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().subtract(money));
						//租户总额-
						tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().subtract(money));
						tenantsFunds.setTenantId(tenantId);
						tenantsFunds.setModifyTime(new Date());
						tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
					}
					if(state.equals("02")){//已处理
						//余额不足
						if(tenantsFunds.getAvailableAmount().compareTo(money) == -1){
							return JsonResult.failure(ResultCode.Funds.AVAILABLE_AMOUNT_NOT_ENOUGH);
						}
						//可用-
						tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().subtract(money));
						//租户总额-
						tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().subtract(money));
						tenantsFunds.setTenantId(tenantId);
						tenantsFunds.setModifyTime(new Date());
						tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
					}
				}
			}
			return JsonResult.success(null);
		}catch (Exception e) {
        	e.printStackTrace();
        	Log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
	}

	/**
	 * 充值
	 */
	@Override
	public JsonResult recharge(String flowNo,Integer tenantId,String addAccount) {
		TenantsFlow record = new TenantsFlow();
		record.setTradeFlowNo(flowNo);
		record.setTenantId(tenantId);
		record.setAddTime(new Date());
		record.setAddAccount(addAccount);
		record.setPayresult("1");
		tenantsFlowDao.insertSelective(record);
		return null;
	}
	
	/**
	 * 查询支付结果
	 */
	@Override
	public JsonResult getPayResult(String flowNo) {
		String payResult = tenantsFlowDao.selectByPrimaryKey(flowNo).getPayresult();
		return JsonResult.success(payResult);
	}

	/**
	 * 获取banner信息
	 */
	@Override
	public JsonResult getBannerInfo(Integer tenantId){
		return JsonResult.success(tenantsBannersDao.getBannerByTenantId(tenantId));
	}

	/**
	 * 获取banner信息
	 */
	@Override
	public JsonResult findBannerList(Integer tenantId){
		return JsonResult.success(tenantsBannersDao.findBannersByTenantId(tenantId));
	}

	@Override
	public JsonResult getQrCode(Integer tenantId) {
		return JsonResult.success("images"+tenantsContactBarMapper.selectByPrimaryKey(tenantId).getQrCode());
	}

	@Override
	public JsonResult getDomain(UserBean userBean) {
		if(userBean!=null){
			TenantsApps tenantsApps = tenantsAppsDao.selectByPrimaryKey(userBean.getTenantId());
			userBean.setDomain(tenantsApps.getDomain());
		}
		return JsonResult.success(userBean);
	}
}
