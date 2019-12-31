package com.wulang.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wulang.quartz.common.Result;
import com.wulang.quartz.entity.QuartzJob;
import com.wulang.quartz.enums.JobStatus;
import com.wulang.quartz.mapper.JobMapper;
import com.wulang.quartz.service.IJobService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements IJobService {

    private static final String TRIGGER_IDENTITY = "trigger";

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private JobMapper jobMapper;

    @Override
    public PageInfo listQuartzJob(String jobName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<QuartzJob> jobList = jobMapper.listJob(jobName);
        PageInfo pageInfo = new PageInfo(jobList);
        return pageInfo;
    }

    @Override
    public Result saveJob(QuartzJob quartz){
        try {
            schedulerJob(quartz);

            quartz.setTriggerState(JobStatus.RUNNING.getStatus());
            quartz.setOldJobGroup(quartz.getJobGroup());
            quartz.setOldJobName(quartz.getJobName());
            jobMapper.saveJob(quartz);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
        return Result.ok();
    }

    @Override
    public Result triggerJob(String jobName, String jobGroup) {
        JobKey key = new JobKey(jobName,jobGroup);
        try {
            scheduler.triggerJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return Result.error();
        }
        return Result.ok();
    }

    @Override
    public Result pauseJob(String jobName, String jobGroup) {
        JobKey key = new JobKey(jobName,jobGroup);
        try {
            scheduler.pauseJob(key);
            jobMapper.updateJobStatus(jobName, jobGroup, JobStatus.PAUSED.getStatus());
        } catch (SchedulerException e) {
            e.printStackTrace();
            return Result.error();
        }
        return Result.ok();
    }

    @Override
    public Result resumeJob(String jobName, String jobGroup) {
        JobKey key = new JobKey(jobName,jobGroup);
        try {
            scheduler.resumeJob(key);
            jobMapper.updateJobStatus(jobName,jobGroup, JobStatus.RUNNING.getStatus());
        } catch (SchedulerException e) {
            e.printStackTrace();
            return Result.error();
        }
        return Result.ok();
    }

    @Override
    public Result removeJob(String jobName, String jobGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(TRIGGER_IDENTITY + jobName, jobGroup);
            scheduler.pauseTrigger(triggerKey);                                 // 停止触发器
            scheduler.unscheduleJob(triggerKey);                                // 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));              // 删除任务
            jobMapper.removeQuartzJob(jobName, jobGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
        return Result.ok();
    }

    @Override
    public QuartzJob getJob(String jobName, String jobGroup) {
        QuartzJob job = jobMapper.getJob(jobName, jobGroup);
        return job;
    }

    @Override
    public Result updateJob(QuartzJob quartz) {
        try {

            scheduler.deleteJob(new JobKey(quartz.getOldJobName(),quartz.getOldJobGroup()));
            schedulerJob(quartz);

            quartz.setOldJobGroup(quartz.getJobGroup());
            quartz.setOldJobName(quartz.getJobName());
            jobMapper.updateJob(quartz);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
        return Result.ok();
    }

    @Override
    public void schedulerJob(QuartzJob job) throws Exception {
        //构建job信息
        Class cls = Class.forName(job.getJobClassName()) ;
        // cls.newInstance(); // 检验类是否存在
        JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(job.getJobName(),job.getJobGroup())
                .withDescription(job.getDescription()).build();
        /**
         * 为每个执行类传入方法参数
         */
        jobDetail.getJobDataMap().put("COM",job.getJobParameter());

        // 触发时间点
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression().trim());
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER_IDENTITY + job.getJobName(), job.getJobGroup())
                .startNow().withSchedule(cronScheduleBuilder).build();
        //交由Scheduler安排触发
        scheduler.scheduleJob(jobDetail, trigger);
    }
}