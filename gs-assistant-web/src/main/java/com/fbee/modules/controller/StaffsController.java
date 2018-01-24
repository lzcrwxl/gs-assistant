package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.ErrorCode;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.*;
import com.fbee.modules.form.extend.StaffServiceItemform;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.redis.consts.RedisKey;
import com.fbee.modules.service.StaffsService;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.validate.StaffFormValidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 贺章鹏
 * @ClassName: StaffsController
 * @Description: 员工管理控制器
 * @date 2016年12月28日 下午12:04:07
 */
@Controller
@RequestMapping(RequestMappingURL.STAFF_BASE_URL)
public class StaffsController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(StaffsController.class);

    @Autowired
    StaffsService staffsService;

    private JedisTemplate redis = JedisUtils.getJedisTemplate();
    /**
     * 保存/修改 家政员基础信息
     *
     * @param staffBaseInfoForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SAVE_STAFF_BASE_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveBaseInfo(StaffBaseInfoForm staffBaseInfoForm, Integer isAdd) {
        try {

            HttpSession session = SessionUtils.getSession();
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {//租户信息
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            if (isAdd != null && isAdd.equals(1)) {
                if (StringUtils.isBlank(staffBaseInfoForm.getCertImage())) {
                    return JsonResult.failure(ResultCode.Staff.STAFF_CERT_IMAGE_IS_NULL, "身份证图片为空，请重新读卡");
                }
                if (StringUtils.isBlank(staffBaseInfoForm.getCertNo())) {
                    return JsonResult.failure(ResultCode.Staff.STAFF_CERT_CERT_NO_IS_NULL);
                }
            }
            JsonResult jsonResult = StaffFormValidate.validAddBaseInfo(staffBaseInfoForm);
            if (!jsonResult.isSuccess()) {//阿姨详情是否填完
                return jsonResult;
            }

            log.info(JsonUtils.toJson(staffBaseInfoForm));

            JsonResult js = staffsService.addBaseInfo(userBean.getTenantId(), staffBaseInfoForm, isAdd);
            System.out.println("======= save base info: " + JsonUtils.toJson(js));
            return js;
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 保存或修改银行卡信息（由于家政员仅一条银行卡信息）
     *
     * @param request
     * @param response
     * @param staffBankForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SAVE_STAFF_BANK_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveStaffBank(HttpServletRequest request, HttpServletResponse response, StaffBankForm staffBankForm) {
        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            JsonResult jsonResult = StaffFormValidate.validBankInfo(staffBankForm);
            if (!jsonResult.isSuccess()) {
                return jsonResult;
            }

            log.info(JsonUtils.toJson(staffBankForm));

            return staffsService.saveStaffBank(userBean.getTenantId(), staffBankForm);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 保存或修改保单信息
     *
     * @param request
     * @param response
     * @param staffPolicyForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SAVE_STAFF_POLICY_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveStaffPolicy(HttpServletRequest request, HttpServletResponse response, StaffPolicyForm staffPolicyForm) {
        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            JsonResult jsonResult = StaffFormValidate.validPolicyInfo(staffPolicyForm);
            if (!jsonResult.isSuccess()) {
                return jsonResult;
            }

            log.info(JsonUtils.toJson(staffPolicyForm));

            return staffsService.saveStaffPolicy(userBean.getTenantId(), staffPolicyForm);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


    /**
     * 保存或修改求职信息
     *
     * @param staffJobForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SAVE_STAFF_JOB_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveStaffJob(StaffJobForm staffJobForm) {
        try {

            HttpSession session = SessionUtils.getSession();
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            JsonResult jsonResult = StaffFormValidate.validJobInfo(staffJobForm);
            if (!jsonResult.isSuccess()) {
                return jsonResult;
            }
            System.out.println("add job : " + JsonUtils.toJson(staffJobForm));
            log.info(JsonUtils.toJson(staffJobForm));

            JsonResult js = staffsService.saveStaffJob(userBean.getTenantId(), staffJobForm);
            System.out.println(" ======== save job: " + JsonUtils.toJson(js));
            return js;
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除工种
     *
     * @param request
     * @param response
     * @param staffJobForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.DEL_STAFF_ITEMS_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult delStaffItemsInfo(HttpServletRequest request, HttpServletResponse response, StaffJobForm staffJobForm,
                                        @RequestParam(value = "serviceItemCode", defaultValue = Constants.DEFAULT_PAGE_NO) String serviceItemCode) {
        HttpSession session = SessionUtils.getSession(request);
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }
        log.info(staffJobForm.toString());
        if (null != serviceItemCode && !"".equals(serviceItemCode)) {
            StaffServiceItemform staffServiceItemform = new StaffServiceItemform();
            staffServiceItemform.setServiceItemCode(serviceItemCode);
            List<StaffServiceItemform> list = new ArrayList<StaffServiceItemform>();
            list.add(staffServiceItemform);
            staffJobForm.setServiceItems(list);
            staffsService.delStaffItemsInfo(userBean.getTenantId(), staffJobForm);
        }
        return JsonResult.success(null);
    }

    /**
     * 保存工种信息
     *
     * @param staffJobForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SAVE_ITEMS_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveStaffItemsInfo(StaffJobForm staffJobForm) {
        try {

            HttpSession session = SessionUtils.getSession();
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            JsonResult jsonResult = StaffFormValidate.validJobInfo(staffJobForm);
            if (!jsonResult.isSuccess()) {
                return jsonResult;
            }

            System.out.println("add staffItem:" + JsonUtils.toJson(staffJobForm));
            log.info(JsonUtils.toJson(staffJobForm));

            JsonResult js = staffsService.saveStaffItemsInfo(userBean.getTenantId(), staffJobForm);
            System.out.println(" === save items info: " + JsonUtils.toJson(js));
            return js;
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @RequestMapping(value = RequestMappingURL.SAVE_STAFF_CERTS_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView saveStaffCert(HttpServletRequest request, HttpServletResponse response,
                                      StaffCertForm staffCertForm,
                                      String callbackUrl, String valid) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:" + callbackUrl);
        try {
            System.out.println("进入新增证书方法");
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                mv.addObject("JsonResult", JsonResult.failure(ResultCode.SESSION_TIMEOUT));
                return mv;
            }
            JsonResult jsonResult = StaffFormValidate.validCertInfo(staffCertForm);
            if (!jsonResult.isSuccess()) {
                mv.addObject("JsonResult", jsonResult);
                return mv;
            }

            log.info(JsonUtils.toJson(staffCertForm));
            jsonResult = staffsService.saveStaffCert(userBean.getTenantId(), staffCertForm, valid);
            mv.addObject("JsonResult", jsonResult);
            return mv;
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            mv.addObject("JsonResult", JsonResult.failure(ResultCode.ERROR));
            return mv;
        }
    }


    /**
     * 分页查询家政员列表
     *
     * @param request
     * @param response
     * @param pageNumber
     * @param pageSize
     * @param staffQueryForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.QUERY_STAFF, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult queryStaff(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) int pageNumber,
                                 @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
                                 StaffQueryForm staffQueryForm) {
        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            log.info(JsonUtils.toJson(staffQueryForm));

            return staffsService.queryStaff(userBean.getTenantId(), staffQueryForm, pageNumber, pageSize);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取家政员详情信息类表
     *
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_STAFF_DETAILS, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getDetails(Integer staffId, String orderNo) {
        try {
            if (staffId == null) {
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            HttpSession session = SessionUtils.getSession();
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            log.info("getDetails params staffId is " + staffId);

            return staffsService.getDetails(userBean.getTenantId(), staffId, orderNo);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取家政员详情信息类表
     *
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_STAFF_DETAILS_PART_BASE, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getDetailsBase(Integer staffId, String orderNo) {
        try {
            if (staffId == null) {
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            HttpSession session = SessionUtils.getSession();
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            HttpServletRequest request = SessionUtils.getRequest();
            String ua = request.getParameter(Constants.QR_UA);
            if(StringUtils.isNotBlank(ua)){
            	String str = redis.get(RedisKey.User.UA_QR.getKey(ua));
            	if(StringUtils.isBlank(str)){
            		return JsonResult.failure(ErrorCode.RESUME_ERROR);
            	}
            	UserBean userBeanQA = JsonUtils.fromJson(str, UserBean.class);
            	if(!userBean.getUserId().equals(userBeanQA.getUserId())){
            		return JsonResult.failure(ErrorCode.RESUME_ERROR);
            	}
            }
            log.info("getDetails params staffId is " + staffId);

            return staffsService.getDetailsPartBase(userBean.getTenantId(), staffId, orderNo);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取家政员详情信息类表
     *
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_STAFF_DETAILS_PART_MEDIA, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getDetailsMediaPart(Integer staffId) {
        try {
            if (staffId == null) {
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            HttpSession session = SessionUtils.getSession();
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            HttpServletRequest request = SessionUtils.getRequest();
            String ua = request.getParameter(Constants.QR_UA);
            if(StringUtils.isNotBlank(ua)){
            	String str = redis.get(RedisKey.User.UA_QR.getKey(ua));
            	if(StringUtils.isBlank(str)){
            		return JsonResult.failure(ErrorCode.RESUME_ERROR);
            	}
            	UserBean userBeanQA = JsonUtils.fromJson(str, UserBean.class);
            	if(!userBean.getUserId().equals(userBeanQA.getUserId())){
            		return JsonResult.failure(ErrorCode.RESUME_ERROR);
            	}
            }
            log.info("getDetails params staffId is " + staffId);

            return staffsService.getDetailsPartMedia(userBean.getTenantId(), staffId);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取家政员详情信息类表
     *
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_STAFF_DETAILS_PART_FINANCE, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getDetailsFinancePart(Integer staffId) {
        try {
            if (staffId == null) {
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            HttpSession session = SessionUtils.getSession();
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            log.info("getDetails params staffId is " + staffId);

            return staffsService.getDetailsPartFinance(userBean.getTenantId(), staffId);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取家政员详情信息类表
     *
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_STAFF_DETAILS_PART_RECORD, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getDetailsRecordPart(Integer staffId) {
        try {
            if (staffId == null) {
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            HttpSession session = SessionUtils.getSession();
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            log.info("getDetails params staffId is " + staffId);

            return staffsService.getDetailsPartRecord(userBean.getTenantId(), staffId);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


    /**
     * 获取家政员银行信息类
     *
     * @param request
     * @param response
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_STAFF_BANKINFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getBankInfo(HttpServletRequest request, HttpServletResponse response,
                                  Integer staffId) {
        try {
            if (staffId == null) {
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            log.info("getDetails params staffId is " + staffId);
            return staffsService.getBankInfo(userBean.getTenantId(), staffId);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取家政员保单
     *
     * @param request
     * @param response
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_STAFF_POLICYLIST, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getPolicyList(HttpServletRequest request, HttpServletResponse response,
                                    Integer staffId) {
        try {
            if (staffId == null) {
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            log.info("getDetails params staffId is " + staffId);
            return staffsService.getPolicyList(userBean.getTenantId(), staffId);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取家政员财务记录
     *
     * @param request
     * @param response
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_STAFF_FINANCEINFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getFinanceInfo(HttpServletRequest request, HttpServletResponse response,
                                     Integer staffId) {
        try {
            if (staffId == null) {
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            log.info("getDetails params staffId is " + staffId);
            return staffsService.getFinanceInfo(userBean.getTenantId(), staffId);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取家政员派工记录
     *
     * @param request
     * @param response
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GET_STAFF_WORKLIST, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getWorkList(HttpServletRequest request, HttpServletResponse response,
                                  Integer staffId) {
        try {
            if (staffId == null) {
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            log.info("getDetails params staffId is " + staffId);
            return staffsService.getWorkList(userBean.getTenantId(), staffId);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 设置家政员默认头像信息
     *
     * @param request
     * @param response
     * @param staffId
     * @param imageId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.STAFF_IMGAE_DEFAULT, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult imageDefault(HttpServletRequest request, HttpServletResponse response,
                                   Integer staffId, Integer imageId) {
        try {

            if (staffId == null || imageId == null) {
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }

            log.info("imageDefault params staffId is " + staffId + ",imageId is " + imageId);

            return staffsService.addImageDefault(staffId, imageId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取服务列表
     *
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SERITEMS_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffServiceItemList(HttpServletRequest request, HttpServletResponse response) {
        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return staffsService.getStaffServiceItemList(userBean.getTenantId());
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取服务工种
     *
     * @param serviceItemCode
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SERITEM_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult selectByPrimaryKey(HttpServletRequest request, HttpServletResponse response, String serviceItemCode) {
        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return staffsService.selectByPrimaryKey(serviceItemCode, userBean.getTenantId());
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @RequestMapping(value = RequestMappingURL.UPDATESERITEM_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult updateByPrimaryKeySelective(HttpServletRequest request, HttpServletResponse response, ServiceItemsForm serviceItemsForm,
                                                  @RequestParam(value = "imageUrl", required = false) MultipartFile imageUrl) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            serviceItemsForm.setTenantId(userBean.getTenantId());
            return staffsService.editServiceItem(serviceItemsForm, imageUrl);
        } catch (Exception e) {
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @RequestMapping(value = RequestMappingURL.STAFFRECOMMEND_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult selectRecommendList(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return staffsService.selectRecommendList(userBean.getTenantId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @RequestMapping(value = RequestMappingURL.STAFFSINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult selectStaffInfoList(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) Integer pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) Integer pageSize) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return staffsService.selectStaffInfoList(userBean.getTenantId(), pageNumber, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @RequestMapping(value = RequestMappingURL.STAFFINFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffInfoByStaffId(HttpServletRequest request, HttpServletResponse response, String staffId) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return staffsService.getStaffInfoByStaffId(userBean.getTenantId(), Integer.valueOf(staffId));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @RequestMapping(value = RequestMappingURL.STAFFSINFOLIKE_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffInfoLikeStaffName(HttpServletRequest request, HttpServletResponse response, String staffName,
                                                @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NO) Integer pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE) Integer pageSize) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return staffsService.getStaffInfoLikeStaffName(userBean.getTenantId(), staffName, pageNumber, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 保存家政员上下架
     *
     * @param request
     * @param response
     * @param staffId
     * @param onOff
     * @return
     */
    @RequestMapping(value = RequestMappingURL.STAFF_ON_OFF_SHELF, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult staffOnOffShelf(HttpServletRequest request, HttpServletResponse response,
                                      Integer staffId, String onOff) {
        try {

            if (staffId == null || StringUtils.isBlank(onOff)) {
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }

            log.info("imageDefault params staffId is " + staffId + ",onOff is " + onOff);

            return staffsService.staffOnOff(staffId, onOff);
        } catch (Exception e) {

            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @RequestMapping(value = RequestMappingURL.SAVESTAFFRECOMMEND_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveRecommend(HttpServletRequest request, HttpServletResponse response, RecommendForm recommendForm) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            recommendForm.setTenantId(userBean.getTenantId());
            return staffsService.saveRecommend(recommendForm);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


    @RequestMapping(value = RequestMappingURL.UPDATESTAFFRECOMMEND_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult updateRecommend(HttpServletRequest request, HttpServletResponse response, RecommendForm recommendForm) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            recommendForm.setTenantId(userBean.getTenantId());
            return staffsService.updateRecommend(recommendForm);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 上传视频/图片
     *
     * @param request
     * @param staffId
     * @return
     */
    @RequestMapping(value = RequestMappingURL.UPLOAD_MEDIA_PATH, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadVideoPath(HttpServletRequest request, @RequestParam(value = "videoPath") String[] videoPath, @RequestParam(value = "imagePath") String[] imagePath, Integer staffId, Integer isWhole) {
        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return staffsService.uploadVideoPath(userBean, videoPath, imagePath, staffId, isWhole);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }

    }

    /**
     * 删除照片墙（物理删除）<br>
     * 删除一条记录<br>
     * 删除服务上对应的照片
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = RequestMappingURL.DELETE_MEDIA_PATH, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult deletePhotoWall(HttpServletRequest request, Integer id) {
        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return staffsService.deletePhotoWall(userBean, id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 设置封面
     *
     * @param request
     * @param id      主键id
     * @param staffId 外键
     * @return
     */
    @RequestMapping(value = RequestMappingURL.SET_COVER, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult setCover(HttpServletRequest request, Integer staffId, Integer id) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return staffsService.setCover(userBean, staffId, id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 保存阿姨财务记录
     *
     * @param request
     * @param staffFinancialForm
     * @return
     */
    @RequestMapping(value = RequestMappingURL.STAFF_FINANCIAL_RECORD, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveFinancialRecord(HttpServletRequest request,
                                          StaffFinancialForm staffFinancialForm, Integer staffId) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            if (StringUtils.isBlank(staffFinancialForm.getPayType())) {
                log.info(String.format("payType is null [%s]", JsonUtils.toJson(staffFinancialForm)));
                return JsonResult.failure(ResultCode.PARAMS_ERROR);
            }

            staffFinancialForm.setStaffId(staffId);//阿姨id
            staffFinancialForm.setTenantId(userBean.getTenantId());
            staffFinancialForm.setAddAccount(userBean.getUserName());
            return staffsService.saveFinancialRecord(staffFinancialForm);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 阿姨工作安排表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = RequestMappingURL.STAFF_WORK_LIST, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getStaffWorkList(HttpServletRequest request, Integer staffId) {
        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return JsonResult.success(staffsService.getStaffWorkList(staffId));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * @param request
     * @param response
     * @param staffId
     * @param id
     * @return
     * @author xiehui 删除未通过的证书
     */
    @RequestMapping(value = RequestMappingURL.LOGIC_DELETE_CERT, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult logicDeleteCert(HttpServletRequest request, HttpServletResponse response, Integer staffId,
                                      Integer id) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return staffsService.deleteCert(staffId, id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


    /**
     * @param request
     * @param response
     * @param staffId
     * @return
     * @author xiehui 证书校验
     */
    @RequestMapping(value = RequestMappingURL.CERT_VALID, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult certValid(HttpServletRequest request, HttpServletResponse response, Integer staffId,
                                String authenticateGrade, String certType) {

        try {

            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return staffsService.certValid(staffId, authenticateGrade, certType);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

}
