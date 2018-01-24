package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.OrderContractInfoEntity;
import com.fbee.modules.mybatis.model.OrderContractInfo;

@MyBatisDao
public interface OrderContractInfoMapper extends CrudDao<OrderContractInfoEntity>{
    int deleteByPrimaryKey(Integer id);

    int insert(OrderContractInfo record);

    int insertSelective(OrderContractInfo record);

    OrderContractInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderContractInfoEntity record);

    int updateByPrimaryKey(OrderContractInfoEntity record);
    
    /**
     * 根据订单流水号获取合同详情
     */
    List<Map<String,String>> selectContractInfoByOrderNo(String orderNo);
    
    /**
     * 根据订单流水号获取合同信息记录条数
     */
    int getCountByOrderNo(String orderNo);
    
    /**
     * 根据订单号获取合同图片的id
     */
    List<String> getContractsByOrderNo(String orderNo);
    
}