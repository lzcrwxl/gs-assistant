package com.fbee.modules.mybatis.dao;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.mybatis.entity.TenantsStaffsInfoEntity;
import com.fbee.modules.mybatis.model.StaffSnapShotInfo;
import com.fbee.modules.mybatis.model.TenantsStaffsInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface TenantsStaffsInfoMapper extends CrudDao<TenantsStaffsInfoEntity>{

	TenantsStaffsInfoEntity selectByPrimaryKey(Integer staffId);
	void updateByPrimaryKey(TenantsStaffsInfo tenantsStaffsInfo);
	/**
	 * @param map
	 * @return
	 */
	Integer getStaffQueryCount(Map<Object, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<TenantsStaffsInfoEntity> getStaffQueryList(Map<Object, Object> map);

	/**
	 * @param param
	 * @return
	 */
	TenantsStaffsInfoEntity getStaffInfo(TenantsStaffsInfoEntity param);
	
	/**
	 * 获取推荐阿姨列表
	 * @param tenantId
	 * @return
	 */
	List<Map<String, Object>> selectRecommendList(Integer tenantId);
	
	/**
	 * 获取阿姨列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> selectStaffInfoList(Map<String, Object> map);
	
	/**
	 * 获取阿姨个人信息
	 * @param tenantId
	 * @param staffId
	 * @return
	 */
	Map<String, Object> getStaffInfoByStaffId(Integer tenantId, Integer staffId);
	
	/**
	 * 模糊查询获取阿姨列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getStaffInfoLikeStaffName(Map<String, Object> map);
	
	/**
	 * 模糊查询获取阿姨记录数
	 */
	@SuppressWarnings("rawtypes")
	int getCountLike(Map map);
	Integer getOrdersQueryCount(int tenantId, String membermobile);
	
	/**
	 * 获取总条数
	 * @param tenantId
	 */
	int selectStaffInfoListCount(Integer tenantId);

	/**
	 * 获取阿姨信息列表<br>
	 * 匹配度
	 * @param map
	 * @return
	 */
	Integer getStaffInfoCount(Map<Object, Object> map);


	List<TenantsStaffsInfoEntity> getStaffInfoList(Map<Object, Object> map);

	/**
	 * 带条件查询阿姨列表(匹配度)
	 */
	List<Map<Object, Object>> selectOrderStaffInfoList(Map<Object, Object> map);
	
	/**
	 * 带条件查询阿姨列表条数(匹配度)
	 */
	int getOrderStaffsCount(Map<Object, Object> map);
	
	/**
	 * 带条件查询蜂享阿姨列表(匹配度)
	 */
	List<Map<Object, Object>> selectShareStaffInfoList(Map<Object, Object> map);
	
	/**
	 * 带条件查询蜂享阿姨列表条数(匹配度)
	 */
	int getShareStaffsCount(Map<Object, Object> map);
	
	/**
	 * 根据身份证号查询租户阿姨
	 */
	TenantsStaffsInfoEntity getStaffByCertNo(TenantsStaffsInfoEntity entity);
	
	/**
	 * 
	 */
	List<TenantsStaffsInfoEntity> getStaffListByCertNo(String certNo);
	
	int insertSelective(TenantsStaffsInfo entity);
	
	/**
	 * 修改家政员信息
	 * @param staffInfo
	 * @return
	 */
	int updateStaffInfo(TenantsStaffsInfoEntity staffInfo);
	
	/**
	 * 修改家政员上下架状态
	 * @param map
	 * @return
	 */
	int updateStaffOnOffShelf(TenantsStaffsInfoEntity staffInfo);
	
	/**
	 * @param map
	 * @return
	 */
	Integer staffQueryCount(Map<Object, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<TenantsStaffsInfoEntity> staffQueryList(Map<Object, Object> map);
	
	/**
	 * 更新家政员工作状态
	 * @return
	 */
	int updateStaffWorkStatus();
	
	/**
	 * 更新家政员工作状态
	 * @return
	 */
	int updateStaffWorkStatusInfo();
	
	/**
	 * 
	 * @MethodName:getStaffInfoByTenantId
	 * @Type:TenantsStaffsInfoMapper
	 * @Description:推荐管理信息不足两条时，系统默认添加
	 * @Return:List<TenantsStaffsInfoEntity>
	 * @Param:@param tenantId
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 24, 2017 12:26:03 PM
	 */
	List<TenantsStaffsInfoEntity> getStaffInfoByTenantId(Integer tenantId);
	
	/**
	 * 获取总条数
	 * @param tenantId
	 */
	int getCount(Integer tenantId);


	StaffSnapShotInfo getSnapshotById(Integer staffId);
	
	
	
}