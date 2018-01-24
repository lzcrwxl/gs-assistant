package com.fbee.modules.wechat.message.model;

import java.io.Serializable;

public class MessageModel implements Serializable{

    /**
     * openid
     */
    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
