package com.fbee.modules.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.fbee.modules.core.persistence.CrudDao;
import com.fbee.modules.core.persistence.annotation.MyBatisDao;
import com.fbee.modules.jsonData.extend.ServiceManageIndexInfoJson;
import com.fbee.modules.mybatis.entity.TenantsServiceItemsEntity;
import com.fbee.modules.mybatis.model.TenantsServiceItems;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface TenantsServiceItemsMapper extends CrudDao<TenantsServiceItemsEntity>{
	
	/**
	 * 获取服务工种列表
	 * @param staffSerItems
	 * @return
	 */
	List<Map<String, String>> getStaffServiceItemList(Integer tenantId);
	
	/**
	 * 获取服务工种
	 * @param tenantsServiceItems
	 * @return
	 */
	TenantsServiceItems selectByPrimaryKey(TenantsServiceItemsEntity tenantsServiceItems);
	
	/**
	 * 更新服务工种
	 * @param tenantsServiceItems
	 * @return
	 */
	int updateByPrimaryKeySelective(TenantsServiceItems tenantsServiceItems);
	
	/**
	 * 获取租户服务工种serviceItemCode
	 * @param tenantId
	 */
	List<Map<String,String>> getStaffServiceItemCodes(Integer tenantId);

	/**
	 * 
	 * @MethodName:getServiceManageIndexInfo
	 * @Type:TenantsServiceItemsMapper
	 * @Description:服务管理首页
	 * @Return:List<TenantsServiceItems>
	 * @Param:@param tenantId
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 12, 2017 6:14:42 PM
	 */
	List<TenantsServiceItems> getServiceManageIndexInfo(Integer tenantId);

	/**
	 * 
	 * @MethodName:updateServiceManageDetailInfo
	 * @Type:TenantsServiceItemsMapper
	 * @Description:服务管理-修改详情信息
	 * @Return:int
	 * @Param:@param serviceManageIndexInfoJson
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 14, 2017 1:24:54 PM
	 */
	int updateServiceManageDetailInfo(ServiceManageIndexInfoJson serviceManageIndexInfoJson);

	TenantsServiceItems getServiceManageByTenantCode(@Param("tenantId") Integer tenantId, @Param("serviceItemCode") String serviceItemCode);

    /**
	 * 
	 * @MethodName:getOldImgUrlByTenantId
	 * @Type:TenantsServiceItemsMapper
	 * @Description:获取工种图片信息
	 * @Return:String
	 * @Param:@param tenantId
	 * @Param:@return
	 * @Thrown:
	 * @Date:Sep 15, 2017 6:37:52 AM
	 */
	/*String getOldImgUrlByTenantId(Integer tenantId);*/
	
	
}