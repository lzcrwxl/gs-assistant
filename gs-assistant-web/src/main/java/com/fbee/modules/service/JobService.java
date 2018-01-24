package com.fbee.modules.service;

import com.fbee.modules.core.page.Page;
import com.fbee.modules.core.page.form.TenantJobForm;
import com.fbee.modules.core.page.form.TenantJobResumeForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.mybatis.entity.TenantsJobsEntity;
import com.fbee.modules.mybatis.model.TenantsJobCheck;
import com.fbee.modules.mybatis.model.TenantsJobResume;
import com.fbee.modules.mybatis.model.TenantsJobs;
import com.fbee.modules.service.basic.ServiceException;

import java.util.List;

public interface JobService {

    /**
     * 租户招聘管理<br/>
     * 获取招聘列表信息
     * @return
     */
    Page<TenantsJobs> getTenantsJobsInfoList(TenantJobForm form);

    /**
     * 租户招聘管理<br/>
     * 保存租户招聘信息
     * @return
     */
    Integer saveTenantsJobsInfo(TenantsJobs TenantsJobs) throws ServiceException;

    /**
     * 租户招聘管理<br/>
     * 根据主键获取租户招聘信息
     * @param id
     * @return
     */
    TenantsJobs getTenantsJobsDetail(Integer id);

    /**
     * 租户招聘管理<br/>
     * 更新租户招聘信息
     * @return
     */
    Integer updateTenantsJobsInfo(TenantsJobs TenantsJobs);

    /**
     * 检查招聘发布条件
     * @param tenantId
     * @return
     */
    TenantsJobCheck getTenantsJobsCheck(Integer tenantId, Integer userId);

    /**
     * 投递简历
     * @param resume
     * @throws ServiceException
     */
    Integer applyResume(TenantsJobResume resume) throws ServiceException;

    /**
     * 查询简历列表
     * @param form
     * @return
     */
    Page<TenantsJobResume> getMyApplyResume(TenantJobResumeForm form);
    /**
     * 查询简历列表
     * @param form
     * @return
     */
    Page<TenantsJobResume> getMyResumeBox(TenantJobResumeForm form);

    /**
     * 查询简历详情
     * @param id
     * @return
     */
    TenantsJobResume getTenantsJobsResumeDetail(Integer id);

    /**
     * 审核简历
     * @param resume
     * @return
     */
    Integer checkJobResumes(TenantsJobResume resume);

    /**
     * 取消延期的简历信息
     */
    void cancelDelayResumeTask(TenantsJobResume resume);

    /**
     * 订阅职位消息发布
     * @param serviceTypes
     */
    void subscribeJobMessage(String[] serviceTypes);
}
