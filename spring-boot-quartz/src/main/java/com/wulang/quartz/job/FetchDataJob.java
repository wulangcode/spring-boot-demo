package com.wulang.quartz.job;

import com.wulang.quartz.entity.User;
import com.wulang.quartz.mapper.UserMapper;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Random;
import java.util.stream.IntStream;

public class FetchDataJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchDataJob.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//        String com = (String)context.getMergedJobDataMap().get("COM");
        // TODO 业务处理   取出方法参数
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String jobSays = dataMap.getString("COM");

        Random random = new Random();
        IntStream intStream = random.ints(18, 100);
        int first = intStream.limit(1).findFirst().getAsInt();
        int count = userMapper.saveUser(new User("zhangsan" + first, first));
        if (count == 0) {
            LOGGER.error("用户保存失败！");
            return;
        }
        LOGGER.info("用户保存成功");
    }
}
