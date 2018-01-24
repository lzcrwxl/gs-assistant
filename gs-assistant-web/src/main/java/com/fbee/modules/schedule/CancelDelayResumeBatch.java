package com.fbee.modules.schedule;

import com.fbee.modules.mybatis.dao.TenantsJobResumesMapper;
import com.fbee.modules.mybatis.dao.TenantsJobsMapper;
import com.fbee.modules.mybatis.model.TenantsJobResume;
import com.fbee.modules.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 简历投递后24小时未处理自动取消
 */
@Service
public class CancelDelayResumeBatch extends ScheduledStarter {

    @Autowired
    JobService jobService;

    @Autowired
    TenantsJobResumesMapper tenantsJobResumesMapper;

    /**
     * 每2分钟跑一次
     */
    @Scheduled(cron = "0 */2 * * * ?")
    public void executeJob() {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                rejectStaff();
            }
        });
    }

    private void rejectStaff(){
        log.info("系统自动拒绝阿姨信息批处理开始...");
        List<TenantsJobResume> resumes = tenantsJobResumesMapper.getDelayResumeList();
        if (resumes == null || resumes.size() == 0) {
            return;
        }
        for (TenantsJobResume resume : resumes) {
            log.info(String.format("cancelDelayResumeTask : %s", resume.getId()));
            try{
                jobService.cancelDelayResumeTask(resume);
            }catch (Exception e){
                log.info(String.format("cancelDelayResumeTask : %s ,e[%s]", resume.getId(), e.getMessage()));
            }
        }
        log.info("系统自动拒绝阿姨信息批处理结束...");
    }


}
