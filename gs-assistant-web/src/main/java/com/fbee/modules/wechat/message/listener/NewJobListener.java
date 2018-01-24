package com.fbee.modules.wechat.message.listener;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.mybatis.dao.TenantsJobsMapper;
import com.fbee.modules.mybatis.dao.TenantsUserSubscribeMapper;
import com.fbee.modules.mybatis.model.Dictionarys;
import com.fbee.modules.mybatis.model.TenantsJobs;
import com.fbee.modules.mybatis.model.TenantsUserSubscrbes;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.JobMessageModel;
import com.fbee.modules.wechat.message.model.PushModel;
import com.fbee.modules.wechat.message.util.OKHttpUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewJobListener extends PushWechatListener {

    private final static Logger log = LoggerFactory.getLogger("messageLogger");

    @Autowired
    private TenantsJobsMapper tenantsJobsMapper;

    @Autowired
    private TenantsUserSubscribeMapper tenantsUserSubscribeMapper;

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    /**
     * 需求（职位）发布
     * 1. 门店助手用户
     * 该用户设置了该工种提醒
     * 2. 家策健康学院公众号
     *
     * @param channel
     */
    @Override
    public void onMessage(String channel, String message) {
        log.info(String.format("[Listener] new job: received messageKey [%s] on channel [%s]", message, channel));
        try {
            //等待业务处理事务提交
            Thread.sleep(1000);
            //String message = mq.brpop(60,WechatConfig.Queue.JOB_PUBLISH_B.getQueue());
            log.info(String.format("get message [%s] from queue [%s] ", message, channel));
            JobMessageModel job = JsonUtils.fromJson(message, JobMessageModel.class);
            if (StringUtils.isBlank(message) || job == null) {
                return;
            }
            TenantsUserSubscrbes ts = new TenantsUserSubscrbes();
            ts.setChannel(WechatConfig.Channel.JOB_PUBLISH.getChannel());
            ts.setSubject(job.getServiceType());
            ts.setProvince(job.getProvince());
            ts.setCity(job.getCity());
            ts.setDistrict(job.getDistrict());
            ts.setSubject(job.getServiceType());
            List<TenantsUserSubscrbes> lists = tenantsUserSubscribeMapper.getJobSubscribeBySubject(ts);
            for (TenantsUserSubscrbes sub : lists) {
                job.setOpenid(sub.getOpenid());
                PushModel data = new PushModel();
                data.setTouser(job.getOpenid());
                data.setTemplateId(WechatConfig.Template.NEW_JOB_TEMPLATE.getId());
                data.setUrl(Constants.MOBILE_HOST_NAME + "/#/grap/grapdetail/" + job.getJobId() + "/6");
                data.addData("first", job.getTitle() + "\n", null)
                        .addData("keyword1", job.getServiceDate(), "#173177")
                        .addData("keyword2", job.getAddress(), "#173177")
                        .addData("keyword3", job.getSalary(), "#173177")
                        .addData("keyword4", job.getDescription(), "#173177");

                push(data);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}
