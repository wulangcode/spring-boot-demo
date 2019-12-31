package com.wulang.quartz.init;

import com.wulang.quartz.entity.QuartzJob;
import com.wulang.quartz.enums.JobStatus;
import com.wulang.quartz.mapper.JobMapper;
import com.wulang.quartz.service.IJobService;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationInit implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInit.class);

    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private IJobService jobService;
    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        loadJobToQuartz();
    }

    private void loadJobToQuartz() throws Exception {
        //每次重启服务时，可以重新加载数据库中的任务列表
        LOGGER.info("quartz job load...加载数据库任务列表");
        List<QuartzJob> jobs = jobMapper.listJob("");
        for(QuartzJob job : jobs) {
            jobService.schedulerJob(job);
            if (JobStatus.PAUSED.equals(job.getTriggerState())) {
                scheduler.pauseJob(new JobKey(job.getJobName(), job.getJobGroup()));
            }
        }
    }
}
