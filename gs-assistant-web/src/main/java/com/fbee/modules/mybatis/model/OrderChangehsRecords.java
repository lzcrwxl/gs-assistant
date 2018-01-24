package com.fbee.modules.mybatis.model;
import java.io.Serializable;
import java.util.Date;

public class OrderChangehsRecords implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String orderNo;
    private String hsRemark;
    private Date hsApplicationTime;
    private Date hsHandlingTime;
    private Integer mxId;

    public Integer getMxId() {
        return mxId;
    }

    public void setMxId(Integer mxId) {
        this.mxId = mxId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getHsRemark() {
        return hsRemark;
    }

    public void setHsRemark(String hsRemark) {
        this.hsRemark = hsRemark;
    }

    public Date getHsApplicationTime() {
        return hsApplicationTime;
    }

    public void setHsApplicationTime(Date hsApplicationTime) {
        this.hsApplicationTime = hsApplicationTime;
    }

    public Date getHsHandlingTime() {
        return hsHandlingTime;
    }

    public void setHsHandlingTime(Date hsHandlingTime) {
        this.hsHandlingTime = hsHandlingTime;
    }
}