package com.wulang.security.distributed.uaa.model;

import lombok.Data;

/**
 * @author wulang
 * @create 2019/12/21/13:56
 */
@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
