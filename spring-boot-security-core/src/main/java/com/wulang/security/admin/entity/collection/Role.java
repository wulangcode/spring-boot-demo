package com.wulang.security.admin.entity.collection;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (Role)实体类
 *
 * @author makejava
 * @since 2019-11-22 14:23:38
 */
@Data
@TableName("role")
@Accessors(chain = true)
public class Role implements Serializable {
    private static final long serialVersionUID = -42729431821391239L;
    private Integer id;
    private String roleName;
    private String role;
    private String remake;
    private Date createTime;
    private Date updateTime;

}