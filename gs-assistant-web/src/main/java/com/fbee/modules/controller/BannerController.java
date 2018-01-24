package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.service.TenantService;
import com.fbee.modules.service.WebsiteService;
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
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(RequestMappingURL.BANNER_BASE_URL)
public class BannerController {

    @Autowired
    TenantService tenantService;

    @Autowired
    WebsiteService websiteService;


    /**
     * 获取系统banner图
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.BANNER_SYSTEM_LIST, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getSystemBanners(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SMSCODE_TIMEOUT);
            }
            //tenantId = 0 表示系统图片
            return tenantService.findBannerList(0);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * @MethodName:getBannerInfo
     * @Type:BannerController
     * @Description:网站管理--banner管理--首页
     * @Return:JsonResult
     * @Param:@param request
     * @Param:@param response
     * @Param:@return
     * @Thrown:
     * @Date:Sep 20, 2017 7:10:03 AM
     */
    @RequestMapping(value = RequestMappingURL.BANNER_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getBannerInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SMSCODE_TIMEOUT);
            }
            Integer tenantId = userBean.getTenantId();
            return tenantService.getBannerInfo(tenantId);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * @MethodName:save
     * @Type:BannerController
     * @Description:banner 保存--图片信息只保存图片在图片服务器上的地址
     * @Return:JsonResult
     * @Param:@param request
     * @Param:@param response
     * @Param:@param bannerPath 图片服务器地址
     * @Param:@param isDefault  是否默认
     * @Param:@return
     * @Thrown:
     * @Date:Sep 15, 2017 10:44:42 AM
     */
    @RequestMapping(value = RequestMappingURL.BANNER_UPDATE, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult save(HttpServletRequest request, HttpServletResponse response, String bannerPath, String isDefault) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SMSCODE_TIMEOUT);
            }
            Integer tenantId = userBean.getTenantId();
            return websiteService.bannerSave(bannerPath, tenantId, isDefault);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


    /**
     * 上传banner
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public JsonResult upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        String path = "C:/upload/";
        String fileName = new Date().getTime() + ".jpg";
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("fileUrl", path + fileName);
        return JsonResult.success(map);
    }


}
