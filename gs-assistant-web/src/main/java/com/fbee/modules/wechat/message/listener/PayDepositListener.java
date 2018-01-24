package com.fbee.modules.wechat.message.listener;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.entity.MembersInfoEntity;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;
import com.fbee.modules.mybatis.model.OrderCustomersInfo;
import com.fbee.modules.mybatis.model.Orders;
import com.fbee.modules.mybatis.model.TenantsUserSubscrbes;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.PayDepositModel;
import com.fbee.modules.wechat.message.model.PushModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayDepositListener extends PushWechatListener {

    private final static Logger log = LoggerFactory.getLogger("messageLogger");

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    @Autowired
    private TenantsUserSubscribeMapper tenantsUserSubscribeMapper;
    @Autowired
    private TenantsUsersMapper tenantsUsersMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderCustomersInfoMapper orderCustomersInfoMapper;
    @Autowired
    private MembersInfoMapper membersInfoMapper;


    /**
     * 支付定金提醒
     *
     * @param channel
     * @param messageKey
     */
    @Override
    public void onMessage(String channel, String messageKey) {
        log.info(String.format("[Listener] pay deposit: received messageKey [%s] on channel [%s]", messageKey, channel));
        if (StringUtils.isBlank(messageKey)) {
            return;
        }
        try {
            while (true) {
                //等待业务处理事务提交
                Thread.sleep(1000);
                String orderNo = mq.brpop(60, WechatConfig.Queue.ORDER_PAY_DEPOSIT_B.getQueue());
                if (StringUtils.isBlank(orderNo)) {
                    return;
                }

                Orders order = ordersMapper.selectByPrimaryKey(orderNo);
                OrderCustomersInfo cust = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
                TenantsUsersEntity user = tenantsUsersMapper.getById(order.getUserId());

                PayDepositModel model = new PayDepositModel();
                model.setTitle(String.format("您好，客户%s已支付定金。",cust.getMemberName()));
                model.setOrderNo(orderNo);
                model.setOrderAmount(order.getOrderDeposit().setScale(2).toString()+"元");
                model.setRemark("请尽快安排家政员与客户进行面试");

                PushModel data = new PushModel();
                data.setTouser(user.getOpenId());
                data.setTemplateId(WechatConfig.Template.ORDER_PAY_DEPOSIT_TEMPLATE.getId());
                data.setUrl(Constants.MOBILE_HOST_NAME + "/#/order/inprocess/"+orderNo+"/02/02");
                data.addData("first", model.getTitle()+"\n")
                        .addData("keyword1", model.getOrderNo(), "#173177")
                        .addData("keyword2", model.getOrderAmount(), "#173177")
                        .addData("remark", "\n"+model.getRemark());

                push(data);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
