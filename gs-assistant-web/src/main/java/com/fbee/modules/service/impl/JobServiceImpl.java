package com.fbee.modules.service.impl;

import com.fbee.modules.bean.consts.JobConst;
import com.fbee.modules.bean.consts.Status;
import com.fbee.modules.bean.consts.SysSetting;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.page.form.TenantJobForm;
import com.fbee.modules.core.page.form.TenantJobResumeForm;
import com.fbee.modules.core.utils.DateUtils;
import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.dao.*;
import com.fbee.modules.mybatis.entity.*;
import com.fbee.modules.mybatis.model.*;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.service.CommonService;
import com.fbee.modules.service.JobService;
import com.fbee.modules.service.OrderService;
import com.fbee.modules.service.basic.BaseService;
import com.fbee.modules.service.basic.ServiceException;
import com.fbee.modules.utils.DictionarysCacheUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.BusinessModel;
import com.fbee.modules.wechat.message.model.JobMessageModel;
import com.fbee.modules.wechat.message.model.ResumeMessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class JobServiceImpl extends BaseService implements JobService {

    private final static Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    TenantsJobsMapper tenantsJobsMapper;

    @Autowired
    TenantsJobResumesMapper tenantsJobResumesMapper;

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    TenantsInfoMapper tenantsInfoMapper;

    @Autowired
    TenantsFundsMapper tenantsFundsMapper;

    @Autowired
    OrderCustomersInfoMapper orderCustomersInfoMapper;

    @Autowired
    CommonService commonService;

    @Autowired
    TenantsUsersMapper tenantsUsersMapper;

    @Autowired
    TenantsAppsMapper tenantsAppsMapper;

    @Autowired
    OrderService orderService;
    
    private JedisTemplate redis = JedisUtils.getJedisTemplate();

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    /**
     * 检查商家是否可发布订单
     *
     * @param tenantId
     * @return
     */
    @Override
    public TenantsJobCheck getTenantsJobsCheck(Integer tenantId, Integer userId) {

        //商家信息
        TenantsInfoEntity info = tenantsInfoMapper.getById(tenantId);

        //1. 商家是否可以发布订单
        String jobTotal = DictionarysCacheUtils.getSysSetting(SysSetting.Key.JOB_COUNT);
        //2. 商家是否可以抢单
        String resumeTotal = DictionarysCacheUtils.getSysSetting(SysSetting.Key.RESUME_COUNT);
        String jobDeposit = DictionarysCacheUtils.getSysSetting(SysSetting.Key.JOB_DEPOSIT);
        String resumeDeposit = DictionarysCacheUtils.getSysSetting(SysSetting.Key.RESUME_DEPOSIT);
        //3. 商家余额
        TenantsFundsEntity fund = tenantsFundsMapper.getById(tenantId);
        if (fund == null) {
            fund = new TenantsFundsEntity();
            fund.setAvailableAmount(new BigDecimal(0));
        }
        TenantJobResumeForm form = new TenantJobResumeForm();
        //查询状态是1和2的数据
        form.setStatus("12");
        form.setResumeTenantId(tenantId);
        form.setResumeTenantUserId(userId);
        Integer count = tenantsJobResumesMapper.getMyApplyResumesCount(form);

        TenantsJobCheck ck = new TenantsJobCheck();
        ck.setJobTotalNum(Integer.valueOf(jobTotal));
        ck.setJobUsedNum(info.getJobCount());
        ck.setResumeTotalNum(Integer.valueOf(resumeTotal));
        ck.setResumeUsedNum(info.getResumeCount());
        ck.setBalance(fund.getAvailableAmount());
        ck.setJobDeposit(new BigDecimal(jobDeposit));
        ck.setResumeDeposit(new BigDecimal(resumeDeposit));
        ck.setTenantId(tenantId);
        ck.setProcessResumeNum(count);

        return ck;
    }

    /**
     * 保存招聘信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveTenantsJobsInfo(TenantsJobs job) throws ServiceException {
        if (!redis.setnxex("saveTenantsJobsInfo" + job.getOrderNo(), "job", 10)) {
            throw new ServiceException(ResultCode.OPERATION_TOO_FREQUENT, ResultCode.getMsg(ResultCode.OPERATION_TOO_FREQUENT));
        }
        try {
            if (job.getTenantId() == null) {
                throw new ServiceException("未获取到租户信息,请重试");
            }
            //1. 检查订单是否已经发布
            if (StringUtils.isBlank(job.getOrderNo())) {
                throw new ServiceException("未获取到订单编号,请检查订单信息");
            }
            Orders order = ordersMapper.selectByPrimaryKey(job.getOrderNo());
            OrderCustomersInfo cust = orderCustomersInfoMapper.selectByPrimaryKey(job.getOrderNo());
            if (order == null || cust == null) {
                throw new ServiceException("未获取到订单信息,请检查订单信息");
            }
            if (order.getIsPublishedJob().equals(Integer.valueOf(1))) {
                throw new ServiceException("订单已发布,请刷新后重试");
            }

            //1. 检查发布招聘次数
            boolean isDeposit = false;
            TenantsJobCheck ck = getTenantsJobsCheck(job.getTenantId(), job.getTenantUserId());
            if (ck.getJobUsedNum() < ck.getJobTotalNum()) {
                //2. 修改发布次数
                TenantsInfoEntity te = new TenantsInfoEntity();
                te.setTenantId(job.getTenantId());
                te.setModifyAccount(job.getModifyAccount());
                te.setJobCount(ck.getJobUsedNum());
                Integer num = tenantsInfoMapper.updateJobCount(te);
                if (num < 1) {
                    log.error("修改发布次数失败");
                    isDeposit = true;
                }
            } else if (ck.getBalance().compareTo(ck.getJobDeposit()) >= 0) {
                isDeposit = true;
            } else {
                log.error("免费发布次数已用尽，请缴纳保证金后使用");
                throw new ServiceException("免费发布次数已用尽，请缴纳保证金后使用");
            }
            if (isDeposit) {
                //3. 缴纳保证金，冻结余额
                commonService.frozenAmount(job.getTenantId(), ck.getJobDeposit(), job.getOrderNo(), "保证金冻结[招聘发布]");
                job.setDeposit(ck.getJobDeposit());
            }

            TenantsUsersEntity user = tenantsUsersMapper.getById(job.getTenantUserId());
            String webName = tenantsAppsMapper.getWebSiteById(job.getTenantId());

            //5. 新增租户招聘信息
            job.setPositionName(String.format("%s - %s", DictionarysCacheUtils.getServiceTypeName(cust.getServiceItemCode()), DictionarysCacheUtils.getServiceNatureStr(cust.getServiceItemCode(), cust.getServiceType())));
            job.setServiceType(cust.getServiceItemCode());
            job.setServiceMold(cust.getServiceType());
            job.setServiceProvince(cust.getServiceProvice());
            job.setServiceCity(cust.getServiceCity());
            job.setServiceArea(cust.getServiceCounty());
            job.setServiceStartTime(cust.getServiceStart());
            job.setServiceEndTime(cust.getServiceEnd());
            job.setIsRefreshed(0);
            job.setContactName(user.getLoginName());
            job.setContactPhone(user.getTelephone());
            job.setJobTenantName(webName);
            job.setTotalNum(ck.getJobTotalNum());
            job.setUsedNum(0);
            job.setAddTime(new Date());
            job.setModifyTime(new Date());
            job.setStatus(Status.OnOffShelf.ON_SHELF);
            job.setOrderStatus(order.getOrderStatus());
            //所有渠道可见
            job.setVisualExtend("0");
            this.tenantsJobsMapper.insert(job);

            //修改订单->职位已发布
            OrdersEntity oe = new OrdersEntity();
            oe.setOrderNo(job.getOrderNo());
            oe.setIsPublishedJob(1);
            oe.setModifyAccount(user.getTenantId() + "");
            oe.setModifyTime(new Date());
            ordersMapper.updateByPrimaryKeySelective(oe);

            //广播消息
            JobMessageModel model = new JobMessageModel();
            model.setJobId(job.getId()+"");
            model.setServiceType(job.getServiceType());
            model.setServiceDate(String.format("%s至%s", DateUtils.formatDate(job.getServiceStartTime()), DateUtils.formatDate(job.getServiceEndTime())));
            model.setProvince(job.getServiceProvince());
            model.setCity(job.getServiceCity());
            model.setDistrict(job.getServiceArea());
            model.setAddress(String.format("%s%s%s",job.getServiceProvinceValue(), job.getServiceCityValue(), job.getServiceAreaValue()));
            model.setAge(job.getAgeValue());
            model.setSalary(job.getSalary() + job.getSalaryTypeValue());
            model.setDescription(DictionarysCacheUtils.getSkillsStr(job.getServiceType(), cust.getSalarySkills()));

            if("0".equals(job.getVisualExtend()) || "2".equals(job.getVisualExtend())){
                //门店可见
                model.setTitle(String.format("您好，有一笔%s的合单机会，快来看看吧。", DictionarysCacheUtils.getServiceTypeName(cust.getServiceItemCode())));
                String msg = JsonUtils.toJson(model);
                mq.lpush(WechatConfig.Queue.JOB_PUBLISH_B.getQueue(), msg);
                mq.publish(WechatConfig.Channel.JOB_PUBLISH.getChannel(), msg);
            }
            if("0".equals(job.getVisualExtend()) || "1".equals(job.getVisualExtend())){
                //客户端可见
                model.setTitle(String.format("您好，有一笔%s的工作机会，快来看看吧。", DictionarysCacheUtils.getServiceTypeName(cust.getServiceItemCode())));
                String msg = JsonUtils.toJson(model);
                mq.publish(WechatConfig.Channel.JOB_MS_PUBLISH.getChannel(), msg);
            }


            return job.getId();  //返回主键
        } finally {
            redis.del("saveTenantsJobsInfo" + job.getOrderNo());
        }
    }


    @Override
    public Integer updateTenantsJobsInfo(TenantsJobs job) {
        Integer id = job.getId();
        if (id == null) {
            throw new ServiceException("未获取到招聘信息,请联系管理员");
        }
        TenantsJobs tenantsJobs = this.tenantsJobsMapper.getById(id);
        if (tenantsJobs == null) {
            throw new ServiceException("未获取到招聘信息,请联系管理员");
        }
        if (Integer.valueOf(1).equals(tenantsJobs.getIsRefreshed()) && job.getIsRefreshed().equals(1)) {
            return 0;
        }
        Integer num = this.tenantsJobsMapper.update(job); // 修改入库
        return num;
    }


    @Override
    public TenantsJobs getTenantsJobsDetail(Integer id) {
        if (id == null) {
            throw new ServiceException("未获取到招聘信息, 请联系管理员");
        }
        TenantsJobs job = tenantsJobsMapper.getById(id);
        return job;
    }

    /**
     * 查询列表
     *
     * @return
     */
    @Override
    public Page<TenantsJobs> getTenantsJobsInfoList(TenantJobForm form) {
        //只查门店助手端显示的职位
        form.setVisualExtend(StringUtils.isBlank(form.getVisualExtend()) ? "2" : form.getVisualExtend());
        String price = DictionarysCacheUtils.getServicePirceName(form.getSalaryRange());
        if (StringUtils.isNotBlank(price)) {
            String[] strs = price.split(",");
            form.setSalaryMin(new BigDecimal(strs[0]));
            form.setSalaryMax(strs.length > 1 && StringUtils.isNotBlank(strs[1]) ? new BigDecimal(strs[1]) : null);
        }
        TenantsApps tenantsApps=(TenantsApps) tenantsAppsMapper.getById(form.getTenantId());
        if(tenantsApps != null){
        	StringBuffer sb = new StringBuffer().append(Global.getConfig("imageUrl")).append("/").append(tenantsApps.getDomain()).append("/").append("job.html");
            form.setToUrl(sb.toString());
        }
        Integer totalCount = tenantsJobsMapper.getTenantsJobsInfoCount(form);
        if (totalCount == null || totalCount.equals(0)) {
            form.setRecords(0);
            return form;
        }
//        if(form.getSalaryType() != null){
//        	StringBuffer sb = new StringBuffer().append("(").append(form.getSalaryType()).append(")");
//        	form.setSalaryType(sb.toString());
//        }
        form.setRecords(totalCount);
        List<TenantsJobs> resultList = tenantsJobsMapper.getTenantsJobsInfoList(form);
        form.setRows(resultList);
        
        
        return form;
    }


    /**
     * 申请职位
     * 1. 检查招聘人数是否已满
     * 2. 检查免费应聘次数是否足够
     * 3. 添加应聘信息
     * 4. 扣减次数/冻结保证金
     *
     * @param resume
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer applyResume(TenantsJobResume resume) throws ServiceException {
        if (!redis.setnxex("applyResume" + resume.getJobId() + resume.getResumeTenantStaffId(), "apply resume", 10)) {
            throw new ServiceException(ResultCode.OPERATION_TOO_FREQUENT, ResultCode.getMsg(ResultCode.OPERATION_TOO_FREQUENT));
        }
        try {
            if (resume.getJobId() == null) {
                log.error("未获取到招聘信息,请确认");
                throw new ServiceException("未获取到招聘信息,请确认");
            }
            //1. 检查订单是否已经发布
            if (resume.getResumeTenantStaffId() == null) {
                log.error("未获取到阿姨信息,请确认");
                throw new ServiceException("未获取到阿姨信息,请确认");
            }
            TenantsJobs applyJob = tenantsJobsMapper.getById(resume.getJobId());
            if (applyJob == null) {
                log.error("未获取到招聘信息,请确认");
                throw new ServiceException("未获取到招聘信息,请确认");
            }
            if (applyJob.getStatus().equals(JobConst.JobStatus.OFF.getCode())) {
                log.error("未获取到招聘信息,请确认");
                throw new ServiceException(ResultCode.JobResume.JOB_OFF, "未获取到招聘信息,请确认");
            }
            if (applyJob.getTenantUserId().equals(resume.getResumeTenantUserId())) {
                log.error("这是您本人订单，请到我的订单中进行处理");
                throw new ServiceException("这是您本人订单，请到我的订单中进行处理");
            }

            TenantsJobResume re = tenantsJobResumesMapper.getByJobStaff(resume.getJobId(), resume.getResumeTenantStaffId());
            if (re != null) {
                log.error("该家政员已投递过简历,请确认");
                throw new ServiceException("该家政员已投递过简历,请确认");
            }

            TenantsUsersEntity user = tenantsUsersMapper.getById(resume.getResumeTenantUserId());
            String webName = tenantsAppsMapper.getWebSiteById(resume.getResumeTenantId());
            resume.setContactName(user.getLoginName());
            resume.setContactPhone(user.getTelephone());
            resume.setResumeTenantName(webName);
            resume.setJobTenantId(applyJob.getTenantId());
            resume.setJobTenantUserId(applyJob.getTenantUserId());
            resume.setOrderNo(applyJob.getOrderNo());
            resume.setApplyDate(new Date());
            resume.setStatus(JobConst.ResumeStatus.APPLY.getCode());
            resume.setMatch(orderService.getMatchValue(applyJob.getOrderNo(), resume.getResumeTenantStaffId()));
            resume.setAddTime(new Date());
            resume.setModifyTime(new Date());

            //1. 检查发布招聘次数
            boolean isDeposit = false;
            TenantsJobCheck ck = getTenantsJobsCheck(resume.getResumeTenantId(), resume.getResumeTenantUserId());
            if (ck.getResumeUsedNum() < ck.getResumeTotalNum()) {
                //2. 修改抢单次数
                TenantsInfoEntity te = new TenantsInfoEntity();
                te.setTenantId(resume.getResumeTenantId());
                te.setModifyAccount(resume.getAddAccount());
                te.setResumeCount(ck.getResumeUsedNum());
                Integer num = tenantsInfoMapper.updateResumeCount(te);
                if (num < 1) {
                    log.error("修改抢单次数失败");
                    isDeposit = true;
                }
            } else if (ck.getBalance().compareTo(ck.getResumeDeposit()) >= 0) {
                isDeposit = true;
            } else {
                log.error("免费抢单次数已用尽，请缴纳保证金后使用");
                throw new ServiceException("免费抢单次数已用尽，请缴纳保证金后使用");
            }
            if (isDeposit) {
                //3. 缴纳保证金，冻结余额
                commonService.frozenAmount(resume.getResumeTenantId(), ck.getResumeDeposit(), applyJob.getOrderNo(), "保证金冻结[简历投递]");
                resume.setDeposit(ck.getResumeDeposit());
            }

            //更新职位招聘可用次数
            int updateJob = tenantsJobsMapper.useOneResumeNum(applyJob);
            if (updateJob != 1) {
                log.info("use job resume num failure, 请确认");
                throw new ServiceException(ResultCode.JobResume.JOB_FINISHED, "该订单已抢完，请下次加油");
            }

            //添加应聘信息
            tenantsJobResumesMapper.insert(resume);

            //广播消息
            ResumeMessageModel model = new ResumeMessageModel();
            model.setJobId(applyJob.getId());
            model.setResumeId(resume.getId());
            model.setJobTenantUserId(applyJob.getTenantUserId());
            model.setTitle("您收到一份新简历，请点击查看详情");
            model.setJobName(applyJob.getPositionName());
            model.setStaffId(resume.getResumeTenantStaffId());
            model.setRemark("请在24小时内完成处理，逾期将自动拒绝简历。");
            String msg = JsonUtils.toJson(model);
            mq.publish(WechatConfig.Channel.RESUME_APPLY.getChannel(), msg);

            return resume.getId();
        } finally {
            redis.del("applyResume" + resume.getJobId() + resume.getResumeTenantStaffId());
        }
    }

    /**
     * 查询我投递的简历列表
     *
     * @param form
     * @return
     */
    @Override
    public Page<TenantsJobResume> getMyApplyResume(TenantJobResumeForm form) {

        String price = DictionarysCacheUtils.getServicePirceName(form.getSalaryRange());
        if (StringUtils.isNotBlank(price)) {
            String[] strs = price.split(",");
            form.setSalaryMin(new BigDecimal(strs[0]));
            form.setSalaryMax(strs.length > 1 && StringUtils.isNotBlank(strs[1]) ? new BigDecimal(strs[1]) : null);
        }

        Integer totalCount = tenantsJobResumesMapper.getMyApplyResumesCount(form);
        if (totalCount == null || totalCount.equals(0)) {
            form.setRecords(0);
            return form;
        }
        form.setRecords(totalCount);
        List<TenantsJobResume> resultList = tenantsJobResumesMapper.getMyApplyResumesList(form);
        form.setRows(resultList);
        return form;
    }

    /**
     * 查询我收到的简历列表
     *
     * @param form
     * @return
     */
    @Override
    public Page<TenantsJobResume> getMyResumeBox(TenantJobResumeForm form) {
        String ages = DictionarysCacheUtils.getAgeIntervalName(form.getAge());
        if (StringUtils.isNotBlank(ages)) {
            String[] strs = ages.split(",");
            form.setAgeStart(strs[0]);
            form.setAgeEnd(strs.length == 2 ? strs[1] : null);
        }
        if(StringUtils.isNotBlank(form.getOrderNo())){
        	form.setOrderNo(StringUtils.strLike(form.getOrderNo()));
        }
        Integer totalCount = tenantsJobResumesMapper.getMyResumesBoxCount(form);
        if (totalCount == null || totalCount.equals(0)) {
            form.setRecords(0);
            return form;
        }
        form.setRecords(totalCount);
        List<TenantsJobResume> resultList = tenantsJobResumesMapper.getMyResumesBoxList(form);
        form.setRows(resultList);
        return form;
    }

    /**
     * 查询简历详情
     *
     * @param id
     * @return
     */
    @Override
    public TenantsJobResume getTenantsJobsResumeDetail(Integer id) {
        return tenantsJobResumesMapper.getById(id);
    }

    /**
     * 审核简历
     * 1. 修改简历状态
     * 已通过/已拒绝/拒绝理由/审核时间
     *
     * @param resume
     * @return
     */
    @Override
    public Integer checkJobResumes(TenantsJobResume resume) {
        if (resume == null) {
            log.info("参数为空，请确认");
            throw new ServiceException("简历为空， 请确认");
        }
        if (StringUtils.isNotBlank(resume.getStatus())) {
            resume.setCheckDate(new Date());
        }
        tenantsJobResumesMapper.update(resume);

        TenantsJobResume rs = tenantsJobResumesMapper.getById(resume.getId());
        if (rs != null && rs.getStatus().equals(JobConst.ResumeStatus.REJECTS.getCode())
                && rs.getDeposit() != null && rs.getDeposit().compareTo(new BigDecimal(0)) > 0) {
            //拒绝简历，退还保证金
            commonService.thawAmount(rs.getResumeTenantId(), rs.getDeposit(), rs.getOrderNo(), "保证金解冻[简历拒绝]");

        }

        //广播消息
        BusinessModel model = new BusinessModel();
        model.setStaffId(rs.getResumeTenantStaffId());
        model.setUserId(rs.getResumeTenantUserId());
        model.setJobId(rs.getJobId());
        model.setResumeId(rs.getId());
        if(JobConst.ResumeStatus.PASSED.getCode().equals(resume.getStatus())){

            TenantsApps app = tenantsAppsMapper.selectByPrimaryKey(rs.getJobTenantId());

            model.setTitle("恭喜您，抢单处理结果通知。");
            model.setKeywordFirst("合作抢单");
            model.setKeywordSecond("通过");
            model.setKeywordThird("安排面试");
            model.setRemark(app.getWebsiteName());
        }else if(JobConst.ResumeStatus.REJECTS.getCode().equals(resume.getStatus())){
            model.setTitle("很遗憾，抢单处理结果通知。");
            model.setKeywordFirst("合作抢单");
            model.setKeywordSecond("拒绝");
            model.setKeywordThird(resume.getRemarks());
        }
        String msg = JsonUtils.toJson(model);
        //mq.lpush(WechatConfig.Queue.RESUME_CHECK_B.getQueue(), msg);
        mq.publish(WechatConfig.Channel.RESUME_CHECK.getChannel(), msg);

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelDelayResumeTask(TenantsJobResume resume) {
        TenantsJobResume update = new TenantsJobResume();
        //其他简历已取消
        update.setJobId(resume.getJobId());
        update.setId(resume.getId());
        update.setStatus(JobConst.ResumeStatus.CANCELED.getCode());
        update.setRemarks("到期未处理取消");
        tenantsJobResumesMapper.update(update);
        //解冻保证金
        if (resume.getDeposit() != null && resume.getDeposit().compareTo(new BigDecimal(0)) > 0) {
            commonService.thawAmount(resume.getResumeTenantId(), resume.getDeposit(), resume.getOrderNo(), "保证金解冻[简历超期取消]");
        }
    }


    /**
     * 订阅职位消息发布
     *
     * @param serviceTypes
     */
    @Override
    public void subscribeJobMessage(String[] serviceTypes) {
        return;
    }

}
