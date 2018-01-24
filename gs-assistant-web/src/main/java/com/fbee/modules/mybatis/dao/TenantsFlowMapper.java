package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.model.TenantsFlow;
import com.fbee.modules.mybatis.model.TenantsFlowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisDao
public interface TenantsFlowMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_flow
	 * @mbggenerated  Thu Mar 23 09:53:55 CST 2017
	 */
	int countByExample(TenantsFlowExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_flow
	 * @mbggenerated  Thu Mar 23 09:53:55 CST 2017
	 */
	int deleteByExample(TenantsFlowExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_flow
	 * @mbggenerated  Thu Mar 23 09:53:55 CST 2017
	 */
	int deleteByPrimaryKey(String tradeFlowNo);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_flow
	 * @mbggenerated  Thu Mar 23 09:53:55 CST 2017
	 */
	int insert(TenantsFlow record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_flow
	 * @mbggenerated  Thu Mar 23 09:53:55 CST 2017
	 */
	int insertSelective(TenantsFlow record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_flow
	 * @mbggenerated  Thu Mar 23 09:53:55 CST 2017
	 */
	List<TenantsFlow> selectByExample(TenantsFlowExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_flow
	 * @mbggenerated  Thu Mar 23 09:53:55 CST 2017
	 */
	TenantsFlow selectByPrimaryKey(String tradeFlowNo);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_flow
	 * @mbggenerated  Thu Mar 23 09:53:55 CST 2017
	 */
	int updateByExampleSelective(@Param("record") TenantsFlow record, @Param("example") TenantsFlowExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_flow
	 * @mbggenerated  Thu Mar 23 09:53:55 CST 2017
	 */
	int updateByExample(@Param("record") TenantsFlow record, @Param("example") TenantsFlowExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_flow
	 * @mbggenerated  Thu Mar 23 09:53:55 CST 2017
	 */
	int updateByPrimaryKeySelective(TenantsFlow record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_flow
	 * @mbggenerated  Thu Mar 23 09:53:55 CST 2017
	 */
	int updateByPrimaryKey(TenantsFlow record);
}