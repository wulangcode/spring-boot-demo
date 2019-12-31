package com.wulang.security.admin.entity.collection;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (SysMenu)实体类
 *
 * @author makejava
 * @since 2019-11-23 09:50:38
 */
@Data
@TableName("sys_menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = -99805983478111112L;
    
    private Integer menuId;
    
    private String name;

    private String path;

    private String url;

    @TableField(exist = false)
    private String pUrl;
    
    private Integer parentId;
    
    private Integer orderNumber;

    @TableField(exist = false)
    private List<Role> roles;

    @TableField(exist = false)
    private String label;

    private Boolean enabled;

    private String icon;

    @TableField(exist = false)
    private MenuMeta meta;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private List<Menu> children;
}
