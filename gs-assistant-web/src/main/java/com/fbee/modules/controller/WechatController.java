package com.fbee.modules.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.ErrorCode;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.interceptor.anno.Auth;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.dao.TenantsUsersMapper;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.redis.consts.RedisKey;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.bean.OAuthTokenBean;
import com.fbee.modules.wechat.bean.WechatUserinfoBean;
import com.fbee.modules.wechat.config.WechatConfig;
import com.fbee.modules.wechat.core.TokenSingleton;
import com.fbee.modules.wechat.core.WechatAuthrize;

@Controller
@RequestMapping({RequestMappingURL.WECHAT_BASE_URL})
public class WechatController {

    private final static Logger log = LoggerFactory.getLogger(WechatController.class);
    private final static String UTF8 = "UTF8";

    @Autowired
    private TenantsUsersMapper userDao;

    private JedisTemplate redis = JedisUtils.getJedisTemplate();

    @Guest
    @RequestMapping(value = {RequestMappingURL.GET_WECHAT_INFO}, method = {org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public JsonResult getWechatInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("urlpath") String urlpath) {
        return JsonResult.success(null);
    }

    /**
     * 获取微信授权用户信息
     * @return
     */
    @Auth
    @RequestMapping(value = "/info", method = org.springframework.web.bind.annotation.RequestMethod.GET)
    @ResponseBody
    public JsonResult getWechatInfo() {
        String json = redis.get(RedisKey.User.UA_WECHAT.getKey(SessionUtils.getHeaderValue(Constants.UA)));
        if (StringUtils.isNotBlank(json)) {
            return JsonResult.success(JsonUtils.fromJson(json, WechatUserinfoBean.class));
        }
        return JsonResult.failure(ErrorCode.WX_GET_USER_INFO_ERROR);
    }
    /**
     * @param request
     * @param code
     * @return
     */
    @Guest
    @RequestMapping(value = "/auth/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView wechatAuthLogin(ModelAndView mav, HttpServletRequest request, String code, String state,
                                        String redirect) throws UnsupportedEncodingException {
        String rwurl = "/error.html";
        if (StringUtils.isBlank(code)) {
            log.warn("请求需要code参数，需微信授权后拿到code访问");
            mav.addObject("e", ErrorCode.WX_AUTH_ERROR);
            mav.setView(new RedirectView(rwurl, false));
            return mav;
        }
        //获取授权用户信息
        OAuthTokenBean token = WechatAuthrize.getAccessToken(code);
        if (token == null) {
            log.warn("获取token失败");
            mav.addObject("e", ResultCode.AUTHORIZE_ERROR);
            mav.setView(new RedirectView(rwurl, false));
            return mav;

        }
        String openId = token.getOpenId();
        if (StringUtils.isEmpty(openId)) {
            log.warn("获取openid为空");
            mav.addObject("e", ErrorCode.WX_OPENID_AUTH_ERROR);
            mav.setView(new RedirectView(rwurl, false));
            return mav;
        }

        /**
         * 获取微信用户信息
         */
        WechatUserinfoBean userinfo = WechatAuthrize.getWechatUserinfo(token.getAccessToken(), openId, WechatConfig.LANG_ZH_CN);

        if (userinfo == null) {
            log.warn("用户微信信息获取失败··");
            mav.addObject("e", ErrorCode.WX_GET_USER_INFO_ERROR);
            mav.setView(new RedirectView(rwurl, false));
            return mav;
        }
        redis.set(RedisKey.User.UA_WECHAT.getKey(token.getAccessToken()), JsonUtils.toJson(userinfo),30 * 24 * 60 * 60);

        /**
         * 将用户信息存储到redis, session
         * 1. 根据openid获取用户信息
         *    > 不存在则需登陆绑定
         *    > 存在则放入session/redis
         */
        List<TenantsUsersEntity> usersList = userDao.getByOpenId(openId);
        StringBuilder redirectUrl = new StringBuilder();
        //跳转首页
        log.info("跳转到首页?=" + redirect);
        redirectUrl.append("/index.html?token=").append(token.getAccessToken());
        if(usersList == null || usersList.size() < 1){
            redirectUrl.append("&uid=null");
            redirectUrl.append("&to=");
            if (StringUtils.isNotBlank(redirect)) {
                redirectUrl.append(URLEncoder.encode(redirect, "UTF8"));
            }

        }else{
            TenantsUsersEntity usersEntity = usersList.get(0);
            if("1".equals(usersEntity.getIsUsable())){
                UserBean userBean = new UserBean();
                userBean.setLoginAccount(usersEntity.getLoginAccount());
                userBean.setTenantId(usersEntity.getTenantId());
                userBean.setUserId(usersEntity.getId());
                userBean.setUserName(usersEntity.getLoginName());
                //增加用户类型 Baron20170524
                userBean.setUserType(usersEntity.getUserType());
                userBean.setMobile(usersEntity.getTelephone());
                userBean.setOpenid(openId);
                userBean.setToken(token.getAccessToken());
                //保存redis
                redis.set(RedisKey.User.UA.getKey(token.getAccessToken()), JsonUtils.toJson(userBean), 30 * 24 * 60 * 60);
                redis.set(RedisKey.User.UA_TOKEN.getKey(usersEntity.getId().toString()), token.getAccessToken(),30 * 24 * 60 * 60);
                redirectUrl.append("&uid=").append(usersEntity.getId());
                redirectUrl.append("&to=");
            }else{
                mav.addObject("e", ErrorCode.USER_NOT_USABLE);
                mav.setView(new RedirectView(rwurl, false));
                return mav;
            }
        }
        log.info("redirect to " + redirectUrl.toString());
        mav.setView(new RedirectView(redirectUrl.toString(), false));
        return mav;
    }


    @Guest
    @RequestMapping(value = "/auth/url", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getAuthURL() throws UnsupportedEncodingException {
        String authpath = "/fbeeConsole_web/api/WechatInfo/auth/login?redirect=";
        String suf = "&response_type=code&scope=snsapi_userinfo&state=@STATE@#wechat_redirect";
        StringBuffer sb = new StringBuffer();
        sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?")
                .append("appid=").append(Constants.APPID)
                .append("&redirect_uri=")
                .append(URLEncoder.encode(Constants.MOBILE_HOST_NAME, UTF8))
                .append(URLEncoder.encode(authpath, UTF8))
                .append("@REDIRECT@")
                .append(suf);
        return JsonResult.success(sb.toString());
    }

    /**
     * 接收微信平台的GET事件提交,主要用于微信接入服务器TOKEN验证
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @param response
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.PROCESS_URL, method = RequestMethod.GET)
    public void signature(@RequestParam(value = "signature", required = true) String signature,
                          @RequestParam(value = "timestamp", required = true) String timestamp,
                          @RequestParam(value = "nonce", required = true) String nonce,
                          @RequestParam(value = "echostr", required = true) String echostr, HttpServletResponse response)
            throws IOException {
        String[] values = {Constants.TOKEN, timestamp, nonce};
        Arrays.sort(values); // 字典序排序
        String value = values[0] + values[1] + values[2];
        String sign = DigestUtils.shaHex(value);
        PrintWriter writer = response.getWriter();
        if (signature.equals(sign)) {// 验证成功返回ehcostr
            writer.print(echostr);
        } else {
            writer.print("error");
        }
        writer.flush();
        writer.close();
    }
    
    @Guest
    @RequestMapping(value = "/share/sign", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getWebShareJsSign(String urlpath) {

        Map<String, Object> ret = new HashMap();
        String appId = Constants.APPID;
        String secret = Constants.APPSECRET;
        String requestUrl = urlpath;
        log.info(requestUrl + "*************************");
        String access_token = "";
        String jsapi_ticket = "";
        String timestamp = Long.toString(System.currentTimeMillis() / 1000L);
        String nonceStr = UUID.randomUUID().toString();


        Map tokenMap = TokenSingleton.getInstance().getMap();
        if (tokenMap.get("jsapi_token") != null) {
            jsapi_ticket = (String) tokenMap.get("jsapi_token");
            log.info(jsapi_ticket + "===========jsapi_ticket");
        }
        String signature = "";

        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + requestUrl;

        log.info(sign + "-------------------");
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sign.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ret.put("appId", appId);
        ret.put("timestamp", timestamp);
        ret.put("nonceStr", nonceStr);
        ret.put("signature", signature);
        log.info("signature=============" + signature);
        return JsonResult.success(ret);
    }
    
    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", new Object[]{Byte.valueOf(b)});
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
