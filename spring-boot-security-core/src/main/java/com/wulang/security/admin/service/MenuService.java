package com.wulang.security.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wulang.security.admin.entity.collection.Menu;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2019-11-19 10:44:11
 */
public interface MenuService extends IService<Menu> {
    /**
     * 查询所有用户能访问已激活的路径的集合
     *
     * @return
     */
    List<Menu> getAllMenu();

    List<Menu> listByRoleId(Integer roleId);
}