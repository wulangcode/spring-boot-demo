package com.wulang.security.admin.entity.collection;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: wulang
 * @date: 2019/11/26
 * @description:
 */
@Data
@TableName("sys_menu_role")
public class MenuRole {
    private Integer id;
    private Integer menuId;
    private Integer roleId;
}
