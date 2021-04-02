package com.wulang.rename.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author wulang
 * @create 2021/3/31/21:19
 */
@Data
public class User {
    private Long id;

    private String userName;

    private String sex;

    private String domain;

    private String deptName;

    private String email;

    private String mobile;

    private Date createTime;

    private Date updateTime;
}
