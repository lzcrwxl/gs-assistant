package com.fbee.modules.controller;

import com.fbee.modules.basic.RequestMappingURL;
import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.bean.consts.ErrorCode;
import com.fbee.modules.bean.consts.Status;
import com.fbee.modules.bean.UserBean;
import com.fbee.modules.controller.validate.JobValidate;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.page.form.TenantJobForm;
import com.fbee.modules.core.page.form.TenantJobResumeForm;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.model.TenantsJobCheck;
import com.fbee.modules.mybatis.model.TenantsJobResume;
import com.fbee.modules.mybatis.model.TenantsJobs;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.redis.consts.RedisKey;
import com.fbee.modules.service.JobService;
import com.fbee.modules.service.TenantService;
import com.fbee.modules.service.basic.ServiceException;
import com.fbee.modules.utils.JsonUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;

/**
 * 职位招聘发布
 */
@Controller
@RequestMapping(RequestMappingURL.JOB_BASE_URL)
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);
    
    private JedisTemplate redis = JedisUtils.getJedisTemplate();

    @Autowired
    JobService jobService;

    /**
     * 查询招聘条件（发布次数／账户余额）
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult info() {

        //判断session是否超时
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }

        TenantsJobCheck ck = jobService.getTenantsJobsCheck(userBean.getTenantId(), userBean.getUserId());
        return JsonResult.success(ck);

    }

    /**
     * 创建职位
     * 由订单（待定金／待面试）生成发布
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody TenantsJobs jobs) {

        //判断session是否超时
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }

        //表单校验
        JsonResult jsonResult = JobValidate.validFormInfo(jobs);
        if (!jsonResult.isSuccess()) {
            return jsonResult;
        }

        jobs.setAddAccount(userBean.getLoginAccount());
        jobs.setTenantId(userBean.getTenantId());
        jobs.setTenantUserId(userBean.getUserId());

        try {
            Integer id = this.jobService.saveTenantsJobsInfo(jobs);
            return JsonResult.success(Collections.singletonMap("jobId", id));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return JsonResult.failure(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 查询所有职位
     * 1. 筛选
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult find(TenantJobForm form) {

        //判断用户session是否过期
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean != null) {
            form.setTenantId(userBean.getTenantId());
            form.setTenantUserId(userBean.getUserId());
            if(userBean.getUserType().equals("02") || userBean.getUserType().equals("01")){
                //如果是管理员
                form.setIsAdmin("1");
            }
        }
        //调用业务层查询
        Page<TenantsJobs> page = jobService.getTenantsJobsInfoList(form);
        return JsonResult.success(page);
    }

    /**
     * 查询职位详情
     */
    @RequestMapping(value = "/{jobId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult get(@PathVariable Integer jobId) {
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }
        TenantsJobs job = jobService.getTenantsJobsDetail(jobId);
        return JsonResult.success(job);
    }

    /**
     * 下架招聘职位
     *
     * @return
     */
    @RequestMapping(value = "/off", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(String jobIds) {
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }
        if(StringUtils.isBlank(jobIds)){
            return JsonResult.failure(ResultCode.DATA_IS_NULL);
        }
        for(String jobId : jobIds.split(",")){
            if(StringUtils.isBlank(jobId)){
                continue;
            }
            TenantsJobs job = new TenantsJobs();
            job.setId(Integer.valueOf(jobId));
            job.setStatus(Status.OnOffShelf.OFF_SHELF);
            job.setModifyAccount(userBean.getLoginAccount());
            try{
                jobService.updateTenantsJobsInfo(job);
            }catch (ServiceException e){
                log.info(e.getMessage());
                return JsonResult.failure(ResultCode.ERROR, e.getMessage());
            }
        }
        return JsonResult.success("success");
    }

    /**
     * 刷新职位信息
     *
     * @return
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult refreshJob(String jobIds) {
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }
        if(StringUtils.isBlank(jobIds)){
            return JsonResult.failure(ResultCode.DATA_IS_NULL);
        }
        Integer total = 0;
        for(String jobId : jobIds.split(",")){
            if(StringUtils.isBlank(jobId)){
                continue;
            }
            TenantsJobs job = new TenantsJobs();
            job.setId(Integer.valueOf(jobId));
            job.setIsRefreshed(1);
            job.setModifyAccount(userBean.getLoginAccount());
            try{
                Integer num = jobService.updateTenantsJobsInfo(job);
                total = total + num;
            }catch (ServiceException e){
                log.info(e.getMessage());
                //return JsonResult.failure(ResultCode.ERROR, e.getMessage());
            }
        }
        return JsonResult.success(total);
    }


    /**
     * 投递阿姨简（抢单）
     */
    @RequestMapping(value = "/{jobId}/resume", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult postResume(@PathVariable("jobId") Integer jobId, Integer staffId) {
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }

        TenantsJobResume resume = new TenantsJobResume();
        resume.setAddAccount(userBean.getLoginAccount());
        resume.setJobId(jobId);
        resume.setResumeTenantId(userBean.getTenantId());
        resume.setResumeTenantUserId(userBean.getUserId());
        resume.setResumeTenantStaffId(staffId);
        try{
            Integer id = jobService.applyResume(resume);
            return JsonResult.success(Collections.singletonMap("id", id));
        }catch (ServiceException e){
            log.error(e.getMessage());
            return JsonResult.failure(e.getCode(),e.getMessage());
        }

    }
    /**
     * 查询我投递的所有简历
     */
    @RequestMapping(value = "/resume/mine/apply", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult findMyResume(TenantJobResumeForm form) {
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }
        form.setResumeTenantId(userBean.getTenantId());
        form.setResumeTenantUserId(userBean.getUserId());
        Page<TenantsJobResume> list = jobService.getMyApplyResume(form);
        return JsonResult.success(list);
    }

    /**
     * 查询我收到的所有简历
     */
    @Guest
    @RequestMapping(value = "/resume/mine/box", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult findResume(TenantJobResumeForm form) {
    	String weChatJson = null;
    	HttpServletRequest request = SessionUtils.getRequest();
        String token = request.getHeader(Constants.UA);
        if(StringUtils.isNotBlank(token)){
        	weChatJson = redis.get(RedisKey.User.UA_WECHAT.getKey(token));
        	if(StringUtils.isBlank(weChatJson)){
        		log.info("auth visit , check token and wechat auth info");
        		JsonResult jsonResult = JsonResult.failure(ErrorCode.WX_AUTH_ERROR);
        		return jsonResult;
        	}
        }
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
        	String str = redis.get(RedisKey.User.UA.getKey(token));
            userBean = org.apache.commons.lang3.StringUtils.isBlank(str) ? null : JsonUtils.fromJson(str, UserBean.class);
            if(userBean == null){
            	log.warn("userBean is null, The method must be login");
                JsonResult jsonResult = JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
                return jsonResult;
            }
        }
        form.setJobTenantId(userBean.getTenantId());
        form.setJobTenantUserId(userBean.getUserId());
        Page<TenantsJobResume> list = jobService.getMyResumeBox(form);
        return JsonResult.success(list);
    }

    /**
     * 查询简历详情
     */
    @RequestMapping(value = "/resume/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult findResumeDetail(@PathVariable Integer id) {
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }
        TenantsJobResume resume = jobService.getTenantsJobsResumeDetail(id);
        return JsonResult.success(resume);
    }

    /**
     * 审核简历
     * 1. 修改简历状态通过
     */
    @RequestMapping(value = "/{jobId}/resume/{resumeId}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult checkResume(@PathVariable Integer jobId, @PathVariable Integer resumeId, @RequestBody TenantsJobResume resume) {
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }
        resume.setJobId(jobId);
        resume.setId(resumeId);
        resume.setJobTenantId(userBean.getTenantId());
        resume.setJobTenantUserId(userBean.getUserId());
        Integer id = jobService.checkJobResumes(resume);
        return JsonResult.success(id);
    }


    /**
     * 订阅职位发布信息
     * @return
     */
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult subscribe(String [] serviceTypes){
        HttpSession session = SessionUtils.getSession();
        UserBean userBean = (UserBean) session.getAttribute(Constants.USER_SESSION);
        if (userBean == null) {
            return JsonResult.failure(ResultCode.SESSION_TIMEOUT);
        }
        jobService.subscribeJobMessage(serviceTypes);
        return JsonResult.success(null);
    }

}
