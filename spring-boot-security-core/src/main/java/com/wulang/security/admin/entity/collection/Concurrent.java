package com.wulang.security.admin.entity.collection;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: wulang
 * @date: 2019/12/9
 * @description:
 */
@Data
@Accessors(chain = true)
public class Concurrent {
    private Integer id;
    private String ip;
    private String username;
    private String password;
}
