package com.wulang.security.system.controller;

import com.wulang.security.common.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author: wulang
 * @date: 2019/11/23
 * @description:
 */
@ApiIgnore
@RestController
public class LoginController {
    @GetMapping("/login_p")
    public RespBean login() {
        return RespBean.error("尚未登录，请登录!");
    }
}
