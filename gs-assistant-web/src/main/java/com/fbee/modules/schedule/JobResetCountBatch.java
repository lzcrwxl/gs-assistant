package com.fbee.modules.schedule;

import com.fbee.modules.mybatis.dao.TenantsJobsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 商家职位刷新时效24小时
 */
@Service
public class JobResetCountBatch extends ScheduledStarter {

    @Autowired
    TenantsJobsMapper jobsMapper;

    /**
     * 商家职位刷新时效24小时
     * 每天0点批处理
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void executeJob() {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                refreshJob();
            }
        });
    }
    private void refreshJob(){
        log.info("系统 reset refresh job 批处理开始...");
        jobsMapper.resetRefresh();
        log.info("系统 reset refresh job 批处理结束...");
    }
}
