package com.fbee.modules.wechat.message.listener;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.PushModel;
import com.fbee.modules.wechat.message.util.OKHttpUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PushWechatListener extends JedisPubSub {

    private final static Logger log = LoggerFactory.getLogger("messageLogger");

    public void push(PushModel data) throws Exception {
        log.info("push message: " + JsonUtils.toJson(data));

        String tokenString = OKHttpUtils.get(WechatConfig.Api.GET_TOKEN.getValue(Constants.APPID, Constants.APPSECRET));
        if (StringUtils.isBlank(tokenString)) {
            return;
        }
        Map<String, Object> tokenEntity = JsonUtils.fromJson(tokenString, Map.class);
        String token = (String) tokenEntity.get("access_token");

        log.info("token:" + token);
        String url = WechatConfig.Api.POST_MESSAGE.getValue(token);
        String content = JsonUtils.toJson(data);
        String result = OKHttpUtils.post(url, content);
        log.info("result: " + result);
    }

}
