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
import com.fbee.modules.wechat.message.model.NewOrderModel;
import com.fbee.modules.wechat.message.model.PushModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewReseverOrderListener extends PushWechatListener {

    private final static Logger log = LoggerFactory.getLogger("messageLogger");

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    @Autowired
    private TenantsUserSubscribeMapper tenantsUserSubscribeMapper;

    @Autowired
    private TenantsUsersMapper tenantsUsersMapper;

    /**
     * 新预约订单提醒
     *
     * @param channel
     * @param messageKey
     */
    @Override
    public void onMessage(String channel, String messageKey) {
        log.info(String.format("[Listener] new resever order: received messageKey [%s] on channel [%s]", messageKey, channel));
        if (StringUtils.isBlank(messageKey)) {
            return;
        }
        try {
            while (true) {
                //等待业务处理事务提交
                Thread.sleep(1000);
                String message = mq.brpop(60, WechatConfig.Queue.RESERVE_ORDER_B.getQueue());
                NewOrderModel model = JsonUtils.fromJson(message, NewOrderModel.class);
                if (StringUtils.isBlank(message) || model == null) {
                    return;
                }
                //查询门店所有用户
                List<TenantsUsersEntity> list = tenantsUsersMapper.getByTenantId(model.getTenantId());
                for (TenantsUsersEntity user : list) {
                    //查询订阅的用户
//                    TenantsUserSubscrbes user = tenantsUserSubscribeMapper.getSubscribeInfoByUserId(WechatConfig.Channel.FINISH_JOB.getChannel(), users.getId());
//                    if (user == null) {
//                        continue;
//                    }
                    if (StringUtils.isBlank(user.getOpenId())) {
                        continue;
                    }
                    PushModel data = new PushModel();
                    data.setTouser(user.getOpenId());
                    data.setTemplateId(WechatConfig.Template.NEW_RESERVE_ORDER_TEMPLATE.getId());
                    data.setUrl(Constants.MOBILE_HOST_NAME + "/#/order/detail/" + model.getOrderNo() + "/02");
                    data.addData("first", model.getTitle() + "\n", null)
                            .addData("keyword1", model.getOrderName(), "#173177")
                            .addData("keyword2", model.getOrderTime(), "#173177")
                            .addData("keyword3", model.getOrderAmount(), "#173177")
                            .addData("keyword4", model.getDescription()+ "\n", "#173177")
                            .addData("remark", "点击查看详情", null);

                    push(data);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
