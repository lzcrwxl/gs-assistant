package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.OrderChangehsRecordsEntity;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface OrderChangehsRecordsMapper extends CrudDao<OrderChangehsRecordsEntity>  {

    /**
     * 本地订单-更换阿姨
     * @xiehui
     * @param orderChangehsRecordsEntity
     * @return
     */
    int updateChangehs(OrderChangehsRecordsEntity orderChangehsRecordsEntity);

    /**
     * 本地订单-查询明细最大值
     * @xiehui
     * @param orderNo
     * @return
     */
    OrderChangehsRecordsEntity queryMxid(String orderNo);

    /**
     * 本地订单-第一步，插入信息
     * @xiehui
     * @param orderChangehsRecordsEntity
     * @return
     */
    int insertChangehs(OrderChangehsRecordsEntity orderChangehsRecordsEntity);


    /**
     * 本地更换阿姨获取list
     * @param orderNo
     * @return
     */
    List<OrderChangehsRecordsEntity> queryInfo(String orderNo);
}