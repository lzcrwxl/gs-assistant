package com.fbee.modules.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.core.Log;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.dao.TenantsFundsMapper;
import com.fbee.modules.mybatis.dao.TenantsTradeRecordsMapper;
import com.fbee.modules.mybatis.entity.TenantsFundsEntity;
import com.fbee.modules.mybatis.model.TenantsFunds;
import com.fbee.modules.mybatis.model.TenantsTradeRecords;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.DealAccountService;

@Service
public class DealAccountServiceImpl implements DealAccountService{
	
	@Autowired
	TenantsTradeRecordsMapper tenantsTradeRecordsMapper;
	@Autowired
	TenantsFundsMapper tenantsFundsMapper;
	@Autowired
	CommonService commonService;
	
	@Override
	public JsonResult dealAccount(Integer tenantId, String bussType,String payType, String state,BigDecimal money){
		
			try {
				//查询表中是否有该租户资金
				TenantsFunds tenantsFunds = tenantsFundsMapper.selectByPrimaryKey(tenantId);
				//如果没有创建租户资金
				if(tenantsFunds == null){
					TenantsFundsEntity tenantsFundsEntity = new TenantsFundsEntity();
					tenantsFundsEntity.setAvailableAmount(BigDecimal.valueOf(0.00));
					tenantsFundsEntity.setFrozenAmount(BigDecimal.valueOf(0.00));
					tenantsFundsEntity.setGrandTotalAmount(BigDecimal.valueOf(0.00));
					tenantsFundsEntity.setTotalAmount(BigDecimal.valueOf(0.00));
					tenantsFundsEntity.setTenantId(tenantId);
					tenantsFundsMapper.insert(tenantsFundsEntity);
					return JsonResult.failure(ResultCode.DATA_ERROR);
				}
				if(payType.equals("01")){//收入
					if(bussType.equals("04")){//解冻
						//冻结-
						tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().subtract(money));
						//可用+
						tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().add(money));
						tenantsFunds.setTenantId(tenantId);
						tenantsFundsMapper.updateByPrimaryKeySelective(tenantsFunds);
					}else{//其他收入
						if(state.equals("01")){//处理中
							//冻结+
							tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().add(money));
							//租户总额+
							tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().add(money));
							tenantsFunds.setTenantId(tenantId);
							tenantsFundsMapper.updateByPrimaryKeySelective(tenantsFunds);
						}
						if(state.equals("02")){//已处理
							//可用+
							tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().add(money));
							//租户总额+
							tenantsFunds.setTenantId(tenantId);
							tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().add(money));
							tenantsFundsMapper.updateByPrimaryKeySelective(tenantsFunds);
						}
					}
				}
				if(payType.equals("02")){//支出
					if(bussType.equals("03")){//冻结
						//冻结+
						tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().add(money));
						//可用-
						tenantsFunds.setTenantId(tenantId);
						tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().subtract(money));
						tenantsFundsMapper.updateByPrimaryKeySelective(tenantsFunds);
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
							tenantsFundsMapper.updateByPrimaryKeySelective(tenantsFunds);
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
							tenantsFundsMapper.updateByPrimaryKeySelective(tenantsFunds);
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
	 * 处理账户轨迹表<br>
	 * 处理租户交易记录轨迹表
	 */
	@Override
	public JsonResult dealAccountTrace(Integer tenantId, String bussType, String payType, String state,
			BigDecimal money, String orderNo, String remarks) {
		TenantsTradeRecords tenantsTradeRecords = new TenantsTradeRecords();
		Date date = new Date();
		String tradeRecordsNo = commonService.createOrderNo(Constants.ZH);
		tenantsTradeRecords.setTradeNo(tradeRecordsNo);//P //账户流水号
		tenantsTradeRecords.setTenantId(tenantId);  //租户id
		tenantsTradeRecords.setTradeType(bussType); //业务类型 bussType 交易类型 01：充值 02：提现 03:冻结 04:解冻 05：手续费
		tenantsTradeRecords.setStatus(state);       //交易状态 01处理中 02交易成功 03交易失败
		tenantsTradeRecords.setFinanceType(payType);//财务类型 01收入 02支出
		tenantsTradeRecords.setTradeAmount(money);  //交易金额
		tenantsTradeRecords.setTradeTime(date);     //交易时间
		tenantsTradeRecords.setAddTime(date);       //添加时间
		tenantsTradeRecords.setIsUsable("1");       //1代表可用 0代表逻辑删除
		tenantsTradeRecords.setOrderNo(orderNo);
		tenantsTradeRecords.setRemarks(remarks);
		tenantsTradeRecordsMapper.insert(tenantsTradeRecords);
		return JsonResult.success(tradeRecordsNo);
	}
	
}
