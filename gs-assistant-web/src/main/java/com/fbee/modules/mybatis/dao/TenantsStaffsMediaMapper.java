package com.fbee.modules.mybatis.dao;

import java.util.List;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsStaffsMediaEntity;
import com.fbee.modules.mybatis.model.TenantsStaffsMedia;
import com.fbee.modules.mybatis.model.TenantsStaffsMediaExample;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface TenantsStaffsMediaMapper extends CrudDao<TenantsStaffsMediaEntity>{

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_staffs_media
	 * @mbggenerated  Tue Mar 07 10:11:39 CST 2017
	 */
	int countByExample(TenantsStaffsMediaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_staffs_media
	 * @mbggenerated  Tue Mar 07 10:11:39 CST 2017
	 */
	int deleteByExample(TenantsStaffsMediaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_staffs_media
	 * @mbggenerated  Tue Mar 07 10:11:39 CST 2017
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_staffs_media
	 * @mbggenerated  Tue Mar 07 10:11:39 CST 2017
	 */
	int insert(TenantsStaffsMedia record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_staffs_media
	 * @mbggenerated  Tue Mar 07 10:11:39 CST 2017
	 */
	int insertSelective(TenantsStaffsMedia record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_staffs_media
	 * @mbggenerated  Tue Mar 07 10:11:39 CST 2017
	 */
	List<TenantsStaffsMedia> selectByExample(TenantsStaffsMediaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_staffs_media
	 * @mbggenerated  Tue Mar 07 10:11:39 CST 2017
	 */
	TenantsStaffsMedia selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_staffs_media
	 * @mbggenerated  Tue Mar 07 10:11:39 CST 2017
	 */
	int updateByExampleSelective(@Param("record") TenantsStaffsMedia record,
			@Param("example") TenantsStaffsMediaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_staffs_media
	 * @mbggenerated  Tue Mar 07 10:11:39 CST 2017
	 */
	int updateByExample(@Param("record") TenantsStaffsMedia record,
			@Param("example") TenantsStaffsMediaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_staffs_media
	 * @mbggenerated  Tue Mar 07 10:11:39 CST 2017
	 */
	int updateByPrimaryKeySelective(TenantsStaffsMedia record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tenants_staffs_media
	 * @mbggenerated  Tue Mar 07 10:11:39 CST 2017
	 */
	int updateByPrimaryKey(TenantsStaffsMedia record);

	/**
	 * @param staffId
	 */
	List<TenantsStaffsMedia> getAllMedias(Integer staffId);

	/**
	 * 清除阿姨默认图片
	 * @param staffId
	 */
	void clearStaffImageDefault(Integer staffId);
}