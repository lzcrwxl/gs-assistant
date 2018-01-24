package com.fbee.modules.wechat.message.model;

/**
 * nbnOIMwxInSANwvT4FA6wGUVAOXWJZAXcmwULZVWSGE
 */
public class CancelOrderModel extends MessageModel{

    private String title;
    private String type;
    private String address;
    private String name;
    private String cancelApplyDate;
    private String remark;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCancelApplyDate() {
        return cancelApplyDate;
    }

    public void setCancelApplyDate(String cancelApplyDate) {
        this.cancelApplyDate = cancelApplyDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
