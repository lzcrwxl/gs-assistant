package com.fbee.modules.service;

import com.fbee.modules.bean.UserBean;
import com.fbee.modules.form.*;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.model.Orders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface OrderService {
	
	/**
	 * 订单列表查询
	 */
	JsonResult selectOrdersList(OrdersForm form);
	
	/**
	 * 通过订单流水号查询订单信息
	 */
	JsonResult selectByOrderNo(String orderNo);
	
	/**
	 * 通过订单流水号查询客户信息
	 */
	JsonResult selectMemberInfoByOrderNo(String orderNo);
	
    /**
     * 通过订单流水号查询服务信息
     */
	JsonResult selectServiceInfoByOrderNo(String orderNo);
	
	/**
     * 通过订单流水号查询支付信息
     */
	JsonResult selectPayInfoByOrderNo(String orderNo);
	
	/**
	 * 根据订单流水号获取合同详情
	 */
	JsonResult selectContractInfoByOrderNo(String orderNo);

	/**
	 * 面试通过更新订单信息
	 */
	JsonResult passInterviewInfo(String orderNo,String staffId,UserBean userBean);
	
	/**
	 * 保存订单详情
	 */
	JsonResult saveOrder(String orderNo, String amount, String remark,String serviceCharge);
	
	/**
	 * 更换订单阿姨
	 */
	JsonResult changeStaff(String orderNo, Integer staffId,Integer isLocalStaff, UserBean userBean);
	
    /**
     * 客户合同图片上传
     */
    JsonResult saveContractInfo(String orderNo, MultipartFile[] file, String imgIds);
    
    /**
     * 阿姨详情
     */
    JsonResult selectStaffInfoByOrderNo(String orderNo);
    
    /**
     * 本地订单支付定金
     */
    JsonResult payDeposit(String orderNo);
    
    /**
     * 本地订单支付尾款
     */
    JsonResult payBalance(String orderNo);

    /**
     * 订单阿姨列表获取
     */
	JsonResult getStaffsInfo(StaffListForm form, int pageNumber, int pageSize);

	/**
	 * 订单结单后更换阿姨
	 */
	JsonResult orderChangeStaff(String orderNo, Integer staffId,String tenantRemark,UserBean userBean);

	/**
	 * 删除合同信息
	 * @param id
	 * @return
	 */
	JsonResult deleteContractInfo(Integer id);

	/**
	 * 订单管理保存或更新客户信息
	 */
	JsonResult saveOrUpdateCustomer(OrderCustomerInfoForm form, Integer tenantId);
	
	/**
	 * 订单管理保存或更新服务信息
	 */
	JsonResult saveOrUpdateService(OrderServiceInfoForm form);

	/**
	 * 蜂享阿姨列表
	 */
	JsonResult getShareStaffsInfo(StaffListForm form, int pageNumber, int pageSize);

	/**
     * 创建订单阿姨列表获取
     */
	JsonResult getCreateStaffsInfo(StaffListForm form, int pageNumber, int pageSize);

	/**
	 * 创建订单
	 */
	JsonResult createOrder(OrderCreateForm form, UserBean userBean);

	/**
     * 客户合同图片删除(单个)
     */
	JsonResult deleteContract(Integer id);

	/**
	 * 本地订单-定金支付
	 * @param orderNo
	 * author Baron
	 * date 2017-04-27
	 * @return
	 */
	JsonResult depositPayment(String orderNo,String type,String inOutObject);

	/**
	 * 本地订单-定金修改
	 * @param orderNo
	 * author Baron
	 * date 2017-04-27
	 * @return
	 */
	JsonResult modifyDeposit(String orderNo,String deposit);


	/**
	 * 本地订单-更换阿姨
	 * @param orderNo
	 * author Baron
	 * date 2017-05-02
	 * @return
	 */
	JsonResult changehs(String orderNo,String type,String remark,UserBean userBean);


	/**
	 * 根据状态取消订单
	 *
	 * @xiehui
	 * @param orderNo
	 * @return
	 */
	JsonResult cancleOrderStatus(String orderNo,String inOutObject);

	
	/**
	 *  更新订单客户服务价格和年龄要求
	 */
	JsonResult updateOrdersCustomersInfo(Map<String, Object> map);


	Integer getMatchValue(String orderNo, Integer staffId) ;

	/**
	 * 取消超期未处理的订单
	 */
	void cancelDelayOrder(Orders order) throws Exception;
}
