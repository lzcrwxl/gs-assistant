package com.fbee.modules.wechat.message.model;

/**
 * VGluL8d_EYNdcJ74MxT6C71SPhUc6RreZZfYNosAqM8
 */
public class PayBalanceModel extends MessageModel {

    private Integer tenantUserId;
    private String title;
    private String orderNo;
    private String orderAmount;
    private String description;
    private String remark;

    public Integer getTenantUserId() {
        return tenantUserId;
    }

    public void setTenantUserId(Integer tenantUserId) {
        this.tenantUserId = tenantUserId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
