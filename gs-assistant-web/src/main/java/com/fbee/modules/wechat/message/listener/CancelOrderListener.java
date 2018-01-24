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
import com.fbee.modules.wechat.message.model.PushModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderListener extends PushWechatListener {

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
     * 取消订单成功
     *
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(String channel, String message) {
        log.info(String.format("[Listener] cancel order: received messageKey [%s] on channel [%s]", message, channel));
        if (StringUtils.isBlank(message)) {
            return;
        }
        try {
                //等待业务处理事务提交
                Thread.sleep(1000);
                String orderNo = message;
                Orders order = ordersMapper.selectByPrimaryKey(orderNo);
                OrderCustomersInfo orderCustomersInfo = orderCustomersInfoMapper.selectByPrimaryKey(orderNo);
                TenantsUsersEntity user = tenantsUsersMapper.getById(order.getUserId());

                String serviceType = String.format("%s-%s", DictionarysCacheUtils.getServiceTypeName(order.getServiceItemCode()),DictionarysCacheUtils.getServiceNatureStr(order.getServiceItemCode(),orderCustomersInfo.getServiceType()));


                PushModel data = new PushModel();
                data.setTouser(user.getOpenId());
                data.setTemplateId(WechatConfig.Template.BUISNESS_TEMPLATE.getId());
                data.setUrl(Constants.MOBILE_HOST_NAME + "/#/order/inprocess/"+orderNo+"/02/05");
                data.addData("first", "客户订单已取消。\n")
                        .addData("keyword1", serviceType, "#173177")
                        .addData("keyword2", "已取消", "#173177")
                        .addData("keyword3", String.format("客户%s取消订单",orderCustomersInfo.getMemberName()), "#173177")
                        .addData("remark", "\n"+order.getCancleReason());

                push(data);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
