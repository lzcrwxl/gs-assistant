package com.fbee.modules.controller;

import com.fbee.modules.bean.UserBean;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.ErrorCode;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.model.SubscribeInfo;
import com.fbee.modules.service.SubscribeService;
import com.fbee.modules.service.basic.ServiceException;
import com.fbee.modules.utils.JsonUtils;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/subscribe")
public class SubscribeController extends BaseController {


    @Autowired
    private SubscribeService subscribeService;

    /**
     * 设置订阅提醒
     *
     * @return
     */
    @Guest
    @RequestMapping(value = "/setting", method = RequestMethod.POST)
    public JsonResult onSubscribe(@RequestBody SubscribeInfo info) {
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {//租户信息
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }

        info.setTenantUserId(userBean.getUserId());
        info.setUserName(userBean.getUserName());
        info.setOpenid(userBean.getOpenid());
        try {
            subscribeService.settingSubscribe(info);
        } catch (ServiceException e) {
            return JsonResult.failure(ResultCode.ERROR, e.getMessage());
        }

        return JsonResult.success(null);
    }


    /**
     * 查询当前订阅
     *
     * @return
     */
    @Guest
    @RequestMapping(value = "", method = RequestMethod.GET)
    public JsonResult get() {
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {//租户信息
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }
        try {
            return JsonResult.success(subscribeService.get(userBean.getUserId()));
        } catch (ServiceException e) {
            return JsonResult.failure(ResultCode.ERROR, e.getMessage());
        }

    }


}
