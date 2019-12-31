package com.wulang.security.common.exception;

import com.wulang.security.common.ResultModel;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author: wulang
 * @date: 2019/11/22
 * @description:
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResultModel userNotFound() {
        return new ResultModel().failure("用户不存在");
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ResultModel badCredential() {
        return new ResultModel().failure("用户未登录");
    }

}
