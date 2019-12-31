package com.wulang.quartz.mapper;

import com.wulang.quartz.entity.QuartzJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobMapper {

    List<QuartzJob> listJob(@Param("jobName") String jobName);

    QuartzJob getJob(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup);

    int saveJob(QuartzJob job);

    int updateJobStatus(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup, @Param("status") String status);

    int removeQuartzJob(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup);

    int updateJob(QuartzJob quartz);
}
