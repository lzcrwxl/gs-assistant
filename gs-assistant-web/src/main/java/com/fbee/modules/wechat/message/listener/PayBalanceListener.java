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
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.PayBalanceModel;
import com.fbee.modules.wechat.message.model.PayDepositModel;
import com.fbee.modules.wechat.message.model.PushModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayBalanceListener extends PushWechatListener {

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
     * 支付尾款提醒
     *
     * @param channel
     * @param messageKey
     */
    @Override
    public void onMessage(String channel, String messageKey) {
        log.info(String.format("[Listener] pay balance: received messageKey [%s] on channel [%s]", messageKey, channel));
        if (StringUtils.isBlank(messageKey)) {
            return;
        }
        try {
            while (true) {
                //等待业务处理事务提交
                Thread.sleep(1000);
                String orderNo = mq.brpop(10, WechatConfig.Queue.ORDER_PAY_BALANCE_B.getQueue());
                if (StringUtils.isBlank(orderNo)) {
                    return;
                }
//                TenantsUserSubscrbes user = tenantsUserSubscribeMapper.getSubscribeInfoByUserId(WechatConfig.Channel.RESUME_APPLY.getChannel(),model.getTenantUserId());
//                if(user == null){
//                    continue;
//                }

                Orders order = ordersMapper.selectByPrimaryKey(orderNo);
                OrderCustomersInfo cust = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
                TenantsUsersEntity user = tenantsUsersMapper.getById(order.getUserId());

                String serviceType = String.format("%s-%s", DictionarysCacheUtils.getServiceTypeName(order.getServiceItemCode()), DictionarysCacheUtils.getServiceNatureStr(order.getServiceItemCode(), cust.getServiceType()));

                PushModel data = new PushModel();
                data.setTouser(user.getOpenId());
                data.setTemplateId(WechatConfig.Template.ORDER_PAY_BALANCE_TEMPLATE.getId());
                data.setUrl(Constants.MOBILE_HOST_NAME + "/#/order/inprocess/" + orderNo + "/02/04");
                data.addData("first", String.format("您好，客户%s已成功支付尾款。\n", cust.getMemberName()))
                        .addData("keyword1", order.getOrderBalance().setScale(2).toString() + "元", "#173177")
                        .addData("keyword2", serviceType, "#173177")
                        .addData("keyword3", orderNo, "#173177")
                        .addData("remark", "\n请安排家政员准时上门服务，点击查看详情。");

                push(data);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
