package com.fbee.modules.interceptor;

import com.fbee.modules.basic.WebUtils;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.ErrorCode;
import com.fbee.modules.core.utils.CookieUtils;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.form.SysMenuForm;
import com.fbee.modules.interceptor.anno.Auth;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.redis.consts.RedisKey;
import com.fbee.modules.utils.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

@Aspect
public class AuthInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);


    private JedisTemplate redis = JedisUtils.getJedisTemplate();

    @Pointcut("execution(public * com.fbee.modules.controller..*.*(..))")
    public void point() {
        log.info("aop init");
    }

    @Around("point()")
    public Object aroundAdvice(ProceedingJoinPoint joinpoint) throws Throwable {
        Method m = ((MethodSignature) joinpoint.getSignature()).getMethod();
        boolean guest = m.isAnnotationPresent(Guest.class);
        boolean auth = m.isAnnotationPresent(Auth.class);

        HttpServletRequest request = SessionUtils.getRequest();
        UserBean userBean = null;
        String ua = request.getHeader(Constants.UA);
        if (org.apache.commons.lang3.StringUtils.isBlank(ua)) {
        	ua = CookieUtils.getCookie(request, Constants.UA);
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(ua)){
        	ua = request.getParameter(Constants.QR_UA);
        	String str = redis.get(RedisKey.User.UA_QR.getKey(ua));
        	userBean = org.apache.commons.lang3.StringUtils.isBlank(str) ? null : JsonUtils.fromJson(str, UserBean.class);
        	redis.del(RedisKey.User.UA_QR.getKey(ua));
        }else{
        	String str = redis.get(RedisKey.User.UA.getKey(ua));
            userBean = org.apache.commons.lang3.StringUtils.isBlank(str) ? null : JsonUtils.fromJson(str, UserBean.class);
        }
        
//        if("test".equals(Constants.ENV)){
//        	userBean= WebUtils.getCurrentUser();
//            if(userBean == null){
//                if (org.apache.commons.lang3.StringUtils.isBlank(ua)) {
//                    ua = CookieUtils.getCookie(request, Constants.UA);
//                }
//                if (org.apache.commons.lang3.StringUtils.isBlank(ua)) {
//                    ua = request.getParameter(Constants.QR_UA);
//                    String str = redis.get(RedisKey.User.UA_QR.getKey(ua));
//                    userBean = org.apache.commons.lang3.StringUtils.isBlank(str) ? null : JsonUtils.fromJson(str, UserBean.class);
//                    SessionUtils.getSession().setAttribute(Constants.USER_SESSION, userBean);
//                    redis.del(RedisKey.User.UA_QR.getKey(ua));
//                }else {
//                    String str = redis.get(RedisKey.User.UA.getKey(ua));
//                    userBean = org.apache.commons.lang3.StringUtils.isBlank(str) ? null : JsonUtils.fromJson(str, UserBean.class);
//                    SessionUtils.getSession().setAttribute(Constants.USER_SESSION, userBean);
//                }
//            }
//        }
        
        //若请求方法允许游客访问，则不验证
        if (guest) {
            log.info("guest method, skipping");
            return joinpoint.proceed();
        }
        if(auth){
        	String weChatJson = null;
            String token = request.getHeader(Constants.UA);
            if(StringUtils.isNotBlank(token)){
            	weChatJson = redis.get(RedisKey.User.UA_WECHAT.getKey(token));
            }
            log.info("weChatJson ---------- : {}", weChatJson);
        	if(StringUtils.isBlank(weChatJson)){
        		log.info("auth visit , check token and wechat auth info");
        		JsonResult jsonResult = JsonResult.failure(ErrorCode.WX_AUTH_ERROR);
        		return jsonResult;
        	}
        	if(userBean==null){
                log.warn("userBean is null, The method must be login");
                JsonResult jsonResult = JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
                return jsonResult;
            }
        	log.info("auth method, skipping");
            return joinpoint.proceed();
        }
        if(userBean==null){
            log.warn("userBean is null, The method must be login");
            JsonResult jsonResult = JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
            return jsonResult;
        }
        
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());

        String str = redis.get(Constants.USER_NO_PERMISSION + userBean.getUserId());
        List<SysMenuForm> sysMenuArray = JsonUtils.fromJson(str, List.class, SysMenuForm.class);
        if (sysMenuArray != null && sysMenuArray.size() > 0) {
            for (SysMenuForm menu : sysMenuArray) {
                if (url.equals(menu.getHref())) {
                    log.warn("url:" + url + " 权限限制");
                    JsonResult jsonResult = JsonResult.failure(ResultCode.NO_PERMISSION);
                    return jsonResult;
                }
            }
        }
        return joinpoint.proceed();
    }

}
