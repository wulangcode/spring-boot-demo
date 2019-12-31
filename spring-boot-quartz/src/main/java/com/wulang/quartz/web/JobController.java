package com.wulang.quartz.web;

import com.github.pagehelper.PageInfo;
import com.wulang.quartz.common.Result;
import com.wulang.quartz.entity.QuartzJob;
import com.wulang.quartz.service.IJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {
	private final static Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private IJobService jobService;
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/add")
	public Result save(QuartzJob quartz){
		LOGGER.info("新增任务");
		quartz.setJobParameter("COM");
		Result result = jobService.saveJob(quartz);
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/edit")
	public Result edit(QuartzJob quartz){
		LOGGER.info("编辑任务");
		Result result = jobService.updateJob(quartz);
		return result;
	}
	@PostMapping("/list")
	public PageInfo list(String jobName,Integer pageNo,Integer pageSize){
		LOGGER.info("任务列表");
		PageInfo pageInfo = jobService.listQuartzJob(jobName, pageNo, pageSize);
		return pageInfo;
	}

	@PostMapping("/trigger")
	public  Result trigger(String jobName, String jobGroup) {
		LOGGER.info("触发任务");
		Result result = jobService.triggerJob(jobName, jobGroup);
		return result;
	}

	@PostMapping("/pause")
	public  Result pause(String jobName, String jobGroup) {
		LOGGER.info("停止任务");
		Result result = jobService.pauseJob(jobName, jobGroup);
		return result;
	}

	@PostMapping("/resume")
	public  Result resume(String jobName, String jobGroup) {
		LOGGER.info("恢复任务");
		Result result = jobService.resumeJob(jobName, jobGroup);
		return result;
	}

	@PostMapping("/remove")
	public  Result remove(String jobName, String jobGroup) {
		LOGGER.info("移除任务");
        Result result = jobService.removeJob(jobName, jobGroup);
        return result;
	}
}
