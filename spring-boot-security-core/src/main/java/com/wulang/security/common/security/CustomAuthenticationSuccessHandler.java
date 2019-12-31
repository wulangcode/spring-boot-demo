package com.wulang.security.common.security;

import com.wulang.security.admin.entity.collection.LoginRecord;
import com.wulang.security.admin.service.LoginRecordsService;
import com.wulang.security.common.ResultModel;
import com.wulang.security.common.utils.UserAgentGetter;
import com.wulang.security.common.utils.UserDetailUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 身份验证成功处理程序
 *
 * @author: wulang
 * @date: 2019/11/27
 * @description:
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginRecordsService loginRecordsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        Authentication auth) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        ResultModel respBean = new ResultModel().success1("登录成功!", UserDetailUtils.getCurrentUser());
        addLoginRecord(UserDetailUtils.getCurrentUser().getId(), req);
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = resp.getWriter();
        out.write(om.writeValueAsString(respBean));
        out.flush();
        out.close();
    }

    private void addLoginRecord(Long userId, HttpServletRequest request) {
        UserAgentGetter agentGetter = new UserAgentGetter(request);
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUserId(userId);
        loginRecord.setUsername(UserDetailUtils.getCurrentUser().getUsername());
        loginRecord.setOsName(agentGetter.getOS());
        loginRecord.setDevice(agentGetter.getDevice());
        loginRecord.setBrowserType(agentGetter.getBrowser());
        loginRecord.setIpAddress(agentGetter.getIpAddr());
        loginRecord.setCreateTime(new Date());
        loginRecordsService.save(loginRecord);
    }
}
