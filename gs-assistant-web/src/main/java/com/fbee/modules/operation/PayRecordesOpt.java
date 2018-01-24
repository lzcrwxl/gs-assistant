package com.fbee.modules.operation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.fbee.modules.bean.consts.FinanceConst;
import com.fbee.modules.mybatis.model.TenantsFinanceRecords;
import com.fbee.modules.mybatis.model.TenantsTradeRecords;
import com.fbee.modules.mybatis.model.TradeRecords;

public class PayRecordesOpt {
	
	/**
	 * 插入提现租户交易总表
	 * @param InOutAmount
	 * @param tenantId
	 * @param inOutNo
	 * @return
	 */
	public TenantsFinanceRecords buildWithdrawalsTenantsFinanceRecords(BigDecimal InOutAmount,Integer tenantId,String inOutNo) {
		TenantsFinanceRecords tenantsFinanceRecords = new TenantsFinanceRecords();
		tenantsFinanceRecords.setInOutNo(inOutNo);//交易流水号
		tenantsFinanceRecords.setTenantId(tenantId);//租户id
		tenantsFinanceRecords.setPayType("05");//交易类型 01 订单支付 02成单加价 03成单奖励 04账户充值 05账户提现 06会员续费
		tenantsFinanceRecords.setInOutAmount(InOutAmount);//交易金额
		tenantsFinanceRecords.setTransType("02");//交易方式 01线上 02线下
		tenantsFinanceRecords.setInOutType("02");//提现,收支类型为支出
		tenantsFinanceRecords.setAddTime(new Date());//添加时间
		tenantsFinanceRecords.setIsUsable("1"); //1代表可用 0代表逻辑删除
		tenantsFinanceRecords.setStatus("01");//01待处理 02处理中 03已处理
		tenantsFinanceRecords.setPayeeId(tenantId);//收款方id
		return tenantsFinanceRecords;
	}

	/**
	 * 插入租户交易总表
	 * @return
	 */
	public TenantsFinanceRecords buildTenantsFinanceRecords(Integer tenantId, String inOutNo, BigDecimal amount,
															FinanceConst.PayType payType, FinanceConst.TransType transType,
															FinanceConst.InOutType inOutType, FinanceConst.Status status) {
		TenantsFinanceRecords tenantsFinanceRecords = new TenantsFinanceRecords();
		tenantsFinanceRecords.setInOutNo(inOutNo);//交易流水号
		tenantsFinanceRecords.setTenantId(tenantId);//租户id
		tenantsFinanceRecords.setPayType(payType.getCode());//交易类型 01 订单支付 02成单加价 03成单奖励 04账户充值 05账户提现 06会员续费 11保证金
		tenantsFinanceRecords.setInOutAmount(amount);//交易金额
		tenantsFinanceRecords.setTransType(transType.getCode());//交易方式 01线上 02线下
		tenantsFinanceRecords.setInOutType(inOutType.getCode());//收支类型
		tenantsFinanceRecords.setAddTime(new Date());//添加时间
		tenantsFinanceRecords.setIsUsable("1"); //1代表可用 0代表逻辑删除
		tenantsFinanceRecords.setStatus(status.getCode());//01待处理 02处理中 03已处理
		tenantsFinanceRecords.setPayeeId(tenantId);//收款方id
		return tenantsFinanceRecords;
	}

	/**
	 * 插入提现手续费租户交易总表
	 * @param InOutAmount
	 * @param tenantId
	 * @param inOutNo
	 * @return
	 */
	public TenantsFinanceRecords buildCounterTenantsFinanceRecords(BigDecimal InOutAmount,Integer tenantId,String inOutNo) {
		TenantsFinanceRecords tenantsFinanceRecords = new TenantsFinanceRecords();
		tenantsFinanceRecords.setInOutNo(inOutNo);//交易流水号
		tenantsFinanceRecords.setTenantId(tenantId);//租户id
		tenantsFinanceRecords.setPayType("12");// 交易类型 01 订单支付 02成单加价 03成单奖励 04账户充值 05账户提现 06会员续费 07报名费 08住宿费 09佣金费10取消订单退定金11:服务费12手续费
		tenantsFinanceRecords.setInOutAmount(InOutAmount);//交易金额
		tenantsFinanceRecords.setTransType("02");//交易方式 01线上 02线下
		tenantsFinanceRecords.setInOutType("02");//提现,收支类型为支出
		tenantsFinanceRecords.setAddTime(new Date());//添加时间
		tenantsFinanceRecords.setIsUsable("1"); //1代表可用 0代表逻辑删除
		tenantsFinanceRecords.setStatus("03");//01待处理 02处理中 03已处理
		tenantsFinanceRecords.setPayeeId(tenantId);//收款方id
		return tenantsFinanceRecords;
	}
	
	
    /**
     * 插入提现租户资金变动轨迹表
     * @param InOutAmount
     * @param tenantId
     * @param tradeNo
     * @param InOutNo
     * @return
     */
    public  TenantsTradeRecords bulidWithdrawalsTenantsTradeRecords(BigDecimal InOutAmount,Integer tenantId,String tradeNo,String InOutNo){
    	TenantsTradeRecords tenantsTradeRecords=new TenantsTradeRecords();
    	tenantsTradeRecords.setTradeNo(tradeNo);//账户流水号
    	tenantsTradeRecords.setTenantId(tenantId);//租户id
    	tenantsTradeRecords.setTradeTime(new Date());//交易时间
    	tenantsTradeRecords.setFinanceType("02"); //财务类型 01收入 02支出
    	tenantsTradeRecords.setTradeAmount(InOutAmount);//交易金额
    	tenantsTradeRecords.setStatus("01");//交易状态 01处理中 02交易成功 03交易失败
    	tenantsTradeRecords.setInOutNo(InOutNo);//交易流水号
		tenantsTradeRecords.setTradeType("02"); //交易类型  01：充值 02：提现 03:冻结 04:解冻 05：手续费 06：支付定金 07：支付尾款 08成单奖励
    	tenantsTradeRecords.setAddTime(new Date());//添加时间
    	tenantsTradeRecords.setIsUsable("1");//是否可用 0不可用 1可用
		return tenantsTradeRecords;
    }
    
    /**
     * 插入手续费租户资金变动轨迹表
     * @param InOutAmount
     * @param tenantId
     * @param tradeNo
     * @param InOutNo
     * @return
     */
    public  TenantsTradeRecords bulidCounterTenantsTradeRecords(BigDecimal InOutAmount,Integer tenantId,String tradeNo,String InOutNo){
    	TenantsTradeRecords tenantsTradeRecords=new TenantsTradeRecords();
    	tenantsTradeRecords.setTradeNo(tradeNo);//账户流水号
    	tenantsTradeRecords.setTenantId(tenantId);//租户id
    	tenantsTradeRecords.setTradeTime(new Date());//交易时间
    	tenantsTradeRecords.setFinanceType("02"); //财务类型 01收入 02支出
    	tenantsTradeRecords.setTradeAmount(InOutAmount);//交易金额
    	tenantsTradeRecords.setStatus("02");//交易状态 01处理中 02交易成功 03交易失败
    	tenantsTradeRecords.setInOutNo(InOutNo);//交易流水号
		tenantsTradeRecords.setTradeType("05"); //交易类型  01：充值 02：提现 03:冻结 04:解冻 05：手续费 06：支付定金 07：支付尾款 08成单奖励
    	tenantsTradeRecords.setAddTime(new Date());//添加时间
    	tenantsTradeRecords.setIsUsable("1");//是否可用 0不可用 1可用
		return tenantsTradeRecords;
    }

	/**
	 * 充值失败时支付总表
	 * @param xmlDataMap
	 * @param tradeFlowNo
	 * @return
	 * @throws ParseException
	 */
	public TradeRecords buildFailTradeRecords(Map<String, Object> xmlDataMap, String tradeFlowNo) throws ParseException{
		TradeRecords tradeRecords = new TradeRecords();
		tradeRecords.setTradeFlowNo(tradeFlowNo); //支付流水号
		tradeRecords.setTradeType("01");//支付类型 01充值 02提现 03退款
		BigDecimal total_fee = new BigDecimal((String)xmlDataMap.get("total_fee"));
		BigDecimal danwei = new BigDecimal("0.01");
		tradeRecords.setTradeAmount(total_fee.multiply(danwei));//支付金额(单位换算)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		tradeRecords.setTradeTime(sdf.parse((String)xmlDataMap.get("time_end")));//支付时间
		tradeRecords.setTradeStatis("03");//支付状态 01处理中 02支付成功 03支付失败
		tradeRecords.setFailureMsg((String)xmlDataMap.get("pay_info"));//失败信息
		tradeRecords.setInitiatorType("02");//发起方类型 01客户 02租户
		tradeRecords.setOrderNo("0000");//充值时支付表中的订单号
		String tradetype = (String)xmlDataMap.get("trade_type");//交易类型
		if(tradetype.equals("pay.alipay.native")){
			tradeRecords.setTradeChannel("01");//支付宝
		}else if(tradetype.equals("pay.weixin.native")){
			tradeRecords.setTradeChannel("02");//微信扫码
		}else{
			tradeRecords.setTradeChannel("03");//公众号
		}
		tradeRecords.setAddTime(new Date());//添加时间
		tradeRecords.setTradeChannelNo((String)xmlDataMap.get("out_transaction_id"));//支付渠道流水号
		tradeRecords.setIsUsable("1");//1代表可用 0代表逻辑删除
		return tradeRecords;
	}

	/**
	 * 插入充值时支付总表
	 * @param xmlDataMap
	 * @param tradeFlowNo
	 * @return
	 * @throws ParseException
	 */
	public TradeRecords buildRechargeTradeRecords(Map<String, Object> xmlDataMap,String tradeFlowNo) throws ParseException{
		TradeRecords tradeRecords = new TradeRecords();
		tradeRecords.setTradeFlowNo(tradeFlowNo); //支付流水号
		tradeRecords.setTradeType("01");//支付类型 01充值 02提现 03退款04支付定金05支付尾款
		BigDecimal total_fee = new BigDecimal((String)xmlDataMap.get("total_fee"));
		BigDecimal danwei = new BigDecimal("0.01");
		tradeRecords.setTradeAmount(total_fee.multiply(danwei));//支付金额(单位换算)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		tradeRecords.setTradeTime(sdf.parse((String)xmlDataMap.get("time_end")));//支付时间
		tradeRecords.setTradeStatis("02");//支付状态 01处理中 02支付成功 03支付失败
		tradeRecords.setInitiatorType("02");//发起方类型 01客户 02租户
		tradeRecords.setOrderNo("0000");//充值时支付表中的订单号
		String tradetype = (String)xmlDataMap.get("trade_type");//交易类型
		if(tradetype.equals("pay.alipay.native")){
			tradeRecords.setTradeChannel("01");//支付宝
		}else if(tradetype.equals("pay.weixin.native")){
			tradeRecords.setTradeChannel("02");//微信扫码
		}else{
			tradeRecords.setTradeChannel("03");//公众号
		}
		tradeRecords.setAddTime(new Date());//添加时间
		tradeRecords.setTradeChannelNo((String)xmlDataMap.get("out_transaction_id"));//支付渠道流水号
		tradeRecords.setIsUsable("1");//1代表可用 0代表逻辑删除
		return tradeRecords;
	}

	/**
	 * 插入账户充值租户交易总表
	 * @param xmlDataMap
	 * @param payNo
	 * @param inOutNo
	 * @return
	 */
	public TenantsFinanceRecords buildRechargeTenantsFinanceRecords(Map<String, Object> xmlDataMap,String payNo,String inOutNo,Integer tenantId) {
		TenantsFinanceRecords tenantsFinanceRecords = new TenantsFinanceRecords();
		tenantsFinanceRecords.setInOutNo(inOutNo);//交易流水号
		tenantsFinanceRecords.setTenantId(tenantId);//租户id
		tenantsFinanceRecords.setPayNo(payNo);//支付流水号
		tenantsFinanceRecords.setPayType("04");//交易类型 01 订单支付 02成单加价 03成单奖励 04账户充值 05账户提现 06会员续费
		tenantsFinanceRecords.setInOutType("01");//收支类型 01收入 02支出
		BigDecimal total_fee = new BigDecimal((String)xmlDataMap.get("total_fee"));
		BigDecimal danwei = new BigDecimal("0.01");
		tenantsFinanceRecords.setInOutAmount(total_fee.multiply(danwei));//交易金额
		tenantsFinanceRecords.setTransType("01");//交易方式 01线上 02线下
		tenantsFinanceRecords.setAddTime(new Date());//添加时间
		tenantsFinanceRecords.setIsUsable("1"); //1代表可用 0代表逻辑删除
		tenantsFinanceRecords.setStatus("03");//01待处理 02处理中 03已处理
		tenantsFinanceRecords.setRemarks("账户充值");//备注
		tenantsFinanceRecords.setDraweeId(tenantId);//付款方id
		tenantsFinanceRecords.setDraweeType("02");//付款方类型 01客户02家政机构03业务员04家政员05平台
		//tenantsFinanceRecords.setPayeeId(tenantId);//收款方id
		tenantsFinanceRecords.setPayeeType("05");//收款方类型01客户02家政机构03业务员04家政员05平台
		return tenantsFinanceRecords;
	}



	/**
	 * 插入账户充值租户资金变动轨迹表
	 * @param xmlDataMap
	 * @param tradeNo
	 * @param InOutNo
	 * @return
	 */
	public  TenantsTradeRecords bulidRechargeTenantsTradeRecords(Map<String, Object> xmlDataMap,String tradeNo,String InOutNo,Integer tenantId){
		TenantsTradeRecords tenantsTradeRecords=new TenantsTradeRecords();
		tenantsTradeRecords.setTradeNo(tradeNo);//账户流水号
		tenantsTradeRecords.setTenantId(tenantId);//租户id
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			tenantsTradeRecords.setTradeTime(sdf.parse((String)xmlDataMap.get("time_end")));//交易时间
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tenantsTradeRecords.setFinanceType("01"); //财务类型 01收入 02支出
		BigDecimal trade_amount=new BigDecimal((String)xmlDataMap.get("total_fee"));//交易金额
		BigDecimal danwei = new BigDecimal("0.01");
		tenantsTradeRecords.setTradeAmount(trade_amount.multiply(danwei));//换算之后
		tenantsTradeRecords.setStatus("02");//交易状态 01处理中 02交易成功 03交易失败
		tenantsTradeRecords.setInOutNo(InOutNo);//交易流水号
		tenantsTradeRecords.setTradeType("01"); //交易类型  01：充值 02：提现 03:冻结 04:解冻 05：手续费 06：支付定金 07：支付尾款 08成单奖励
		tenantsTradeRecords.setAddTime(new Date());//添加时间
		tenantsTradeRecords.setIsUsable("1");//是否可用 0不可用 1可用
		tenantsTradeRecords.setRemarks("账户充值");//备注
		return tenantsTradeRecords;
	}


}
