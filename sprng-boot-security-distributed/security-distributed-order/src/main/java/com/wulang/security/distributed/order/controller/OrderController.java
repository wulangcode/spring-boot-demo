package com.wulang.security.distributed.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wulang
 * @create 2019/12/21/15:23
 */
@RestController
public class OrderController {

    @GetMapping(value = "/r1")
    @PreAuthorize("hasAuthority('p1')")//拥有p1权限方可访问此url
    public String r1(){
        //获取用户身份信息
//        UserDTO  userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "访问资源1";
    }

}
