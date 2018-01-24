package com.fbee.modules.mybatis.dao;


import com.fbee.modules.mybatis.model.OrderShareStaffInfo;

import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.model.OrderShareStaffInfo;
import com.fbee.modules.mybatis.model.OrderShareStaffInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface OrderShareStaffInfoMapper {
   
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_share_staff_info
	 * @mbggenerated  Thu Mar 02 13:38:38 CST 2017
	 */
	int countByExample(OrderShareStaffInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_share_staff_info
	 * @mbggenerated  Thu Mar 02 13:38:38 CST 2017
	 */
	int deleteByExample(OrderShareStaffInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_share_staff_info
	 * @mbggenerated  Thu Mar 02 13:38:38 CST 2017
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_share_staff_info
	 * @mbggenerated  Thu Mar 02 13:38:38 CST 2017
	 */
	int insert(OrderShareStaffInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_share_staff_info
	 * @mbggenerated  Thu Mar 02 13:38:38 CST 2017
	 */
	int insertSelective(OrderShareStaffInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_share_staff_info
	 * @mbggenerated  Thu Mar 02 13:38:38 CST 2017
	 */
	List<OrderShareStaffInfo> selectByExample(OrderShareStaffInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_share_staff_info
	 * @mbggenerated  Thu Mar 02 13:38:38 CST 2017
	 */
	OrderShareStaffInfo selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_share_staff_info
	 * @mbggenerated  Thu Mar 02 13:38:38 CST 2017
	 */
	int updateByExampleSelective(@Param("record") OrderShareStaffInfo record,
			@Param("example") OrderShareStaffInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_share_staff_info
	 * @mbggenerated  Thu Mar 02 13:38:38 CST 2017
	 */
	int updateByExample(@Param("record") OrderShareStaffInfo record,
			@Param("example") OrderShareStaffInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_share_staff_info
	 * @mbggenerated  Thu Mar 02 13:38:38 CST 2017
	 */
	int updateByPrimaryKeySelective(OrderShareStaffInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_share_staff_info
	 * @mbggenerated  Thu Mar 02 13:38:38 CST 2017
	 */
	int updateByPrimaryKey(OrderShareStaffInfo record);

	/**
     * 获取该笔订单<br/>
     * 分享阿姨的数�?
     * @param orderNo
     * @return
     */
	Integer getStaffCount(String orderNo);

}