package com.fbee.modules.mybatis.dao;

import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.OrderCustomersInfoEntity;
import com.fbee.modules.mybatis.model.OrderCustomersInfo;

@MyBatisDao
public interface OrderCustomersInfoMapper extends CrudDao<OrderCustomersInfoEntity>{
    int deleteByPrimaryKey(String orderNo);

    int insert(OrderCustomersInfo record);

    int insertSelective(OrderCustomersInfo record);


    OrderCustomersInfo selectByPrimaryKey(String orderNo);

//    OrderCustomersInfo selectByPrimaryKey(String orderNo);

    //OrderCustomersInfoEntity selectByPrimaryKey(String orderNo);


    int updateByPrimaryKeySelective(OrderCustomersInfo record);
    
    int updateByCustomerInfo(OrderCustomersInfo record);

    int updateByPrimaryKey(OrderCustomersInfo record);

    
    /**
     * 根据订单流水号获取订单客户信息
     * @param orderNo
     * @return
     */
    Map<String, Object> selectMemberInfoByOrderNo(String orderNo);
    
    /**
     * 根据订单流水号获取订单服务信息
     * @param orderNo
     * @return
     */
    Map<String,Object> selectServiceInfoByOrderNo(String orderNo);
	
	/**
     * 更新订单客户服务价格和年龄要求
     * @param map
     * @return
     */
    int updateOrdersCustomersInfo(Map<String, Object> map);

}