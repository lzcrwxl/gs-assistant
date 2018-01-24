package com.fbee.modules.bean.consts;

/**
 * 错误码
 * Created by gaoyan on 29/06/2017.
 */
public enum ErrorCode {

    SYS_ERROR(10001, "不好意思，出错了。请稍后重试。"),

    WX_AUTH_ERROR(20002, "微信授权登陆失败，请重新发起请求"),
    WX_TICKET_ERROR(20003, "微信TICKET获取失败，请重新发起请求"),
    USER_NEED_LOGIN(20004, "该请求需要用户登陆"),
    USER_NOT_FOUND(20005, "用户不存在"),
    WX_OPENID_AUTH_ERROR(20006, "微信TICKET获取失败，请重新发起请求"),
    WX_GET_USER_INFO_ERROR(20007, "微信用户信息获取失败，请重新发起请求"),
    WX_OPENID_ALREADY_BIND_ERROR(20008, "该微信用户已经绑定手机号，不可重复绑定"),
    WX_MOBILE_ALREADY_BIND_ERROR(20009, "该手机号已经绑定微信用户，不可重复绑定"),
    USER_NOT_USABLE(20010,"该用户账号已被冻结"),
    CODE_ERROR(20011,"验证码错误"),
    
    REG_MOBILE_EMPTY(30001, "手机号不能为空"),
    REG_MOBILE_ERROR(30002, "手机号输入有误"),
    REG_CAPTCHA_EMPTY(30003, "验证码不能为空"),
    REG_CAPTCHA_EXPIRED(30004, "验证码已过期"),
    REG_CAPTCHA_ERROR(30005, "验证码输入有误"),
    REG_SMS_EMPTY(30006, "短信验证码不能为空"),
    REG_SMS_ERROR(30007, "短信验证码输入有误"),
    REG_SMS_EXPIRED(30008, "短信验证码已过期"),
    REG_MOBILE_ALREADY_BIND_ERROR(30009, "该手机已绑定微信号"),
    REG_FAILURE(30010, "注册失败，请稍后重试"),

    QUERY_RESULT_EMPTY(40001, "查询结果为空"),
    QUERY_RESULT_ERROR(40002, "查询出错了，请稍后再试"),

    REQUEST_PARAM_EMPTY(50001, "请求参数为空"),

    ORDER_ALREADY_PAID(60001, "该订单已经支付，请确认"),
    ORDER_BUY_FAILURE(60002, "订单支付失败，请稍后重试"),
    ORDER_NOT_EXIST(60003, "订单不存在，请重新输入"),
    ORDER_NOT_OWN(60004, "订单用户与当前用户不匹配"),
    ORDER_CANT_PAY(60005, "当前订单无法支付"),

    COURSE_NOT_FOUND_BY_ID(70001, "查询不到购买的课程，请重试"),

    GATEWAY_TYPE_ERROR(80001, "type参数不正确"),
    RESUME_ERROR(80010,"简历权限限制"),
    
    ADD_COUNT_TOO_MUCH_ERROR(90001, "新增太多了"),


    SUCCESS(0, "SUCCESS");
    private Integer key;
    private String value;

    private ErrorCode(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static ErrorCode get(String key) {
        for (ErrorCode pair : values()) {
            if (pair.key.equals(key)) {
                return pair;
            }
        }
        return null;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
