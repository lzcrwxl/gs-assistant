package com.fbee.modules.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.bean.consts.ConstantEnum;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.ErrorCode;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.bean.CaptchaCode;
import com.fbee.modules.core.bean.SmsCode;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.utils.GenerateCaptcha;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.LoginForm;
import com.fbee.modules.form.SysMenuForm;
import com.fbee.modules.form.TenantsUsersForm;
import com.fbee.modules.interceptor.anno.Auth;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.jsonData.json.CaptchaJsonData;
import com.fbee.modules.jsonData.json.UserMenusJsonData;
import com.fbee.modules.mybatis.dao.TenantsUsersMapper;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.redis.consts.RedisKey;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.SmsService;
import com.fbee.modules.service.SysPermissionService;
import com.fbee.modules.service.TenantsUserService;
import com.fbee.modules.service.UserService;
import com.fbee.modules.utils.EntryptUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.validate.LoginFormValidate;



/**
 * @author 贺章鹏
 * @ClassName: UserController
 * @Description: 用户登陆、登出、图形验证码
 * @date 2016年12月27日 下午4:19:25
 */
@Controller
@RequestMapping(RequestMappingURL.USERS_BASE_URL)
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SysPermissionService permissionService;

    @Autowired
    TenantsUserService tenantsUserService;
    
    @Autowired
    CommonService commonService;
    
    @Autowired
    SmsService smsService;
    
    @Autowired
	TenantsUsersMapper tenantsUsersDao;

    private static JedisTemplate redis = JedisUtils.getJedisTemplate();

    @Guest
    @RequestMapping(value = RequestMappingURL.CAPTCHA_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        CaptchaJsonData jsonData = new CaptchaJsonData();
        try {
            GenerateCaptcha randomCode = new GenerateCaptcha();
            CaptchaCode captcha = randomCode.getRandcodeBase64(request, response, null, null);

            if (captcha != null && StringUtils.isNotBlank(captcha.getImage())) {
                String key = RandomStringUtils.random(16, true, true);
                jsonData.setCaptcha(captcha.getImage());
                jsonData.setCaptchaKey(key);
                redis.set(RedisKey.User.CAPTCHA.getKey(key), JsonUtils.toJson(captcha));
                return JsonResult.success(jsonData);
            } else {
                return JsonResult.failure(ResultCode.User.CAPTCHA_FAILURE);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @Guest
    @RequestMapping(value = RequestMappingURL.LOGIN_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult userLogin(HttpServletRequest request, HttpServletResponse response, LoginForm loginForm) {
        try {
            JsonResult jsonResult = LoginFormValidate.validLogin(loginForm);
            if (!jsonResult.isSuccess()) {
                return jsonResult;
            }

            Log.info(loginForm.toString());

            //非生产环境不验证图形码
            if ("prod".equalsIgnoreCase(Global.getConfig("env"))) {
                //验证图形验证码
                HttpSession session = SessionUtils.getSession(request);
                CaptchaCode captchaObj = (CaptchaCode) session.getAttribute(GenerateCaptcha.RANDOMCODEKEY);
                if(captchaObj == null){
                    String captchaString = redis.get(RedisKey.User.CAPTCHA.getKey(loginForm.getCaptchaKey()));
                    if(StringUtils.isNotBlank(captchaString)){
                        captchaObj = JsonUtils.fromJson(captchaString, CaptchaCode.class);
                    }
                }
                redis.del(RedisKey.User.CAPTCHA.getKey(loginForm.getCaptchaKey()));
                session.removeAttribute(GenerateCaptcha.RANDOMCODEKEY);
                if (captchaObj == null || captchaObj.isExpired()) { // 验证码过期
                    return JsonResult.failure(ResultCode.User.CAPTCHA_TIMEOUT);
                } else if (!captchaObj.getCode().toUpperCase().equals(loginForm.getCaptcha().toUpperCase())) {// 验证码不正确
                    return JsonResult.failure(ResultCode.User.CAPTCHA_ERROR);
                }
                
            }
            jsonResult = userService.login(loginForm, request);

            return jsonResult;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @RequestMapping(value = RequestMappingURL.USER_MENUS_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getUserMenus(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            UserMenusJsonData userMenusJsonData = userService.getUserMenus(userBean);
            if (userMenusJsonData != null) {
                return JsonResult.success(userMenusJsonData);
            } else {
                return JsonResult.failure(ResultCode.DATA_IS_NULL);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    @Guest
    @RequestMapping(value = RequestMappingURL.LOGOUT_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult userLogout(HttpServletRequest request, HttpServletResponse response) {
        try {
            JsonResult jsonResult = userService.logout(request);
            return jsonResult;
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取用户权限
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.USER_PERMISSION_URL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getUserPermission(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            //sys_menu表信息
            String str = redis.get(Constants.USER_PERMISSION + userBean.getUserId());
            //转换Json对象
            List<SysMenuForm> sysMenuArray = JsonUtils.fromJson(str, List.class, SysMenuForm.class);
            List<SysMenuForm> sysMenuList = new ArrayList<>();
            if (sysMenuArray != null && sysMenuArray.size() > 0) {
                for (SysMenuForm form : sysMenuArray) {
                	//遍历sysMenuArray填充sysMenuList   一级页面
                    if ("0".equals(String.valueOf(form.getParentId()))) {
                        sysMenuList.add(form);
                    }
                }
                if (sysMenuList != null && sysMenuList.size() > 0) {
                	//根据父类ID填充sysMenuListA   二级页面	
                    for (SysMenuForm menu : sysMenuList) {
                        List<SysMenuForm> sysMenuListA = new ArrayList<>();
                        for (Object obj : sysMenuArray) {
                            SysMenuForm form = (SysMenuForm) obj;
                            if (menu.getId().equals(form.getParentId())) {
                                sysMenuListA.add(form);
                            }
                        }
                        if (sysMenuListA.size() > 0) menu.setSubMenuList(sysMenuListA);
                    }
                    //三级菜单填充
                    for (SysMenuForm menu : sysMenuList) {
                        if (null == menu.getSubMenuList()) continue;
                        for (SysMenuForm menuA : menu.getSubMenuList()) {
                            List<SysMenuForm> sysMenuListB = new ArrayList<>();
                            for (Object obj : sysMenuArray) {
                                SysMenuForm form = (SysMenuForm) obj;
                                if (menuA.getId().equals(form.getParentId())) {
                                    sysMenuListB.add(form);
                                }
                            }
                            if (sysMenuListB.size() > 0) menuA.setSubMenuList(sysMenuListB);
                        }
                    }
                }
            }
            if (sysMenuList != null && sysMenuList.size() > 0) {

                return JsonResult.success(sysMenuList);
            } else {
                return JsonResult.failure(ResultCode.DATA_IS_NULL);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取用户系统设置
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.USER_SYS_SETTING_URL, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getUserSetting(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            String str = redis.get(Constants.USER_PERMISSION + userBean.getUserId());
            List<SysMenuForm> sysMenuArray = JsonUtils.fromJson(str, List.class, SysMenuForm.class);
            List<SysMenuForm> sysMenuList = new ArrayList<>();
            if (sysMenuArray != null && sysMenuArray.size() > 0) {
                for (SysMenuForm form : sysMenuArray) {
                    if ("99999".equals(String.valueOf(form.getParentId()))) {
                        sysMenuList.add(form);
                    }
                }
                if (sysMenuList != null && sysMenuList.size() > 0) {
                    for (SysMenuForm menu : sysMenuList) {
                        List<SysMenuForm> sysMenuListA = new ArrayList<>();
                        for (Object obj : sysMenuArray) {
                            SysMenuForm form = (SysMenuForm) obj;
                            if (menu.getId().equals(form.getParentId())) {
                                sysMenuListA.add(form);
                            }
                        }
                        if (sysMenuListA.size() > 0) menu.setSubMenuList(sysMenuListA);
                    }
                    for (SysMenuForm menu : sysMenuList) {
                        if (null == menu.getSubMenuList()) continue;
                        for (SysMenuForm menuA : menu.getSubMenuList()) {
                            List<SysMenuForm> sysMenuListB = new ArrayList<>();
                            for (Object obj : sysMenuArray) {
                                SysMenuForm form = (SysMenuForm) obj;
                                if (menuA.getId().equals(form.getParentId())) {
                                    sysMenuListB.add(form);
                                }
                            }
                            if (sysMenuListB.size() > 0) menuA.setSubMenuList(sysMenuListB);
                        }
                    }
                }
            }
            if (sysMenuList != null && sysMenuList.size() > 0) {
                return JsonResult.success(sysMenuList);
            } else {
                return JsonResult.failure(ResultCode.DATA_IS_NULL);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }


    /**
     * 门店助手用户列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.TENANTS_USER_LISTINFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getTenatsUser(HttpServletRequest request, HttpServletResponse response,
                                    String loginAccount, String loginName, String startCreateDate, String endCreateDate) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            //超级管理员查看所有子账号
            String userType = null;
            if (ConstantEnum.UserType.管理员.getCode().equals(userBean.getUserType())) {
                userType = ConstantEnum.UserType.业务员.getCode();    //管理员查看业务员账号
            }
            if (ConstantEnum.UserType.业务员.getCode().equals(userBean.getUserType())) {
                userType = "04";    //“04”不存在的用户类型，业务员没有下级子账号
            }

            Map<String, Object> map = new HashMap<>();
            map.put("userId", userBean.getUserId());
            map.put("userType", userType);
            map.put("loginAccount", loginAccount);    //登录账号
            map.put("loginName", loginName);        //登陆名称
            map.put("startCreateDate", startCreateDate);    //查询开始时间
            map.put("endCreateDate", endCreateDate);        //查询截止时间

            JsonResult jsonResult = tenantsUserService.getTenatsUser(map);

            if (jsonResult != null) {
                return jsonResult;
            } else {
                return JsonResult.failure(ResultCode.DATA_IS_NULL);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 门店助手用户列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.TENANTS_USER_LISTINFO_SEL, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getTenatsUserSel(HttpServletRequest request, HttpServletResponse response,
                                       String loginAccount, String loginName, String startCreateDate, String endCreateDate) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            //超级管理员查看所有子账号
            String userType = null;
            if (ConstantEnum.UserType.管理员.getCode().equals(userBean.getUserType())) {
                userType = ConstantEnum.UserType.业务员.getCode();    //管理员查看业务员账号
            }
            if (ConstantEnum.UserType.业务员.getCode().equals(userBean.getUserType())) {
                userType = "04";    //“04”不存在的用户类型，业务员没有下级子账号
            }

            Map<String, Object> map = new HashMap<>();
            map.put("userId", userBean.getUserId());
            /**
             map.put("userType", userType);
             map.put("loginAccount", loginAccount);	//登录账号
             map.put("loginName", loginName);		//登陆名称
             map.put("startCreateDate", startCreateDate);	//查询开始时间
             map.put("endCreateDate", endCreateDate);		//查询截止时间
             **/
            JsonResult jsonResult = tenantsUserService.getTenatsUserSel(map);

            if (jsonResult != null) {
                return jsonResult;
            } else {
                return JsonResult.failure(ResultCode.DATA_IS_NULL);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除用户
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.TENANTS_USER_DELETE, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult deleteTenatsUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            String ids = request.getParameter("ids");

            String[] userIds = ids.split(",");

            int result = tenantsUserService.deleteTenatsUser(Arrays.asList(userIds));

            if (result > 0) {
                return JsonResult.success(null);
            } else {
                return JsonResult.failure(ResultCode.ERROR);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 添加用户
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.TENANTS_USER_ADD, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult addTenatsUser(HttpServletRequest request, HttpServletResponse response,
                                    TenantsUsersForm tenantsUsersForm, String menuIds) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            //判断是否超过会员账号限制数
            Map<String, Object> param = new HashMap<>();
            param.put("userId", userBean.getUserId());
            int subAccountCount = tenantsUserService.getAccountCount(param);

            if (subAccountCount >= Integer.valueOf(Global.getConfig("userType.ordinaryMember.max"))) {
                return JsonResult.failure(ResultCode.User.MSG_ACCOUNT_MAX);
            }

            //根据用户账号获取用户的信息
            JsonResult jsonResult = tenantsUserService.getByLoginAccount(tenantsUsersForm.getLoginAccount());
            if (jsonResult.getJsonData() != null) {           	
                return JsonResult.failure(ResultCode.User.ACCOUNT_EXIST);
            }
            //根据手机号获取用户
            int  counts = tenantsUserService.getByTelephone(tenantsUsersForm.getTelephone());
            if (counts>0) {
            	
                return JsonResult.failure(ResultCode.User.TELEPHONE_BUNDED);
            }
            
            SmsCode smsCode=(SmsCode) session.getAttribute(SmsCode.REG_ASS_SMS_CODE_KEY);
            if(smsCode!=null)
            {
            	if(!tenantsUsersForm.getTelephone().equals(smsCode.getMobile()))
            	{//不是此次缓存，要求重新发送
            		return JsonResult.failure(ResultCode.User.MSG_NULL_ERROR);
            	}
            	if(!tenantsUsersForm.getCode().equals(smsCode.getCode()))
                {
                	return JsonResult.failure(ResultCode.User.MSG_CAPTCHA_ERROR);
                }
            }else {
            	return JsonResult.failure(ResultCode.User.MSG_NULL_ERROR);
            }
            
            //添加用户账号
            tenantsUsersForm.setTenantId(userBean.getTenantId());
            tenantsUsersForm.setPassword(EntryptUtils.entryptUserPassword("111111", "123456"));
            tenantsUsersForm.setSalt("123456");
            tenantsUsersForm.setLoginFlag("1");
            tenantsUsersForm.setIsUsable("1");
            int result = tenantsUserService.addTenatsUser(tenantsUsersForm, menuIds);

            if (result > 0) {
                return JsonResult.success(null);
            } else {
                return JsonResult.failure(ResultCode.ERROR);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 修改用户
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.TENANTS_USER_UPDATE, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult updateTenatsUser(HttpServletRequest request, HttpServletResponse response,
                                       TenantsUsersForm tenantsUsersForm, String menuIds) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            int result = tenantsUserService.updateTenatsUser(tenantsUsersForm, menuIds);

            if (result > 0) {
                return JsonResult.success(null);
            } else {
                return JsonResult.failure(ResultCode.DATA_IS_NULL);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 获取修改用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestMappingURL.TENANTS_USER_INFO, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getTenatsUserInfo(HttpServletRequest request, HttpServletResponse response,
                                        Integer id) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }
            return tenantsUserService.getById(id);
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }

    /**
     * 重置密码
     *
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = RequestMappingURL.TENANTS_USER_RESET_PASSWORD, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult resetPassword(HttpServletRequest request, HttpServletResponse response,
                                    Integer id) {
        try {
            HttpSession session = SessionUtils.getSession(request);
            UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
            if (userBean == null) {
                return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
            }

            int result = tenantsUserService.resetPassword(id, "111111");
            if (result > 0) {
                return JsonResult.success(null);
            } else {
                return JsonResult.failure(ResultCode.ERROR);
            }
        } catch (Exception e) {
            Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
            return JsonResult.failure(ResultCode.ERROR);
        }
    }
    
    /**
     * 发送登陆短信校验码
     * @return
     */
    @Guest
    @RequestMapping(value = RequestMappingURL.SENDSMS, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult sendLoginSmsCode(HttpServletRequest request, HttpServletResponse response, LoginForm loginForm){
    	if(!"prod".equalsIgnoreCase(Global.getConfig("env"))){
            return JsonResult.success("test");
        }
        
		//根据用户账号获取用户的信息
		TenantsUsersEntity user=tenantsUsersDao.getByLoginAccount(loginForm.getLoginAccount());
		
		if(user==null){
			return JsonResult.failure(ResultCode.User.ACCOUNT_NOT_EXIST);
		}
		if (!user.getPassword().equals(EntryptUtils.entryptUserPassword(loginForm.getPassword().toUpperCase(), user.getSalt()))) {
			return JsonResult.failure(ResultCode.User.ACCOUNT_PASSWORD_ERROR);
		}
		
        String mobile = user.getTelephone();
        if(StringUtils.isBlank(mobile)){
        	return JsonResult.failure(ErrorCode.REG_MOBILE_EMPTY);
        }
        
        // 获取session中短信验证码对象
        SmsCode smsCode = smsService.sendLoginSmsCode(mobile);
        // 短信发送成功
        if (smsCode != null) {
            redis.set(RedisKey.User.LOGINSMSCODE.getKey(mobile), JsonUtils.toJson(smsCode),60*5);
            return JsonResult.success(smsCode.getCode());
        }

        // 短信发送失败
        return JsonResult.failure(ResultCode.User.SMSSEND_FAILURE);
    }
    
    /**
     * 主页面显示数据
     * @return
     */
    @Auth
    @RequestMapping(value = RequestMappingURL.MAINPAGE_DATA, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getMainPageDate(){
	    try{	
	    	HttpSession session = SessionUtils.getSession();
	        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
	        if (userBean == null) {
	            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
	        }
	        TenantsUsersEntity tenantsUsersEntity = tenantsUsersDao.getById(userBean.getUserId());
	        if(tenantsUsersEntity.getRefresh().equals(Constants.REFRESH_TRUE)){
	        	commonService.refreshSession();
	        }
	        return userService.getMainPageDate(userBean);
	    } catch (Exception e) {
	        Log.error(ResultCode.getMsg(ResultCode.ERROR), e);
	        return JsonResult.failure(ResultCode.ERROR);
	    }
    }
}
