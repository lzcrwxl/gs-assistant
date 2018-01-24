package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.form.*;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.CustomerService;
import com.fbee.modules.service.OrderService;
import com.fbee.modules.utils.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 贺章鹏
 * @ClassName: OrdersController
 * @Description: 订单管理控制器
 * @date 2016年12月28日 下午12:04:35
 */
@Controller
@RequestMapping(RequestMappingURL.ORDER_BASE_URL)
public class OrdersController {
	
	Logger log = LoggerFactory.getLogger(OrdersController.class);
	
    @Autowired
    OrderService orderService;

    @Autowired
    CustomerService customerService;

    /**
     * 获取订单列表
     *
     * @param request
     * @param response
     * @param form
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = RequestMappingURL.ORDERSLIST_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult selectOrdersList(
            HttpServletRequest request, HttpServletResponse response,
            OrdersForm form,
            @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize
    ) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            form.setTenantId(userBean.getTenantId());
            if (form.getPageNum() == null) {
                form.setPageNum(pageNumber);
            }
            if (form.getPageSize() == null) {
                form.setPageSize(pageSize);
            }
            //添加权限 Baron 20170525
            if (!"01".equals(userBean.getUserType()) && !"02".equals(userBean.getUserType()))
                form.setAddAccount(userBean.getLoginAccount());
            if (!"01".equals(userBean.getUserType()) && !"02".equals(userBean.getUserType()))
                form.setUserId(userBean.getUserId());
            return orderService.selectOrdersList(form);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }

    }

    /**
     * 根据订单流水号查询订单详情
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.ORDERINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult selectOrdersList(HttpServletRequest request, HttpServletResponse response, String orderNo) {
        try {
            return orderService.selectByOrderNo(orderNo);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 根据订单流水号查询客户详情
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.ORDERMEMBERINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult selectMemberInfoByOrderNo(HttpServletRequest request, HttpServletResponse response, String orderNo) {

        try {
            return orderService.selectMemberInfoByOrderNo(orderNo);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }

    }

    /**
     * 根据订单流水号查询服务详情
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.ORDERSERVICEINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult selectServiceInfoByOrderNo(HttpServletRequest request, HttpServletResponse response, String orderNo) {
        try {
            return orderService.selectServiceInfoByOrderNo(orderNo);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 根据订单流水号查询支付详情
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.ORDERPAYINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult selectPayInfoByOrderNo(HttpServletRequest request, HttpServletResponse response, String orderNo) {
        try {
            return orderService.selectPayInfoByOrderNo(orderNo);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 根据订单流水号查询合同详情
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.ORDERCONTRACTINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult selectContractInfoByOrderNo(HttpServletRequest request, HttpServletResponse response, String orderNo) {
        try {
            return orderService.selectContractInfoByOrderNo(orderNo);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 面试通过更新订单信息
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.PASSINTERVIEW_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult passInterviewInfo(HttpServletRequest request, HttpServletResponse response, String orderNo, String staffId) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            return orderService.passInterviewInfo(orderNo, staffId, userBean);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 保存订单详情
     *
     * @param request
     * @param response
     * @param orderNo
     * @param amount
     * @param remark
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SAVEORDER_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveOrder(HttpServletRequest request, HttpServletResponse response, String orderNo, String amount, String remark, String serviceCharge) {
        try {
            return orderService.saveOrder(orderNo, amount, remark, serviceCharge);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 订单更换阿姨
     * @param orderNo
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.CHANGESTAFF_URL, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult changeStaff( String orderNo, Integer staffId, Integer isLocalStaff) {
        try {
            HttpSession session = SessionUtils.getSession();
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            return orderService.changeStaff(orderNo, staffId, isLocalStaff, userBean);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


    /**
     * 保存合同信息(合同图片上传)
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SAVECONTRACTINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveContractInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file, String orderNo, String imgIds) {
        try {
            return orderService.saveContractInfo(orderNo, file, imgIds);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 创建订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = RequestMappingURL.CREATEORDER_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult createOrder(HttpServletRequest request, OrderCreateForm form) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            form.setTenantId(userBean.getTenantId());

            //添加权限 Baron 20170525
            if (!"01".equals(userBean.getUserType()) && !"02".equals(userBean.getUserType())) {
                form.setAddAccount(userBean.getLoginAccount());
            }
            return orderService.createOrder(form, userBean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 订单管理保存或更新客户信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SAVECUSTOMERINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveOrUpdateCustomer(HttpServletRequest request, HttpServletResponse response, OrderCustomerInfoForm form) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return orderService.saveOrUpdateCustomer(form, userBean.getTenantId());
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 根据订单号获取订单阿姨详情
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.ORDERSTAFFINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult selectStaffByOrderNo(HttpServletRequest request, HttpServletResponse response, String orderNo) {
        try {
            return orderService.selectStaffInfoByOrderNo(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 本地订单支付定金
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.PAYDEPOSIT_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult payDeposit(HttpServletRequest request, HttpServletResponse response, String orderNo) {
        try {
            return orderService.payDeposit(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 本地订单支付尾款
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.PAYBALANCE_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult payBalance(HttpServletRequest request, HttpServletResponse response, String orderNo) {
        try {
            return orderService.payBalance(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 订单阿姨列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GETSTAFFLIST_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffList(HttpServletRequest request, HttpServletResponse response, StaffListForm form,
                                   @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
                                   @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize
    ) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            form.setTenantId(userBean.getTenantId());
            return orderService.getStaffsInfo(form, pageNumber, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 订单结单后更换阿姨
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.ORDERCHANGESTAFF_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult orderChangeStaff(HttpServletRequest request, HttpServletResponse response,
                                       String orderNo, Integer staffId, String tenantRemark
    ) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            return orderService.orderChangeStaff(orderNo, staffId, tenantRemark, userBean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除订单合同信息
     */
    @RequestMapping(value = RequestMappingURL.DELETECONTRACTINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult deleteContractInfo(HttpServletRequest request, HttpServletResponse response, Integer id) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return orderService.deleteContractInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 订单管理保存或更新服务信息
     */
    @RequestMapping(value = RequestMappingURL.SAVESERVICEINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveOrUpdateService(HttpServletRequest request, OrderServiceInfoForm form) {
    	log.info("=============saveServiceInfo for form :{}", JsonUtils.toJson(form));
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return orderService.saveOrUpdateService(form);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @RequestMapping(value = RequestMappingURL.DELETECONTRACR_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult deleteContract(HttpServletRequest request, HttpServletResponse response, Integer id) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return orderService.deleteContract(id);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 更新订单客户服务价格和年龄要求
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.UPDATE_ORDERS_CUSTOMERS_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult updateOrdersCustomersInfo(HttpServletRequest request, HttpServletResponse response,
                                                String orderNo, Integer salaryMin, Integer salaryMax, String wageRequirements) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("orderNo", orderNo);
            map.put("salaryMin", salaryMin);
            map.put("salaryMax", salaryMax);
            map.put("wageRequirements", wageRequirements);
            return orderService.updateOrdersCustomersInfo(map);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 本地订单-定金支付
     *
     * @param orderNo author Baron
     *                date 2017-04-27
     * @return
     */
    @RequestMapping(value = RequestMappingURL.DEPOSITPAYMENT_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult depositPayment(HttpServletRequest request, HttpServletResponse response, String orderNo,
                                     String type) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return orderService.depositPayment(orderNo, type, userBean.getUserName());
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 本地订单-定金修改
     *
     * @param orderNo author Baron
     *                date 2017-04-27
     * @return
     */
    @RequestMapping(value = RequestMappingURL.MODIFYDEPOSIT_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult modifyDeposit(HttpServletRequest request, HttpServletResponse response, String orderNo,
                                    String deposit) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return orderService.modifyDeposit(orderNo, deposit);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 本地订单-更换阿姨
     *
     * @param orderNo author Baron
     *                date 2017-05-02
     * @return
     */
    @RequestMapping(value = RequestMappingURL.CHANGEHS_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult changehs(HttpServletRequest request, HttpServletResponse response, String orderNo, String type, String remark) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return orderService.changehs(orderNo, type, remark, userBean);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 创建本地订单/阿姨列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GETCREATESTAFFLIST_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getCreateStaffsInfo(HttpServletRequest request, HttpServletResponse response, StaffListForm form,
                                          @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize
    ) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            form.setTenantId(userBean.getTenantId());
            return orderService.getCreateStaffsInfo(form, pageNumber, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


    /**
     * 订单详情-取消订单（状态为01待支付定金，02待面试，03待支付尾款）
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     * @xiehui
     */
    @RequestMapping(value = RequestMappingURL.CANCELORDERSTARUS_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult cancleOrderStatus(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam("orderNo") String orderNo) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return JsonResult.success(orderService.cancleOrderStatus(orderNo, userBean.getUserName()));
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }
}
