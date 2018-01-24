package com.fbee.modules.service.impl;

import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.bean.consts.PaymentConst;
import com.fbee.modules.bean.payment.SwiftpassCallbackInfo;
import com.fbee.modules.bean.payment.SwiftpassConfig;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.model.*;
import com.fbee.modules.operation.PayRecordesOpt;
import com.fbee.modules.service.CallbackService;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.utils.prepay.SignUtils;
import com.fbee.modules.utils.prepay.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class CallbackServiceImpl implements CallbackService {

    private static final Logger log = LoggerFactory.getLogger("callbackLogger");

    @Autowired
    TradeRecordsMapper tradeRecordsDao; // 支付总表

    @Autowired
    TenantsFinanceRecordsMapper tenantsFinanceRecordsDao; // 租户交易流水表

    @Autowired
    TenantsFundsMapper tenantsFundsDao; // 租户资金表

    @Autowired
    TenantsTradeRecordsMapper tenantsTradeRecordsDao; // 租户余额表

    @Autowired
    CommonService commonService;

    @Autowired
    TenantsFlowMapper tenantsFlowDao; // 存储租户充值时支付流水和租户id对应关系

    @Autowired
    TenantsAppsMapper tenantsAppsMapper;

    @Autowired
    PayInfoMapper payInfoMapper;

    PayRecordesOpt payRecordesOpt = new PayRecordesOpt();

    private static ConcurrentHashMap<String, AtomicBoolean> process = new ConcurrentHashMap<String, AtomicBoolean>();

    /**
     * 处理业务
     */
    @Override
    public Object handleService(HttpServletRequest req) throws Exception {
        SwiftpassCallbackInfo sp = null;
        log.info("收到通知...");
        try {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("req.setCharacterEncoding error." + e.getMessage());
        }
        String resString = XmlUtils.parseRequst(req);
        //String resString = "<xml><bank_type><![CDATA[CFT]]></bank_type><charset><![CDATA[UTF-8]]></charset><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[102520153627]]></mch_id><nonce_str><![CDATA[1511779083890]]></nonce_str><openid><![CDATA[oMJGHsyCnz0_awlD3PUlyFM-wTHI]]></openid><out_trade_no><![CDATA[062017112700014]]></out_trade_no><out_transaction_id><![CDATA[4200000021201711277442950741]]></out_transaction_id><pay_result><![CDATA[0]]></pay_result><result_code><![CDATA[0]]></result_code><sign><![CDATA[90E3FA4ADE3F02A2C7740A5796364A76]]></sign><sign_type><![CDATA[MD5]]></sign_type><status><![CDATA[0]]></status><sub_appid><![CDATA[wx3ba7ee60b26f2955]]></sub_appid><sub_is_subscribe><![CDATA[Y]]></sub_is_subscribe><sub_openid><![CDATA[ocj2FwTNvLmTYfOMQrtGSuT7NtBE]]></sub_openid><time_end><![CDATA[20171127183803]]></time_end><total_fee><![CDATA[1]]></total_fee><trade_type><![CDATA[pay.weixin.native]]></trade_type><transaction_id><![CDATA[102520153627201711277115686448]]></transaction_id><version><![CDATA[2.0]]></version></xml>";
        log.info("通知内容：" + resString);

        String respString = "error";
        if (resString == null || "".equals(resString)) {
            log.error("返回报文为空");
            return "fail";
        }
        return process(resString);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String process(String resString) throws Exception {
        Map<String, String> map = null;
        try {
            map = XmlUtils.toMap(resString.getBytes(), "utf-8");
        } catch (Exception e) {
            log.error("XmlUtils.toMap error." + e.getMessage());
        }
        String res = JsonUtils.toJson(map);
        SwiftpassCallbackInfo sp = JsonUtils.fromJson(res, SwiftpassCallbackInfo.class);
        if (StringUtils.isBlank(sp.getSign())) {
            log.error("返回报文没有签名信息");
            return "fail";
        }
        if (!sp.getSignType().equalsIgnoreCase("fix") && !SignUtils.checkParam(map, SwiftpassConfig.key)) {
            log.error("验证签名不通过");
            return "fail";
        }
        String status = map.get("status");
        if (status == null || !"0".equals(status)) {
            log.error(String.format("响应码错误[%s]", status));
            return "fail";
        }
        String result_code = map.get("result_code");
        if (result_code == null || !"0".equals(result_code)) {
            log.error(String.format("返回代码[%s]", result_code));
            return "fail";
        }
        sp.setResultXml(resString);

        if (!process.containsKey(sp.getOutTradeNo())) {
            process.put(sp.getOutTradeNo(), new AtomicBoolean());
        }

        try {
            //此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。
            //如果是false，则准备处理。true表示正在处理，跳过。
            if (process.get(sp.getOutTradeNo()).compareAndSet(false, true)) {
                log.info(String.format("====== callback process begin, pay_no=[%s]", sp.getOutTradeNo()));

                Map<String, Object> xmlDataMap = JsonUtils.fromJson(JsonUtils.toJson(sp), Map.class);
                // 业务结果
                String payresult = sp.getPayResult();
                String flowNo = sp.getOutTradeNo();

                PayInfo payInfo = payInfoMapper.getById(flowNo);

                if (payInfo == null) {
                    log.info(String.format("[%s]没有找到该条支付信息[payInfo] : %s ", "payinfo not exist", flowNo));
                    process.remove(sp.getOutTradeNo());
                    return "fail";
                }
                if (StringUtils.isNotBlank(payInfo.getCallbackInfo())) {
                    log.info(String.format("[%s]该支付请求已经处理，不可重复处理。[%s]", "duplication callback, it already processed", flowNo));
                    process.remove(sp.getOutTradeNo());
                    return "fail";
                }
                log.info(String.format("修改订单及支付单状态,order_no[%s],pay_No[%s]", payInfo.getOrderNo(), payInfo.getPayNo()));
                payInfo.setPayNo(flowNo);
                payInfo.setPayStatus(PaymentConst.PayStatus.PAID.getCode());
                payInfo.setCallbackInfo(sp.getResultXml());
                payInfo.setCallbackTime(new Date());
                payInfo.setResultCode(sp.getResultCode());
                payInfo.setErrCode(sp.getErrCode());
                payInfo.setErrMsg(sp.getErrMsg());
                payInfoMapper.update(payInfo);

                log.info(String.format("更新支付流水状态 [ tenants_flow ] = [ %s ]", flowNo));
                TenantsFlow payresultRecord = new TenantsFlow();
                payresultRecord.setTradeFlowNo(flowNo);
                payresultRecord.setPayresult(payresult);
                tenantsFlowDao.updateByPrimaryKeySelective(payresultRecord);// 标记支付结果,用于界面上判断是否支付成功

                TenantsFlow tenantsFlow = tenantsFlowDao.selectByPrimaryKey(flowNo);// 对应关系
                if (payresult.equals("0")) {
                    //支付成功
                    try {
                        String result = recharge(sp, tenantsFlow, xmlDataMap);
                        //7. 处理完成，返回成功
                        log.info(String.format("支付回调处理完成，pay_no [%s], result [%s]", sp.getOutTradeNo(), result));
                        process.remove(sp.getOutTradeNo());
                        return result;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else { // 支付失败
                    log.info(String.format("支付回调>业务结果失败. pay_no [%s]", flowNo));
                    // 充值时失败
                    TradeRecords tradeRecords = payRecordesOpt.buildFailTradeRecords(xmlDataMap, flowNo);
                    tradeRecordsDao.insertSelective(tradeRecords);// 插入支付总表交易记录
                    log.info("fail: insert selective[recharge]");
                }
            }
        } catch (Exception e) {
            log.info(String.format("exception : ", e.getMessage()));
            process.remove(sp.getOutTradeNo());
            throw e;
        }
        log.info("======== call back finished =======");
        return "fail";
    }


    public String recharge(SwiftpassCallbackInfo sp, TenantsFlow tenantsFlow, Map<String, Object> xmlDataMap) throws ParseException {

        String flowNo = tenantsFlow.getTradeFlowNo();

        Integer tenantId = tenantsFlow.getTenantId();// 租户id
        TradeRecords tradeRecords = payRecordesOpt.buildRechargeTradeRecords(xmlDataMap, flowNo);
        tradeRecordsDao.insertSelective(tradeRecords);// 插入支付总表交易记录

        String inOutNo = commonService.createOrderNo(com.fbee.modules.bean.consts.Constants.CW);//财务流水号08
        TenantsFinanceRecords tenantsFinanceRecords = payRecordesOpt.buildRechargeTenantsFinanceRecords(xmlDataMap, flowNo, inOutNo, tenantId);
        //tenantsFinanceRecords.setInOutObject(inoutobject_userName);

        //充值，插入收支对象  业务员
        TenantsApps tenantApp = tenantsAppsMapper.selectByPrimaryKey(tenantId);
        tenantsFinanceRecords.setInOutObject(tenantApp.getWebsiteName());

        tenantsFinanceRecordsDao.insertSelective(tenantsFinanceRecords);// 插入租户交易总表

        String tradeNo = commonService.createOrderNo(Constants.ZH);// 生成账户流水号 07
        TenantsTradeRecords tenantsTradeRecords = payRecordesOpt.bulidRechargeTenantsTradeRecords(xmlDataMap, tradeNo, inOutNo, tenantId);
        tenantsTradeRecordsDao.insertSelective(tenantsTradeRecords);// 插入租户资金变动轨迹表

        BigDecimal total_fee = new BigDecimal((String) xmlDataMap.get("total_fee"));
        BigDecimal danwei = new BigDecimal("0.01");
        dealAccount(tenantId, "01", "01", "02", total_fee.multiply(danwei), "0", "账户充值");// 更新租户账户资金总表(账户充值)
        return "success";
    }


    /**
     * 更新租户账户资金表
     *
     * @param tenantId
     * @param payType
     * @param bussType
     * @param state
     * @param money
     * @return
     */
    public JsonResult dealAccount(Integer tenantId, String payType, String bussType, String state, BigDecimal money, String orderNo, String remarks) {
        try {
            log.info(String.format("dealAccount: 查询租户租金 tenants_funds, tenantId:[%s], payType[%s], bussType[%s], state[%s], money[%s]", tenantId, payType, bussType, state, money));
            // 查询表中是否有该租户资金
            TenantsFunds tenantsFunds = tenantsFundsDao.selectByPrimaryKey(tenantId);
            if (payType.equals("01")) {// 收入
                log.info(String.format("租户[%s]: payType[01]收入", tenantId));
                if (bussType.equals("04")) {// 解冻
                    log.info(String.format("租户[%s]: payType[01]收入 > bussType[04]解冻", tenantId));
                    log.info(String.format("租户[%s] > [解冻]: 冻结[%s]-， 可用[%s]+ [%s]", tenantId, tenantsFunds.getFrozenAmount(), tenantsFunds.getAvailableAmount(), money));
                    // 冻结-
                    tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().subtract(money));
                    // 可用+
                    tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().add(money));
                    tenantsFunds.setTenantId(tenantId);
                    tenantsFunds.setModifyTime(new Date());
                    tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                } else {// 其他收入
                    log.info(String.format("租户[%s]: payType[01]收入 --> [%s]其他收入", tenantId, bussType));
                    if (state.equals("01")) {// 处理中
                        log.info(String.format("租户[%s] > state[01]处理中: 冻结[%s]+， 总额[%s]+ [%s]", tenantId, tenantsFunds.getFrozenAmount(), tenantsFunds.getTotalAmount(), money));
                        // 冻结+
                        tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().add(money));
                        // 租户总额+
                        tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().add(money));
                        tenantsFunds.setTenantId(tenantId);
                        tenantsFunds.setModifyTime(new Date());
                        tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                    }
                    if (state.equals("02")) {// 已处理
                        log.info(String.format("租户[%s] > state[03]已处理: 可用[%s]+， 总额[%s]+ [%s]", tenantId, tenantsFunds.getAvailableAmount(), tenantsFunds.getTotalAmount(), money));
                        // 可用+
                        tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().add(money));
                        // 租户总额+
                        if(tenantsFunds.getTotalAmount() != null){
                        	tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().add(money));
                        }else{
                        	tenantsFunds.setTotalAmount(money);
                        }
                        tenantsFunds.setTenantId(tenantId);
                        tenantsFunds.setModifyTime(new Date());
                        tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                    }
                }
            }
            if (payType.equals("02")) {// 支出
                log.info(String.format("租户[%s]: payType[02]支出", tenantId));
                if (bussType.equals("03")) {// 冻结
                    log.info(String.format("租户[%s]: payType[01]收入 > bussType[03]解冻", tenantId));
                    log.info(String.format("租户[%s] > [解冻]: 冻结[%s]+， 可用[%s]- [%s]", tenantId, tenantsFunds.getFrozenAmount(), tenantsFunds.getAvailableAmount(), money));
                    // 冻结+
                    tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().add(money));
                    // 可用-
                    tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().subtract(money));
                    tenantsFunds.setTenantId(tenantId);
                    tenantsFunds.setModifyTime(new Date());
                    tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                } else {// 其他收支出
                    log.info(String.format("租户[%s]: payType[02]支出 --> [%s]其他支出", tenantId, bussType));
                    if (state.equals("01")) {// 处理中
                        // 余额不足
                        if (tenantsFunds.getAvailableAmount().compareTo(money) == -1) {
                            log.info(String.format("租户[%s]: 余额不足[%s]", tenantId, tenantsFunds.getAvailableAmount()));
                            return JsonResult.failure(ResultCode.Funds.AVAILABLE_AMOUNT_NOT_ENOUGH);
                        }
                        log.info(String.format("租户[%s] > state[01]处理中: 冻结[%s]-， 总额[%s]- [%s]", tenantId, tenantsFunds.getFrozenAmount(), tenantsFunds.getTotalAmount(), money));
                        // 冻结-
                        tenantsFunds.setFrozenAmount(tenantsFunds.getFrozenAmount().subtract(money));
                        // 租户总额-
                        tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().subtract(money));
                        tenantsFunds.setTenantId(tenantId);
                        tenantsFunds.setModifyTime(new Date());
                        tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                    }
                    if (state.equals("02")) {// 已处理
                        // 余额不足
                        if (tenantsFunds.getAvailableAmount().compareTo(money) == -1) {
                            log.info(String.format("租户[%s]: 余额不足[%s]", tenantId, tenantsFunds.getAvailableAmount()));
                            return JsonResult.failure(ResultCode.Funds.AVAILABLE_AMOUNT_NOT_ENOUGH);
                        }
                        log.info(String.format("租户[%s] > state[02]已处理: 可用[%s]-， 总额[%s]- [%s]", tenantId, tenantsFunds.getAvailableAmount(), tenantsFunds.getTotalAmount(), money));
                        // 可用-
                        tenantsFunds.setAvailableAmount(tenantsFunds.getAvailableAmount().subtract(money));
                        // 租户总额-
                        tenantsFunds.setTotalAmount(tenantsFunds.getTotalAmount().subtract(money));
                        tenantsFunds.setTenantId(tenantId);
                        tenantsFunds.setModifyTime(new Date());
                        tenantsFundsDao.updateByPrimaryKeySelective(tenantsFunds);
                    }
                }
            }
            return JsonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ErrorMsg.ORDERS_QUERY_ERROR, e);
            return JsonResult.failure(ResultCode.DATA_ERROR);
        }
    }


}
