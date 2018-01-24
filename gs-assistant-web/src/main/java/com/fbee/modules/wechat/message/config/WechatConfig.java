package com.fbee.modules.wechat.message.config;

import com.fbee.modules.bean.consts.Constants;

import java.util.ArrayList;
import java.util.List;

public interface WechatConfig {

    enum Channel {
        JOB_PUBLISH("jiacer.channel.job.publish",  "职位发布"),
        JOB_MS_PUBLISH("jiacedu.channel.job.publish",  "职位发布-微课堂提醒"),

        RESUME_APPLY("jiacer.channel.resume.apply",  "简历投递"),
        RESUME_CHECK("jiacer.channel.resume.check",  "简历审核"),
        RESERVE_ORDER("jiacer.channel.reserve.order", "预约新订单"),

        NEW_PAY_ORDER("jiacer.channel.new.pay.order", "新支付订单"),
        ORDER_PAY_DEPOSIT("jiacer.channel.order.pay.deposit", "订单支付定金"),
        ORDER_WAIT_PAY_BALANCE("jiacer.channel.order.wait.pay.balance", "订单待支付尾款"),

        ORDER_PAY_BALANCE("jiacer.channel.order.pay.balance",  "订单支付尾款"),

        FINISH_JOB("jiacer.channel.finish.job",  "合作抢单完成"),
        CANCEL_JOB("jiacer.channel.cancel.job",  "合作抢单取消"),

        CANCEL_ORDER("jiacer.channel.cancel.order", "取消订单");

        private String channel;
        private String description;

        Channel(String channel, String description) {
            this.channel = channel;
            this.description = description;
        }

        public String getChannel() {
            return channel;
        }

    }
    enum Queue {
        JOB_PUBLISH_B( "jiacer.queue.b.job.recommend", "职位发布"),

        RESUME_APPLY_B( "jiacer.queue.b.resume.apply", "简历投递"),
        RESUME_CHECK_B( "jiacer.queue.b.resume.check", "简历审核"),
        RESERVE_ORDER_B( "jiacer.queue.b.reserve.order", "预约新订单"),

        NEW_PAY_ORDER_C( "jiacer.queue.c.new.pay.order", "创建新订单"),
        
        ORDER_PAY_DEPOSIT_B( "jiacer.queue.b.order.pay.deposit", "订单支付定金"),
        ORDER_PAY_DEPOSIT_C( "jiacer.queue.c.order.pay.deposit", "订单支付定金"),

        ORDER_PAY_BALANCE_B("jiacer.queue.b.order.pay.balance", "订单支付尾款"),
        ORDER_PAY_BALANCE_C( "jiacer.queue.c.order.pay.balance", "订单支付尾款"),

        CANCEL_ORDER_B( "jiacer.queue.b.cancel.order", "取消订单"),
        CANCEL_ORDER_C( "jiacer.queue.c.cancel.order", "取消订单");

        private String queue;
        private String description;

        Queue( String queue, String description) {
            this.queue = queue;
            this.description = description;
        }

        public String getQueue() {
            return queue;
        }

    }
    enum Api {
        GET_TOKEN("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s"),
        POST_MESSAGE("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s"),
        POST_INDUSTRY("https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=%s");

        private String value;

        Api(String value) {
            this.value = value;
        }

        public String getValue(String... param) {
            return String.format(value, param);
        }
    }

    enum Template{
        //prod
        NEW_JOB_TEMPLATE("arQA_ktRb7qz6-FCVzOfZal85waCwjwapDDnKQWuCj8",Constants.MOBILE_HOST_NAME+"/#/job/jobRelease/%s",  "职位发布提醒"),
        NEW_RESUME_TEMPLATE("-pp5mrTw-u8wTvG6gHUSJS5FOGenXQFH6qhJuc66_7U",Constants.MOBILE_HOST_NAME+"/",  "收到新简历提醒"),
        CHECK_RESUME_TEMPLATE("YEJ6zYFloJcpsVnQH1YjDtlUJbAUsrdFn3-U9voZNxU",Constants.MOBILE_HOST_NAME+"/",  "简历审核结果提醒"),
        NEW_RESERVE_ORDER_TEMPLATE("Rw1P0P0Yg9LqDhXOVcRGT277M4Ctairs-FYphSVvAMA",Constants.MOBILE_HOST_NAME+"/",  "新的预约订单提醒"),
        ORDER_PAY_DEPOSIT_TEMPLATE("7OCIU70ic7J7aYpxFSH0tJRb-CcHlWZRCqEhdEfyf9E",Constants.MOBILE_HOST_NAME+"/",  "客户支付定金完成提醒"),
        ORDER_PAY_BALANCE_TEMPLATE("VGluL8d_EYNdcJ74MxT6C71SPhUc6RreZZfYNosAqM8",Constants.MOBILE_HOST_NAME+"/",  "客户支付尾款完成提醒"),
        BUISNESS_TEMPLATE("YEJ6zYFloJcpsVnQH1YjDtlUJbAUsrdFn3-U9voZNxU",Constants.MOBILE_HOST_NAME+"/",  "业务模版：合作抢单完成/取消， 订单取消");


        //test
//        NEW_JOB_TEMPLATE("h3rc3MCi0UFHyWFfHCMG153SHIKF882gQg7rapMOW5s",Constants.MOBILE_HOST_NAME+"/#/job/jobRelease/%s",  "职位发布提醒"),
//        NEW_RESUME_TEMPLATE("eIMVOBPVsIuQpQvz2JW4ms_eqeCDTuTLei43_8-wzto",Constants.MOBILE_HOST_NAME+"/",  "收到新简历提醒"),
//        CHECK_RESUME_TEMPLATE("FLp96OWxwbmPXWp6gv_2Ij4EY6CyilWInmzuWhOLNxg",Constants.MOBILE_HOST_NAME+"/",  "简历审核结果提醒"),
//        NEW_RESERVE_ORDER_TEMPLATE("XQcHV_8t0Z1X1EV31-WjJT5pTyhw2cj5C3NmUfMRaQk",Constants.MOBILE_HOST_NAME+"/",  "新的预约订单提醒"),
//        ORDER_PAY_DEPOSIT_TEMPLATE("gY5PFR8oLF3wWOop0j6QEHZo3yr6BeNd-2nqOKMgNPM",Constants.MOBILE_HOST_NAME+"/",  "客户支付定金完成提醒"),
//        ORDER_PAY_BALANCE_TEMPLATE("yBmujHXovb3mUzRIGTGSVX2pi19FgPlV0RgJdRxT24o",Constants.MOBILE_HOST_NAME+"/",  "客户支付尾款完成提醒"),
//        BUISNESS_TEMPLATE("FLp96OWxwbmPXWp6gv_2Ij4EY6CyilWInmzuWhOLNxg",Constants.MOBILE_HOST_NAME+"/",  "业务模版：合作抢单完成/取消， 订单取消");
    	
    	
    	//uat
//    	NEW_JOB_TEMPLATE("WF2EX4E075rWpo2quHcpY_AmCU_J6OQ-7YM6mq8Q2oU",Constants.MOBILE_HOST_NAME+"/#/job/jobRelease/%s",  "职位发布提醒"),
//        NEW_RESUME_TEMPLATE("EICeGyxb8SW7Fgijvq27CZNBmOcQH-M2sC7i5y9nTKA",Constants.MOBILE_HOST_NAME+"/",  "收到新简历提醒"),
//        CHECK_RESUME_TEMPLATE("D0Vg191XCOrV4P4O_3Y4eIOESv7sURmKrrKFgz2gQ1k",Constants.MOBILE_HOST_NAME+"/",  "简历审核结果提醒"),
//        NEW_RESERVE_ORDER_TEMPLATE("DwIQKmj8ztOd0f7AHAygaYsNlnFYapWEbxm25U7-ZHs",Constants.MOBILE_HOST_NAME+"/",  "新的预约订单提醒"),
//        ORDER_PAY_DEPOSIT_TEMPLATE("I1R44TjbuMv0_9eckq99NXluVeEnvxil1bb1RQp5nxU",Constants.MOBILE_HOST_NAME+"/",  "客户支付定金完成提醒"),
//        ORDER_PAY_BALANCE_TEMPLATE("ULByHTH_W-G6JFPXMNUJGuU9ayTU-cKJx2OpORQQQ3o",Constants.MOBILE_HOST_NAME+"/",  "客户支付尾款完成提醒"),
//        BUISNESS_TEMPLATE("D0Vg191XCOrV4P4O_3Y4eIOESv7sURmKrrKFgz2gQ1k",Constants.MOBILE_HOST_NAME+"/",  "业务模版：合作抢单完成/取消， 订单取消");

        private String id;
        private String url;
        private String description;

        Template(String id, String url, String description) {
            this.id = id;
            this.url = url;
            this.description = description;
        }

        public String getId() {
            return id;
        }
        public String getUrl(String ... param) {
            return String.format(url, param);
        }
    }

}
