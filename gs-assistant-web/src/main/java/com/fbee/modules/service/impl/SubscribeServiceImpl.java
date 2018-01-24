package com.fbee.modules.service.impl;

import com.fbee.modules.mybatis.dao.TenantsUserSubscribeMapper;
import com.fbee.modules.mybatis.model.AddressInfo;
import com.fbee.modules.mybatis.model.SubscribeInfo;
import com.fbee.modules.mybatis.model.TenantsUserSubscrbes;
import com.fbee.modules.service.SubscribeService;
import com.fbee.modules.service.basic.ServiceException;
import com.fbee.modules.wechat.message.config.WechatConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class SubscribeServiceImpl implements SubscribeService {

    @Autowired
    private TenantsUserSubscribeMapper tenantsUserSubscribeMapper;

    /**
     * 订阅设置
     *
     * @param subscribeInfo
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void settingSubscribe(SubscribeInfo subscribeInfo) throws ServiceException {
        //清空订阅消息
        tenantsUserSubscribeMapper.clearUserSubscribe(subscribeInfo.getTenantUserId());

        if ("0".equals(subscribeInfo.getIsOn())) {
            //取消订阅
            return;
        }
        //设置订阅消息
        subscribeInfo.setId(null);
        subscribeInfo.setChannel(WechatConfig.Channel.JOB_PUBLISH.getChannel());
        tenantsUserSubscribeMapper.insert(subscribeInfo);
        Integer subId = subscribeInfo.getId();
        if (subscribeInfo.getServices() != null) {
            for (String subject : subscribeInfo.getServices()) {
                tenantsUserSubscribeMapper.insertSubject(subId, subject);
            }
        }
        if (subscribeInfo.getAddressInfos() != null) {
            for (AddressInfo addr : subscribeInfo.getAddressInfos()) {
                tenantsUserSubscribeMapper.insertArea(subId, addr);
            }
        }

    }

    /**
     * 订阅查询
     */
    @Override
    public SubscribeInfo get(Integer tenantUserId) throws ServiceException {
        SubscribeInfo subscribeInfo = new SubscribeInfo();
        TenantsUserSubscrbes sub = tenantsUserSubscribeMapper.getSubscribeInfoByUserId(WechatConfig.Channel.JOB_PUBLISH.getChannel(), tenantUserId);
        if (sub == null) {
            subscribeInfo.setIsOn("0");
            return subscribeInfo;
        }

        subscribeInfo.setIsOn("1");
        subscribeInfo.setServices(tenantsUserSubscribeMapper.findServicesBySubId(sub.getId()));
        subscribeInfo.setAddressInfos(tenantsUserSubscribeMapper.findAddressesBySubId(sub.getId()));

        return subscribeInfo;
    }

}
