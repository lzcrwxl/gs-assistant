package com.fbee.modules.wechat.message;

import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.listener.*;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class MessageApplication implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(MessageApplication.class);

    private JedisTemplate redis = JedisUtils.getJedisTemplate();

    @Autowired
    private NewJobListener newJobListener;
    @Autowired
    private NewResumeListener newResumeListener;
    @Autowired
    private CheckResumeListener checkResumeListener;
    @Autowired
    private CancelOrderListener cancelOrderDepositListener;
    @Autowired
    private JobFinishListener jobFinishListener;
    @Autowired
    private JobCancelListener jobCancelListener;
    @Autowired
    private NewReseverOrderListener newReseverOrderListener;
    @Autowired
    private PayBalanceListener payBalanceListener;
    @Autowired
    private PayDepositListener payDepositListener;


    private ExecutorService exePools = new ThreadPoolExecutor(10, 10, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(),
            new ThreadFactoryBuilder().setNameFormat("thread-message-%d").build(), new ThreadPoolExecutor.AbortPolicy());

    /**
     * 监听广播
     */
    public void onListener() {

        log.info("message listener start ..");

        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener new job subscribe ..");
                redis.subscribe(newJobListener, WechatConfig.Channel.JOB_PUBLISH.getChannel());
            }
        });
        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener new resume subscribe ..");
                redis.subscribe(newResumeListener, WechatConfig.Channel.RESUME_APPLY.getChannel());
            }
        });
        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener check resume subscribe ..");
                redis.subscribe(checkResumeListener, WechatConfig.Channel.RESUME_CHECK.getChannel());
            }
        });
        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener cancel order with deposit subscribe ..");
                redis.subscribe(cancelOrderDepositListener, WechatConfig.Channel.CANCEL_ORDER.getChannel());
            }
        });
        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener finish job subscribe ..");
                redis.subscribe(jobFinishListener, WechatConfig.Channel.FINISH_JOB.getChannel());
            }
        });
        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener finish job subscribe ..");
                redis.subscribe(jobCancelListener, WechatConfig.Channel.CANCEL_JOB.getChannel());
            }
        });
        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener new resever order subscribe ..");
                redis.subscribe(newReseverOrderListener, WechatConfig.Channel.RESERVE_ORDER.getChannel());
            }
        });

        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener pay balance subscribe ..");
                redis.subscribe(payBalanceListener, WechatConfig.Channel.ORDER_PAY_BALANCE.getChannel());
            }
        });

        exePools.execute(new Runnable() {
            @Override
            public void run() {
                log.info("listener pay deposit subscribe ..");
                redis.subscribe(payDepositListener, WechatConfig.Channel.ORDER_PAY_DEPOSIT.getChannel());
            }
        });

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        onListener();
    }
}
