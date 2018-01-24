package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.model.AddressInfo;
import com.fbee.modules.mybatis.model.SubscribeInfo;
import com.fbee.modules.mybatis.model.TenantsJobs;
import com.fbee.modules.mybatis.model.TenantsUserSubscrbes;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

@MyBatisDao
public interface TenantsUserSubscribeMapper extends CrudDao<SubscribeInfo>{

    /**
     * 查询订阅用户列表
     * @return
     */
    List<TenantsUserSubscrbes> getJobSubscribeBySubject(TenantsUserSubscrbes subscrbe);

    /**
     * 获取用户openId
     * @param channel
     * @param userId
     * @return
     */
    TenantsUserSubscrbes getSubscribeInfoByUserId(@Param("channel") String channel, @Param("userId")Integer userId);

    List<String> findServicesBySubId(Integer subId);
    List<AddressInfo> findAddressesBySubId(Integer subId);

    /**
     * 清空订阅
     * @param tenantUserId
     */
    void clearUserSubscribe(Integer tenantUserId);


    /**
     * 添加订阅主题
     * @param subId
     */
    void insertSubject(@Param("subId") Integer subId, @Param("subject") String subject);

    /**
     * 添加订阅地址
     * @param subId
     * @param addr
     */
    void insertArea(@Param("subId") Integer subId, @Param("addr") AddressInfo addr);
}