package com.fbee.modules.bean.payment;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * <一句话功能简述>
 * <功能详细描述>配置信息
 * 
 * @author  Administrator
 * @version  [版本号, 2014-8-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class SwiftpassConfig implements InitializingBean {
    
    /**
     * 威富通交易密钥
     */
    public static String key ;
    
    /**
     * 威富通商户号
     */
    public static String mch_id;
    
    /**
     * 威富通请求url
     */
    public static String req_url;
    
    /**
     * 通知url
     */
    public static String notify_url;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Properties prop = new Properties();
        InputStream in = SwiftpassConfig.class.getResourceAsStream("/env.properties");
        try {
            prop.load(in);
            key = prop.getProperty("swiftpass.key").trim();
            mch_id = prop.getProperty("swiftpass.mch_id").trim();
            req_url = prop.getProperty("swiftpass.req_url").trim();
            notify_url = prop.getProperty("swiftpass.notify_url").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
