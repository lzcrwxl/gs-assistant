package com.fbee.modules.service;

import com.fbee.modules.mybatis.model.SubscribeInfo;
import com.fbee.modules.service.basic.ServiceException;

public interface SubscribeService {

	void settingSubscribe(SubscribeInfo subscribeInfo) throws ServiceException;

	SubscribeInfo get(Integer tenantUserId) throws ServiceException;
}
