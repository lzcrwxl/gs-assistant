package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.Status;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.interceptor.anno.Auth;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.BankService;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.DictionarysCacheService;
import com.fbee.modules.utils.DictionariesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author 贺章鹏
 * @ClassName: CommonController
 * @Description: 公用部分
 * 包含：【省市区】、【服务工种】、【个人特点】、【服务类型】、【技能特点】、【服务价格单位】、
 * 【阿姨查询条件：价格、经验、年龄段、属相、学历、籍贯】
 * @date 2017年1月20日 下午1:45:09
 */
@Controller
@RequestMapping(RequestMappingURL.COMMON_BASE_URL)
public class CommonController {

    @Autowired
    DictionarysCacheService dictionarysCacheService;

    @Autowired
    CommonService commonService;

    @Autowired
    BankService bankService;

    /**
     * 获取当前时间
     *
     * @return
     */
    @Guest
    @RequestMapping(value = "/now", method = RequestMethod.GET)
    @ResponseBody
    public Date now() {
        return new Date();
    }


    /**
     * 获取字典型参数
     * typeCode
     *
     * @param request
     * @param response
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.GET_DICTIONARY_DATA, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getDictionaryData(HttpServletRequest request, HttpServletResponse response, @PathVariable("typeCode") String typeCode) {
        try {
            if (StringUtils.isBlank(Status.getDesc(typeCode))) {
                JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            return dictionarysCacheService.getDictionaryData(Status.getDesc(typeCode));
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取区域值--省市区
     * pcode:父级code
     *
     * @param request
     * @param response
     * @param typeCode
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.GET_AREA_DATA, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getAreaData(HttpServletRequest request, HttpServletResponse response
            , @PathVariable("typeCode") String typeCode, String pcode) {
        try {
            if (StringUtils.isBlank(Status.getDesc(typeCode))) {
                JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            return dictionarysCacheService.getAreaData(Status.getDesc(typeCode), pcode);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取服务工种的技能特定、个人特点、服务工种的服务类型
     *
     * @param request
     * @param response
     * @param typeCode
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.GET_SKILLS_DATA, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getSKillsData(HttpServletRequest request, HttpServletResponse response
            , @PathVariable("typeCode") String typeCode, String itemCode, String level) {
        try {
            if (StringUtils.isBlank(Status.getDesc(typeCode))) {
                JsonResult.failure(ResultCode.PARAMS_ERROR);
            }
            return dictionarysCacheService.getSKillsData(Status.getDesc(typeCode), itemCode, level);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 生成二维码
     *
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.GET_TWODIMENSION_CODE_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getQRCode(HttpServletRequest request, String info, String code, String domain) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return JsonResult.success(commonService.getQRCode(info, code, domain, userBean.getLoginAccount()));
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }
    /**
     * 生成简历箱阿姨详情二维码
     *
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.GET_RESUM_DETAIL_TWODIMENSION_CODE_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getResumeDetailQRCode(HttpServletRequest request, String staffId,String orderNo,String resumeId) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return JsonResult.success(commonService.getResumeDetailQRCode(staffId,orderNo,resumeId));
        } catch (Exception e) { 
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }
    /**
     * 银行code
     *
     * @param request
     * @param response
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.BANK_CODE, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult bankCode(HttpServletRequest request, HttpServletResponse response) {

        try {
            return bankService.getBank();
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /***
     * 获取服务收入列表
     * @return
     */
    @Guest
    @RequestMapping(value = "getServiceIncome", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getServiceIncome() {
        return JsonResult.success(DictionariesUtil.getServiceIncomeList());
    }
    
    @Auth
    @RequestMapping(value = "refresh")
    public JsonResult refreshSession(){
    	HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }
    	return commonService.refreshSession();
    }

}
