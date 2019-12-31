package com.wulang.security.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wulang.security.admin.entity.collection.LoginRecord;
import com.wulang.security.admin.service.LoginRecordsService;
import com.wulang.security.common.PageResponse;
import com.wulang.security.common.ResultModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: wulang
 * @date: 2019/11/27
 * @description:
 */
@CrossOrigin(allowCredentials = "true")
@RestController
@Api(value = "登录日志", description = "登录日志接口")
@RequestMapping("/ship/system/loginRecord")
public class LoginRecordController {

    @Autowired
    private LoginRecordsService loginRecordsService;

    @GetMapping("/list")
    @ApiOperation(value = "分页查询登录日志", produces = "application/json")
    public ResultModel list(Integer limit, Integer page, String username, String startDate, String endDate) {

        PageHelper.startPage(page, limit);
        List<LoginRecord> loginRecordIPage = null;
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(startDate)) {
            loginRecordIPage = loginRecordsService.list(new QueryWrapper<LoginRecord>().eq("username", username).gt("create_time", startDate).lt("create_time", endDate));
        } else if (StringUtils.isNotEmpty(username) && StringUtils.isEmpty(startDate)) {
            loginRecordIPage = loginRecordsService.list(new QueryWrapper<LoginRecord>().eq("username", username));
        } else if (StringUtils.isEmpty(username) && StringUtils.isNotEmpty(startDate)) {
            loginRecordIPage = loginRecordsService.list(new QueryWrapper<LoginRecord>().gt("create_time", startDate).lt("create_time", endDate));
        } else if (StringUtils.isEmpty(username) && StringUtils.isEmpty(startDate)) {
            loginRecordIPage = loginRecordsService.list();
        }
        PageInfo<LoginRecord> recordPageInfo = new PageInfo<>(loginRecordIPage);
        PageResponse<Object> pages = new PageResponse(recordPageInfo.getSize(), recordPageInfo.getPageNum(), recordPageInfo.getTotal(), recordPageInfo.getPages(), recordPageInfo.getList());
        return ResultModel.selectPaginationSuccess(pages);
    }
}
