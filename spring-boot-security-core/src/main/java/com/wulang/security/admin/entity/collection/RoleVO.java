package com.wulang.security.admin.entity.collection;

import lombok.Data;

import java.util.List;

/**
 * @author: wulang
 * @date: 2019/12/4
 * @description:
 */
@Data
public class RoleVO {
    private List<Integer> authIds;
    private Integer roleId;
}
