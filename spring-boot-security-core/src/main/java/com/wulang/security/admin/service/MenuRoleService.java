package com.wulang.security.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wulang.security.admin.entity.collection.MenuRole;

import java.util.List;

/**
 * @author: wulang
 * @date: 2019/11/26
 * @description:
 */
public interface MenuRoleService extends IService<MenuRole> {
    boolean updateRoleAuth(Integer roleId, List<Integer> authIds);
}
