package com.wulang.security.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wulang.security.admin.entity.collection.Role;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2019-11-19 10:44:11
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取角色
     * @param id
     * @return
     */
    List<Role> listByUserId(Long id);
}