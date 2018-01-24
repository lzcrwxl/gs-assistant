package com.fbee.modules.service;

import com.fbee.modules.bean.UserBean;
import com.fbee.modules.form.CustomerSaveForm;
import com.fbee.modules.form.ReserveOrderDetailsForm;
import com.fbee.modules.form.ReserveOrdersForm;
import com.fbee.modules.form.StaffListForm;
import com.fbee.modules.jsonData.basic.JsonResult;


/**
* @Description：预约订单服务层接口 
* @author fry
* @date 2017年2月6日 上午10:34:35
* 
*/
public interface ReserveOrdersService {
	/**
	 * 获取预约订单列表
	 * @param tenantId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	JsonResult getReserveOrdersList(Integer tenantId,UserBean userBean,ReserveOrdersForm reserveOrdersForm ,Integer pageNumber, Integer pageSize);

	JsonResult getReserveOrdersList(Integer tenantId, UserBean userBean);
	/**
	 * 查询预约详情-预约信息
	 * @param orderNo
	 * @return
	 */
	JsonResult selectReserveByOrderNo(String orderNo);
	/**
	 * 查询预约详情-客户信息
	 * @param orderNo
	 * @return
	 */
	JsonResult selectMemberByOrderNo(String orderNo);
	/**
	 * 查询预约详情-服务信息
	 * @param orderNo
	 * @return
	 */
	JsonResult selectServiceByOrderNo(String orderNo);
	/**
	 * 查询预约详情-阿姨信息
	 * @param orderNo
	 * @return
	 */
	JsonResult selectStaffByOrderNo(String orderNo);
	/**
	 * 预约详情-客户信息保存至客户管理
	 * @param orderNo
	 * @return
	 */
	JsonResult saveMemberByOrderNo(String orderNo,ReserveOrderDetailsForm reserveOrderDetailsForm,String loginAccount);
	
	/**
	 * 预约详情-客户信息保存至订单
	 * @param reserveOrderDetailsForm
	 * @return
	 */
	JsonResult saveMemberToOrder(ReserveOrderDetailsForm reserveOrderDetailsForm);
	
	/**
	 * 预约详情-预约信息保存至订单
	 * 
	 * @param reserveOrderDetailsForm
	 * @return
	 */
	JsonResult saveServiceToOrder(ReserveOrderDetailsForm reserveOrderDetailsForm);
	
	/**
	 * 预约详情-完成处理更新
	 * @param reserveOrderDetailsForm
	 * @return
	 */
	JsonResult updateReserveByOrderNo(ReserveOrderDetailsForm reserveOrderDetailsForm);
	
	/**
	 * 预约订单生成订单
	 * @param reserveOrderDetailsForm
	 * @return
	 */
	JsonResult saveReserveByOrderNo(ReserveOrderDetailsForm reserveOrderDetailsForm);
	
	/**
	 * 获取阿姨信息列表
	 * @param userBean
	 * @param orderNo 
	 * @return
	 */
	JsonResult getStaffsInfo(StaffListForm form,int pageNumber, int pageSize);
	
	/**
	 * 预约订单-更换阿姨
	 * @param record
	 * @return
	 */
	JsonResult updateStaffByOrderNo(String orderNo, Integer staffId);

    JsonResult modifyRemark(ReserveOrderDetailsForm reserveOrderDetailsForm);
}

