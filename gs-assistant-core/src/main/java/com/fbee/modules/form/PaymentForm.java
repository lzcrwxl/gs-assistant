package com.fbee.modules.form;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付相关参数
 */
public class PaymentForm implements Serializable{

    private String      orderNo;
    private BigDecimal  amount;
    private String      remarks;
    
    /**
     * 支付宝/微信
     */
    private String      payType;

    /**
     * 充值/支付
     */
    private String      whatFor;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getWhatFor() {
        return whatFor;
    }

    public void setWhatFor(String whatFor) {
        this.whatFor = whatFor;
    }

}
