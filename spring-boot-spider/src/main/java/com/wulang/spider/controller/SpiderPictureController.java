package com.wulang.spider.controller;

import com.wulang.spider.service.SpiderPictureService;
import com.wulang.spider.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wulang
 * @create 2020/5/2/9:19
 */
@RestController
@RequestMapping(value = "/spiderPicture")
public class SpiderPictureController {

    @Autowired(required = false)
    private SpiderPictureService spiderPictureService;

    @ApiOperation(value = "爬取阿里巴巴国际站图片.wl")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "spiderPictureURL", value = "需要爬取的网站地址", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping(value = "/downloadSpiderPicture")
    public Result downloadSpiderPicture(String spiderPictureURL, HttpServletResponse response) {
        return spiderPictureService.downloadSpiderPictureSemaphore(spiderPictureURL, response);
    }

    @ApiOperation(value = "爬取京东图片.wl")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keywords", value = "输入查询的数据", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping(value = "/parseJD")
    public Result parseJD(String keywords, HttpServletResponse response) throws IOException {
        return spiderPictureService.parseJD(keywords);
    }
}
