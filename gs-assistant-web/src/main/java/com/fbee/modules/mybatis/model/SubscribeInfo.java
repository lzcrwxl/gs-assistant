package com.fbee.modules.mybatis.model;

import com.fbee.modules.wechat.message.config.WechatConfig;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订阅模型
 */
public class SubscribeInfo implements Serializable{

    private Integer id;

    private String openid;

    private Integer tenantUserId;

    private String userName;

    private String receiveTime;

    private String addTime;
    private String channel;
    private String remark;

    /**
     * 是否订阅：1是0否
     */
    private String isOn;

    /**
     * 订阅通道
     */
    private List<String> channels;

    /**
     * 订阅服务
     */
    private List<String> services;

    /**
     * 订阅服务地址
     */
    private List<AddressInfo> addressInfos;

    @JsonIgnore
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIsOn() {
        return isOn;
    }

    public void setIsOn(String isOn) {
        this.isOn = isOn;
    }

    public List<String> getChannels() {
        return channels;
    }

    public List<String> getAllChannels() {
        channels = new ArrayList<String>();
        for(WechatConfig.Channel ch : WechatConfig.Channel.values()){
            channels.add(ch.getChannel());
        }
        return channels;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getTenantUserId() {
        return tenantUserId;
    }

    public void setTenantUserId(Integer tenantUserId) {
        this.tenantUserId = tenantUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<AddressInfo> getAddressInfos() {
        return addressInfos;
    }

    public void setAddressInfos(List<AddressInfo> addressInfos) {
        this.addressInfos = addressInfos;
    }
}
