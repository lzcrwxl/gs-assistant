package com.fbee.modules.wechat.message.model;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * {
         "touser":"oC1aRuJjNESIs75g8o5DO2S1vvnQ",
         "template_id":"VMcz03RnwsXudlmbeuOSDtdaGErA7Dm1eyNY4-s1Pa8",
         "url":"http://home.jiacer.com/ttjz/jzfwxq.html?staffId=52",
         "data":{
             "url": {
                 "value":"[预约]",
                 "color":"#173177"
             }
         }
     }
 */
public class PushModel implements Serializable{

    private String touser;
    private String templateId;
    private String url;
    private Map<String,Param> data = new HashMap<String, Param>();


    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Param> getData() {
        return data;
    }
    public PushModel addData(String name, String value, String color) {
        Map<String, Param> pam = Maps.newHashMap();
        pam.put(name, new Param(value, null));
        this.data.putAll(pam);
        return this;
    }
    public PushModel addData(String name, String value) {
        Map<String, Param> pam = Maps.newHashMap();
        pam.put(name, new Param(value, null));
        this.data.putAll(pam);
        return this;
    }
}
