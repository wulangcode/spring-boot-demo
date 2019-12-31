package com.wulang.security.admin.entity.collection;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (UserRole)实体类
 *
 * @author makejava
 * @since 2019-11-22 14:23:56
 */
@Data
public class UserRole implements Serializable {
    private static final long serialVersionUID = 938350323821489393L;

    @TableField(exist = false)
    private Integer id;
    
    private Long userId;
    
    private Integer roleId;

    private Date createTime;

    private Date updateTime;


}