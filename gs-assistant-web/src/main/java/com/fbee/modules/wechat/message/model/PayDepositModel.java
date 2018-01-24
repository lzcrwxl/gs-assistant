package com.fbee.modules.wechat.message.model;

/**
 * 7OCIU70ic7J7aYpxFSH0tJRb-CcHlWZRCqEhdEfyf9E
 */
public class PayDepositModel extends MessageModel {

    private Integer tenantUserId;
    private String title;
    private String orderNo;
    private String orderAmount;
    private String remark;

    public Integer getTenantUserId() {
        return tenantUserId;
    }

    public void setTenantUserId(Integer tenantUserId) {
        this.tenantUserId = tenantUserId;
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
