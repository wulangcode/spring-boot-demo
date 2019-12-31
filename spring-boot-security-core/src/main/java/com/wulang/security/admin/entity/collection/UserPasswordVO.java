package com.wulang.security.admin.entity.collection;

import lombok.Data;

/**
 * @author: wulang
 * @date: 2019/12/5
 * @description:
 */
@Data
public class UserPasswordVO {
    private Long Id;
    private String oldPassword;
    private String newPassword;
}
