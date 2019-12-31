package com.wulang.security.common.security;

import com.wulang.security.common.ResultModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 身份验证失败处理程序
 *
 * @author: wulang
 * @date: 2019/11/27
 * @description:
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        AuthenticationException e) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        ResultModel resultModel = null;
        if (e instanceof BadCredentialsException ||
                e instanceof UsernameNotFoundException) {
            resultModel = new ResultModel().failure("账户名或者密码输入错误!");
        } else if (e instanceof LockedException) {
            resultModel = new ResultModel().failure("账户被锁定，请联系管理员!");
        } else if (e instanceof CredentialsExpiredException) {
            resultModel = new ResultModel().failure("密码过期，请联系管理员!");
        } else if (e instanceof AccountExpiredException) {
            resultModel = new ResultModel().failure("账户过期，请联系管理员!");
        } else if (e instanceof DisabledException) {
            resultModel = new ResultModel().failure("账户被禁用，请联系管理员!");
        } else {
            resultModel = new ResultModel().failure("登录失败!");
        }
        resp.setStatus(401);
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = resp.getWriter();
        out.write(om.writeValueAsString(resultModel));
        out.flush();
        out.close();
    }
}
