package com.fbee.modules.service;

import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.service.basic.ServiceException;

import java.math.BigDecimal;

/**
 * 公共业务层类
 *
 * @author ZhangJie
 */
public interface CommonService {

    /**
     * 根据订单的类型创建订单号<br>
     * 订单号规则为<br>
     * 01,02,03,04代表订单类型目前就这几种类型<br>
     * 加年月日后加5位-->
     *
     * @param orderType
     * @return
     */
    String createOrderNo(String orderType);

    /**
     * 冻结保证金
     *
     * @return
     */
    void frozenAmount(Integer tenantId, BigDecimal money, String orderNo, String remarks) throws ServiceException;

    /**
     * 解冻保证金
     *
     * @return
     */
    void thawAmount(Integer tenantId, BigDecimal money, String orderNo, String remarks) throws ServiceException;

    /**
     * 生成二维码
     */
    String getQRCode(String info, String code, String domain, String loginAccount);

	String getResumeDetailQRCode(String stafIds,String orderNo,String resumeId);
	
    JsonResult refreshSession();

}
