package com.fbee.modules.wechat.message.listener;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.mybatis.dao.TenantsUserSubscribeMapper;
import com.fbee.modules.mybatis.dao.TenantsUsersMapper;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;
import com.fbee.modules.mybatis.model.TenantsUserSubscrbes;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.BusinessModel;
import com.fbee.modules.wechat.message.model.PushModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckResumeListener extends PushWechatListener {

    private final static Logger log = LoggerFactory.getLogger("messageLogger");

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    @Autowired
    private TenantsUserSubscribeMapper tenantsUserSubscribeMapper;
    @Autowired
    private TenantsUsersMapper tenantsUsersMapper;

    /**
     * 审核简历提醒
     *
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(String channel, String message) {
        log.info(String.format("[Listener] check resume: received messageKey [%s] on channel [%s]", message, channel));
        if (StringUtils.isBlank(message)) {
            return;
        }
        try {
            //等待业务处理事务提交
            Thread.sleep(1000);
            BusinessModel model = JsonUtils.fromJson(message, BusinessModel.class);
            if (StringUtils.isBlank(message) || model == null) {
                return;
            }
            TenantsUsersEntity user = tenantsUsersMapper.getById(model.getUserId());
            PushModel data = new PushModel();
            data.setTouser(user.getOpenId());
            data.setTemplateId(WechatConfig.Template.CHECK_RESUME_TEMPLATE.getId());
            data.setUrl(Constants.MOBILE_HOST_NAME + "/#/grap/grapdetail/" + model.getResumeId() + "/" + model.getStaffId());
            data.addData("first", model.getTitle() + "\n", null)
                    .addData("keyword1", model.getKeywordFirst(), "#173177")
                    .addData("keyword2", model.getKeywordSecond(), "#173177")
                    .addData("keyword3", model.getKeywordThird(), "#173177");
            if (StringUtils.isNotBlank(model.getRemark())) {
                data.addData("remark", "\n" + model.getRemark(), null);
            }
            push(data);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
