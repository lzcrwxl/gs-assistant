package com.fbee.modules.jsonData.basic;

import com.fbee.modules.bean.consts.ErrorCode;
import com.fbee.modules.core.persistence.ModelSerializable;

/**
 * 封装返回数据
 * @author hzp
 *
 */
public class JsonResult implements ModelSerializable{

    private static final long serialVersionUID = -3941817907260975651L;
    private boolean success;
    private Integer code;
    private String msg;
    private String toUrl;
    private Object jsonData;

    public JsonResult() {
    }

    public JsonResult(boolean success,Integer code, String msg, Object data) {
    	this.code=code;
        this.success = success;
        this.msg = msg;
        this.jsonData = data;
    }
    
    public JsonResult(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.jsonData = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getJsonData() {
        return jsonData;
    }

    public void setJsonData(Object jsonData) {
        this.jsonData = jsonData;
    }

    public String getToUrl() {
        return toUrl;
    }

    public void setToUrl(String toUrl) {
        this.toUrl = toUrl;
    }
    
    public static JsonResult success(Object jsonData) {
    	return new JsonResult(true,ResultCode.SUCCESS, ResultCode.getMsg(ResultCode.SUCCESS), jsonData);
    }

    public static JsonResult failure(Integer code) {
        return new JsonResult(false,code, ResultCode.getMsg(code), null);
    }
    public static JsonResult failure(Integer code, Object jsonData) {
        return new JsonResult(false,code, ResultCode.getMsg(code), jsonData);
    }
    public static JsonResult failure(ErrorCode ec) {
        return new JsonResult(false,ec.getKey(), ec.getValue(), null);
    }
}
