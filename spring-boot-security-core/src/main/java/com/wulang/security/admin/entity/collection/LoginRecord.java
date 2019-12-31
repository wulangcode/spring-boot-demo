package com.wulang.security.admin.entity.collection;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 登录日志(SysLoginRecord)实体类
 *
 * @author makejava
 * @since 2019-11-27 13:42:08
 */
@Data
@TableName("sys_login_record")
public class LoginRecord implements Serializable {
    private static final long serialVersionUID = -10858384222186911L;
    //主键
    private Long id;

    private String username;
    //用户id
    private Long userId;
    //操作系统
    private String osName;
    //设备名
    private String device;
    //浏览器类型
    private String browserType;
    //ip地址
    private String ipAddress;
    //登录时间
    private Date createTime;

}