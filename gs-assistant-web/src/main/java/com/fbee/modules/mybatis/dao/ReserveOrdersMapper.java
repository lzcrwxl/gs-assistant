package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.ReserveOrdersEntity;
import com.fbee.modules.mybatis.model.Orders;
import com.fbee.modules.mybatis.model.ReserveOrders;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

@MyBatisDao
public interface ReserveOrdersMapper extends CrudDao<ReserveOrdersEntity> {

	int deleteByPrimaryKey(String orderNo);

	int insert(ReserveOrders record);

	int insertSelective(ReserveOrders record);
	
	/**
	 * 查询预约订单详情
	 * @param orderNo
	 * @return
	 */
	ReserveOrders selectByPrimaryKey(String orderNo);

	
	/**
	 * 预约查询-预约信息
	 * @param orderNo
	 * @return
	 */
	Map<String,Object> selectReserveByOrderNo(String orderNo);
	
	/**
	 * 预约查询-阿姨信息
	 * @param orderNo
	 * @return
	 */
	Map<String,Object> selectStaffByOrderNo(String orderNo);

	/**
	 * 预约订单列表总数查询
	 * @param map
	 * @return
	 */
	Integer getReserveOrdersCount(Map<Object, Object> map);

	/**
	 * 预约订单列表查询
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getReserveOrdersList(Map<Object, Object> map);
	
	/**
	 * 预约订单-完成处理更新
	 * @return
	 */
	int updateReserveByOrderNo(ReserveOrdersEntity reserveOrdersEntity);
	/**
	 * 预约订单-更换阿姨
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(ReserveOrders record);
	
	/**
	 * 预约订单-完成处理更新
	 * @return
	 */
	int updateReserveByOrderNo1(ReserveOrdersEntity reserveOrdersEntity);
}