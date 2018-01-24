package com.fbee.modules.schedule;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Service
@EnableScheduling
public class ScheduledStarter {

    protected final Logger log = LoggerFactory.getLogger("schedule.log");

    protected final Executor exec = new ScheduledThreadPoolExecutor(5,
            new BasicThreadFactory.Builder().namingPattern("jc-assistant-schedule-pool-%d").daemon(true).build());

}
