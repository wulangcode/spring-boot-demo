package com.wulang.security.distributed.order.model;

/**
 * @author wulang
 * @create 2019/12/21/16:09
 */

import lombok.Data;

/**
 * 用户信息
 */
@Data
public class UserDTO {

    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 姓名
     */
    private String fullname;
}

