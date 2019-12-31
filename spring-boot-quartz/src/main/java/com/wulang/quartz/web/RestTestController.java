package com.wulang.quartz.web;

import com.wulang.quartz.entity.TestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest")
public class RestTestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTestController.class);

    @RequestMapping("/getEntity")
    @ResponseBody
    public TestEntity getEntity() {
        LOGGER.info("收到请求...");
        return new TestEntity("zhangsan", 22);
    }

}
