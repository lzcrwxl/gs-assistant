package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.CookieUtils;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.IndexTenantForm;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.json.IndexJsonData;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.redis.consts.RedisKey;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.TenantService;
import com.fbee.modules.service.UserService;
import com.fbee.modules.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 贺章鹏
 * @ClassName: IndexController
 * @Description: 首页控制器
 * @date 2016年12月28日 下午12:02:25
 */
@Controller
@RequestMapping(RequestMappingURL.INDEX_BASE_URL)
public class IndexController {
    @Autowired
    TenantService tenantService;

    private JedisTemplate redis = JedisUtils.getJedisTemplate();

    @RequestMapping(value = RequestMappingURL.INFO_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getIndexInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            IndexJsonData indexJsonData = tenantService.getIndexInfo(userBean.getTenantId());
            return JsonResult.success(indexJsonData);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取租户二维码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.GETQRCODE_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getQrCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return tenantService.getQrCode(userBean.getTenantId());
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @RequestMapping(value = RequestMappingURL.MODIFY_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult modifyIndex(HttpServletRequest request, HttpServletResponse response, IndexTenantForm indexTenantForm) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return tenantService.modifyIndex(userBean.getTenantId(), indexTenantForm);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @Guest
    @RequestMapping(value = "getSession.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonResult getSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = SessionUtils.getSession(request);
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            String ua = request.getHeader(Constants.UA);
            if (org.apache.commons.lang3.StringUtils.isBlank(ua)) {
                ua = CookieUtils.getCookie(request, Constants.UA);
            }
            if (org.apache.commons.lang3.StringUtils.isBlank(ua)) {
                ua = request.getParameter(Constants.QR_UA);
                String str = redis.get(RedisKey.User.UA_QR.getKey(ua));
                userBean = org.apache.commons.lang3.StringUtils.isBlank(str) ? null : JsonUtils.fromJson(str, UserBean.class);
                session.setAttribute(Constants.USER_SESSION, userBean);
                redis.del(RedisKey.User.UA_QR.getKey(ua));
            }else {
                String str = redis.get(RedisKey.User.UA.getKey(ua));
                userBean = org.apache.commons.lang3.StringUtils.isBlank(str) ? null : JsonUtils.fromJson(str, UserBean.class);
                session.setAttribute(Constants.USER_SESSION, userBean);
            }
        }
        return tenantService.getDomain(userBean);
    }

    /**
     * 查询支付结果
     *
     * @param request
     * @param response
     * @param flowNo
     * @return
     */
    @RequestMapping(value = "getPayResult.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonResult getPayResult(HttpServletRequest request, HttpServletResponse response, String flowNo) {
        return tenantService.getPayResult(flowNo);

    }

}
