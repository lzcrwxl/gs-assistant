package com.fbee.modules.schedule;

import com.fbee.modules.mybatis.dao.TenantsInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobBatch extends ScheduledStarter {

    @Autowired
    TenantsInfoMapper tenantsInfoMapper;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void executeJob() {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
    }

    private void process() {
        //每月重置门店发布职位／抢单次数
        tenantsInfoMapper.resetJobResumeCount(0, 0);
    }
}
