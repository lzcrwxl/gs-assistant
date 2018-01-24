package com.fbee.modules.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户订阅信息
 */
public class TenantsUserSubscrbes implements Serializable{

    private Integer id;
    /**
     * 用户id
     */
    private Integer tenantUserId;

    private String userName;
    /**
     * 用户openid
     */
    private String  openid;
    /**
     * 订阅通道
     * e.g. jiacer.job.recommand
     */
    private String  channel;
    /**
     * 主题
     * e.g.  月嫂/育儿嫂/家政服务
     */
    private String  subject;

    /**
     * 推送时间
     * e.g. 8:00 - 18:00
     */
    private String  receiveTime;

    private Date    addTime;

    private String province;
    private String city;
    private String district;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Integer getTenantUserId() {
        return tenantUserId;
    }

    public void setTenantUserId(Integer tenantUserId) {
        this.tenantUserId = tenantUserId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
