package com.fbee.modules.schedule;


import com.fbee.modules.mybatis.dao.TenantsStaffsInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class StaffWorkStatusBatch extends ScheduledStarter {

    @Autowired
    TenantsStaffsInfoMapper tenantsStaffsInfoMapper;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void executeJob() {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
    }

    private void process() {
        log.info("阿姨工作状态批处理开始...");
        tenantsStaffsInfoMapper.updateStaffWorkStatus();
        tenantsStaffsInfoMapper.updateStaffWorkStatusInfo();
        log.info("阿姨工作状态批处理结束...");
    }
}
