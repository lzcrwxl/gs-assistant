package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.form.ReserveOrderDetailsForm;
import com.fbee.modules.form.ReserveOrdersForm;
import com.fbee.modules.form.StaffListForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.OrderShareService;
import com.fbee.modules.service.ReserveOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author fry
 * @Description：预约订单控制层
 * @date 2017年2月6日 下午2:48:46
 */
@Controller
@RequestMapping(RequestMappingURL.ORDER_BASE_URL)
public class ReserveOrdersController {

    @Autowired
    ReserveOrdersService reserveOrdersService;
    @Autowired
    OrderShareService OrderShareService;

    /**
     * 获取预约订单列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_RESERVE_ORDERS_URL, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult getReserveOrdersList(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
                                           @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
                                           ReserveOrdersForm reserveOrdersForm) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            Log.info(reserveOrdersForm.toString());

            return reserveOrdersService.getReserveOrdersList(userBean.getTenantId(), userBean, reserveOrdersForm, pageNumber, pageSize);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取预约订单未处理数量
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_RESERVE_ORDERS_COUNT, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getReserveOrdersCount() {
        try {
            HttpSession session = SessionUtils.getSession();
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return reserveOrdersService.getReserveOrdersList(userBean.getTenantId(), userBean);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 查询预约详情-预约信息
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_RESERVE_URL, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult selectReserveByOrderNo(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(value = "orderNo") String orderNo) {

        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            return reserveOrdersService.selectReserveByOrderNo(orderNo);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 查询预约详情-客户信息
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_RESERVE_MEMBER_URL, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult selectMemberByOrderNo(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(value = "orderNo") String orderNo) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return reserveOrdersService.selectMemberByOrderNo(orderNo);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 查询预约详情-服务信息
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_RESERVE_SERVICE_URL, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult selectServiceByOrderNo(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(value = "orderNo") String orderNo) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return reserveOrdersService.selectServiceByOrderNo(orderNo);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 查询预约详情-阿姨信息
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_RESERVE_STAFF_URL, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult selectStaffByOrderNo(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(value = "orderNo") String orderNo) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return reserveOrdersService.selectStaffByOrderNo(orderNo);

        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 预约订单详情-保存客户信息到客户管理中
     *
     * @param request
     * @param response
     * @param orderNo
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SAVE_RESERVE_MEMBER_URL, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult saveMemberByOrderNo(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "orderNo") String orderNo,
                                          ReserveOrderDetailsForm reserveOrderDetailsForm) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return reserveOrdersService.saveMemberByOrderNo(orderNo, reserveOrderDetailsForm, userBean.getLoginAccount());

        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 预约订单详情-保存客户信息到订单客户信息中
     *
     * @param request
     * @param response
     * @param reserveOrderDetailsForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.UPDATE_MEMBER_URL, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult updateMemberToOrder(HttpServletRequest request, HttpServletResponse response,
                                          ReserveOrderDetailsForm reserveOrderDetailsForm) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return reserveOrdersService.saveMemberToOrder(reserveOrderDetailsForm);

        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


    /**
     * 预约订单详情-保存服务信息到订单客户信息中
     *
     * @param request
     * @param response
     * @param reserveOrderDetailsForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.UPDATE_SERVICE_URL, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult updateServiceToOrder(HttpServletRequest request, HttpServletResponse response,
                                           ReserveOrderDetailsForm reserveOrderDetailsForm) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return reserveOrdersService.saveServiceToOrder(reserveOrderDetailsForm);

        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 预约订单详情-修改备注
     *
     * @return
     */
    @RequestMapping(value = RequestMappingURL.UPDATE_RESERVE_REMARK_URL, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateReserveRemark(ReserveOrderDetailsForm reserveOrderDetailsForm) {
        try {
            HttpSession session = SessionUtils.getSession();
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return reserveOrdersService.modifyRemark(reserveOrderDetailsForm);

        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


    /**
     * 预约订单详情-完成处理(取消/放弃订单)
     *
     * @param request
     * @param response
     * @param reserveOrderDetailsForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.UPDATE_RESERVE_URL, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult updateReserveByOrderNo(HttpServletRequest request, HttpServletResponse response,
                                             ReserveOrderDetailsForm reserveOrderDetailsForm) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            if (!"01".equals(userBean.getUserType()) && !"02".equals(userBean.getUserType())) {
                reserveOrderDetailsForm.setAddAccount(userBean.getLoginAccount());
            }
            reserveOrderDetailsForm.setUserId(userBean.getUserId());
            reserveOrderDetailsForm.setUserName(userBean.getUserName());
            reserveOrderDetailsForm.setUserType(userBean.getUserType());
            reserveOrderDetailsForm.setTenantId(userBean.getTenantId());
            return reserveOrdersService.updateReserveByOrderNo(reserveOrderDetailsForm);

        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 预约订单-生成订单
     *
     * @param request
     * @param response
     * @param reserveOrderDetailsForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SAVE_RESERVE_URL, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult saveReserveByOrderNo(HttpServletRequest request, HttpServletResponse response,
                                           ReserveOrderDetailsForm reserveOrderDetailsForm) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            reserveOrderDetailsForm.setTenantId(userBean.getTenantId());
            reserveOrderDetailsForm.setUserId(userBean.getUserId());
            reserveOrderDetailsForm.setUserName(userBean.getUserName());
            reserveOrderDetailsForm.setUserType(userBean.getUserType());

            //添加权限 Baron 20170525
            if (!"01".equals(userBean.getUserType()) && !"02".equals(userBean.getUserType())) {
                reserveOrderDetailsForm.setAddAccount(userBean.getLoginAccount());
            }

            return reserveOrdersService.saveReserveByOrderNo(reserveOrderDetailsForm);

        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 点击"选择阿姨"</br>
     * 校验通过</br>获取阿姨列表信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_RESERVE_STAFF_LIST, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffsInfo(HttpServletRequest request, StaffListForm form,
                                    @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize) {
        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            form.setTenantId(userBean.getTenantId());
            return reserveOrdersService.getStaffsInfo(form, pageNumber, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }

    }

    /**
     * 预约订单详情-更换阿姨
     *
     * @param request
     * @param response
     * @param orderNo
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.UPDATE_STAFF_URL, method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public JsonResult updateStaffByOrderNo(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(value = "orderNo") String orderNo,
                                           @RequestParam(value = "staffId") Integer staffId) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return reserveOrdersService.updateStaffByOrderNo(orderNo, staffId);

        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

}
