package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsTradeRecordsEntity;
import com.fbee.modules.mybatis.model.TenantsTradeRecords;
import com.fbee.modules.mybatis.model.TenantsTradeRecordsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface TenantsTradeRecordsMapper extends CrudDao<TenantsTradeRecordsEntity>{

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_trade_records
	 * @mbggenerated  Mon Feb 27 09:57:14 CST 2017
	 */
	int countByExample(TenantsTradeRecordsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_trade_records
	 * @mbggenerated  Mon Feb 27 09:57:14 CST 2017
	 */
	int deleteByExample(TenantsTradeRecordsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_trade_records
	 * @mbggenerated  Mon Feb 27 09:57:14 CST 2017
	 */
	int deleteByPrimaryKey(String tradeNo);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_trade_records
	 * @mbggenerated  Mon Feb 27 09:57:14 CST 2017
	 */
	int insert(TenantsTradeRecords record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_trade_records
	 * @mbggenerated  Mon Feb 27 09:57:14 CST 2017
	 */
	int insertSelective(TenantsTradeRecords record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_trade_records
	 * @mbggenerated  Mon Feb 27 09:57:14 CST 2017
	 */
	List<TenantsTradeRecords> selectByExample(TenantsTradeRecordsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_trade_records
	 * @mbggenerated  Mon Feb 27 09:57:14 CST 2017
	 */
	TenantsTradeRecords selectByPrimaryKey(String tradeNo);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_trade_records
	 * @mbggenerated  Mon Feb 27 09:57:14 CST 2017
	 */
	int updateByExampleSelective(@Param("record") TenantsTradeRecords record,
			@Param("example") TenantsTradeRecordsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_trade_records
	 * @mbggenerated  Mon Feb 27 09:57:14 CST 2017
	 */
	int updateByExample(@Param("record") TenantsTradeRecords record,
			@Param("example") TenantsTradeRecordsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_trade_records
	 * @mbggenerated  Mon Feb 27 09:57:14 CST 2017
	 */
	int updateByPrimaryKeySelective(TenantsTradeRecords record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_trade_records
	 * @mbggenerated  Mon Feb 27 09:57:14 CST 2017
	 */
	int updateByPrimaryKey(TenantsTradeRecords record);
}