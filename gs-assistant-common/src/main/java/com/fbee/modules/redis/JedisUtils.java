/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.fbee.modules.redis;

import com.fbee.modules.core.config.Global;
import com.fbee.modules.redis.pool.JedisPool;
import com.fbee.modules.redis.pool.JedisPoolBuilder;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Jedis工具类
 */
public class JedisUtils {

    private static final String OK_CODE = "OK";
    private static final String OK_MULTI_CODE = "+OK";

    private static JedisTemplate jedisTemplate;
    private static JedisTemplate jedisMessage;

    /**
     * 获取jedis对象
     *
     * @return
     */
    public static JedisTemplate getJedisTemplate() {
        if (jedisTemplate != null) {
            return jedisTemplate;
        }
        String host = Global.getConfig("redis.host");
        String port = Global.getConfig("redis.port");
        String db = Global.getConfig("redis.db");
        String passwd = Global.getConfig("redis.pass");

        JedisPoolBuilder builder = new JedisPoolBuilder()
                .setDirectHostAndPort("direct:" + host,port)
                .setDatabase(Integer.valueOf(db))
                .setPoolSize(Integer.valueOf(Global.getConfig("redis.maxActive")))
                .setTimeout(Integer.valueOf(Global.getConfig("redis.maxWait")))
                .setPassword(StringUtils.isBlank(passwd)?null:passwd);
        JedisPool pool = builder.buildPool();
        jedisTemplate = new JedisTemplate(pool);
        return jedisTemplate;
    }

    /**
     * 获取jedis消息对象
     *
     * @return
     */
    public static JedisTemplate getJedisMessage() {
        if (jedisMessage != null) {
            return jedisMessage;
        }
        String host = Global.getConfig("redis.host");
        String port = Global.getConfig("redis.port");
        String db = Global.getConfig("redis.mq.db");
        String passwd = Global.getConfig("redis.pass");

        JedisPoolBuilder builder = new JedisPoolBuilder()
                .setDirectHostAndPort("direct:" + host,port)
                .setDatabase(Integer.valueOf(db))
                .setPoolSize(Integer.valueOf(Global.getConfig("redis.maxActive")))
                .setTimeout(Integer.valueOf(Global.getConfig("redis.maxWait")))
                .setPassword(StringUtils.isBlank(passwd)?null:passwd);
        JedisPool pool = builder.buildPool();
        jedisMessage = new JedisTemplate(pool);
        return jedisMessage;
    }


    /**
     * 判断 返回值是否ok.
     */

    public static boolean isStatusOk(String status) {
        return (status != null) && (OK_CODE.equals(status) || OK_MULTI_CODE.equals(status));
    }

    /**
     * 在Pool以外强行销毁Jedis.
     */
    public static void destroyJedis(Jedis jedis) {
        if ((jedis != null) && jedis.isConnected()) {
            try {
                try {
                    jedis.quit();
                } catch (Exception e) {
                }
                jedis.disconnect();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Ping the jedis instance, return true is the result is PONG.
     */
    public static boolean ping(JedisPool pool) {
        JedisTemplate template = new JedisTemplate(pool);
        try {
            String result = template.execute(new JedisTemplate.JedisAction<String>() {
                @Override
                public String action(Jedis jedis) {
                    return jedis.ping();
                }
            });
            return (result != null) && result.equals("PONG");
        } catch (JedisException e) {
            return false;
        }
    }
}
