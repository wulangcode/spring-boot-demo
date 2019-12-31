package com.wulang.batis.admin.controller;

import com.wulang.batis.admin.entity.Test;
import com.wulang.batis.admin.entity.Two;
import com.wulang.batis.admin.service.TestService;
import com.wulang.batis.admin.service.TwoService;
import com.wulang.batis.common.ResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(value = "多数据源测试", description = "多数据源测试接口")
@RequestMapping("/wulang/batis")
public class FerroParaController {

    @Autowired
    private TestService testService;

    @Autowired
    private TwoService twoService;
    @ApiOperation(value = "多数据源测试", produces = "application/json")
    @GetMapping("/test")
    public ResultModel getFerroval() {
        Test test = testService.getById(1);
        Two byId = twoService.getById(1);
        System.out.println(test.toString());
        System.out.println(byId.toString());
        return null;
    }



}