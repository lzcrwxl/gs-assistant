package com.fbee.modules.service;

import com.fbee.modules.bean.UserBean;
import com.fbee.modules.form.StaffListForm;
import com.fbee.modules.jsonData.basic.JsonResult;

/**
 * @ClassName: OrderShareService 
 * @Description:淘蜂享服务接口
 * @author ZhangJie
 *
 */
public interface OrderShareService {

	/**
	 * 获取蜂享池信息列表
	 * @param pageSize 
	 * @param pageNumber 
	 * @return
	 */
	JsonResult getOrderShareInfoList(int pageNumber, int pageSize);

	/**
	 * 获取订单明细
	 * @param userBean 
	 * @param orderNo 主键
	 * @return
	 */
	JsonResult getOrderDetailInfo(UserBean userBean, String orderNo);

	/**
	 * 非分享方<br/>
	 * 获取阿姨信息列表
	 * @param userBean
	 * @param orderNo 
	 * @return
	 */
	JsonResult getStaffsInfo(StaffListForm form,UserBean userBean,int pageNumber, int pageSize);
	/**
	 * 选择阿姨确认提交
	 * @param userBean
	 * @param orderNo
	 * @param staffId
	 * @param matchingDegree
	 * @return
	 */
	JsonResult submitStaffInfo(UserBean userBean, String orderNo, Integer staffId,String matchingDegree);
	/**
	 * 获取投递箱信息列表
	 * @param userBean
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	JsonResult getDeliveryBoxInfoList(UserBean userBean, int pageNumber, int pageSize);
	/**
	 * 获取阿姨明细信息
	 * @param userBean
	 * @param staffId
	 * @return
	 */
	JsonResult getStaffDetailInfo(UserBean userBean, Integer staffId);

	/**
	 * 阿姨通过
	 * @param userBean
	 * @param id
	 * @return
	 */
	JsonResult staffPass(UserBean userBean,Integer id);
	/**
	 * 阿姨退回
	 * @param userBean
	 * @param reason 
	 * @param id 投递箱主键
	 * @return
	 */
	JsonResult staffReturn(UserBean userBean,String reason, Integer id);
	/**
	 * 拒绝阿姨
	 * @param userBean
	 * @param id 投递箱主键
	 * @return
	 */
	JsonResult rejectStaff(UserBean userBean,Integer id);
	/**
	 * 阿姨面试通过
	 * @param userBean
	 * @param id
	 * @return
	 */
	JsonResult staffInterviewPass(UserBean userBean, Integer id);
}
