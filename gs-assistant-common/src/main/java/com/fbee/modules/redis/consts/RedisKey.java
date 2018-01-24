package com.fbee.modules.redis.consts;


public interface RedisKey {

    enum User{
        UA("assistant.ua.user.%s", "用户授权标识，存储用户信息"),
        UA_QR("assistant.ua.qr.%s", "用户授权二维码标识，存储用户信息"),
        UA_WECHAT("assistant.ua.wechat.%s", "微信授权标识，存储微信信息"),
        UA_TOKEN("assistant.ua.token.%s", "微信用户授权标识，存储用户token"),



        CAPTCHA("ua.captcha.%s", "用户登陆验证码"),
    	LOGINSMSCODE("ua.loginsmscode.%s", "用户登陆短信验证码");

        private String key;
        private String desc;

        private User(String key, String desc) {
            this.key = key;
            this.desc = desc;
        }

        public static User get(String key) {
            for (User pair : values()) {
                if (pair.key.equals(key)) {
                    return pair;
                }
            }
            return null;
        }

        public String getKey(String placeholder) {
            return String.format(key, placeholder);
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}