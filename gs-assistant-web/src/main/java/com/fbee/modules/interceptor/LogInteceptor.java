package com.fbee.modules.interceptor;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.core.utils.CookieUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by gaoyan on 13/07/2017.
 */
@Component
public class LogInteceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger("accessLogger");

    private static final ThreadLocal<StopWatch> threadWatch = new ThreadLocal<StopWatch>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求地址信息
        StringBuffer req = new StringBuffer();
        String token = request.getHeader(Constants.UA);
        String uid = request.getHeader(Constants.UID);
        if (StringUtils.isBlank(token)) {
            token = CookieUtils.getCookie(request, Constants.UA);
            uid = CookieUtils.getCookie(request, Constants.UID);
        }
        req.append(uid).append("|").append(token).append("|");
        //请求参数信息
        if (request != null) {
            req.append(request.getMethod()).append("|").append(request.getServletPath()).append("|");
            //请求参数信息
            req.append(JsonUtils.toJson(request.getParameterMap())).append("|");
            //请求头信息
            Enumeration<String> keys = request.getHeaderNames();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                req.append(key).append(":").append(request.getHeader(key)).append("|");
            }
        }
        req.append("Thread[").append(Thread.currentThread().getId()).append("]");
        logger.info("[REQUEST]|{}", req.toString());

        StopWatch watch = new StopWatch();
        watch.start();
        threadWatch.set(watch);

        return super.preHandle(request, response, handler);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);

        StopWatch watch = threadWatch.get();
        if (watch.isRunning()) {
            watch.stop();
            StringBuffer sb = new StringBuffer();
            sb.append("Thread[").append(Thread.currentThread().getId()).append("]").append("|");
            sb.append(request.getServletPath()).append("|");
            sb.append(response.getStatus()).append("|");
            sb.append(watch.getTotalTimeMillis()).append("ms");
            logger.info("[RESPONSE]|{}", sb.toString());
        }

    }


}
