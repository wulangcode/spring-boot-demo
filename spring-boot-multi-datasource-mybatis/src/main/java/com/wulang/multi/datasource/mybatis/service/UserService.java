package com.wulang.multi.datasource.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wulang.multi.datasource.mybatis.model.User;

/**
 * 数据服务层
 *
 * @author wulang
 * @create 2019/12/31/12:13
 */
public interface UserService extends IService<User> {

    /**
     * 添加 User
     *
     * @param user 用户
     */
    void addUser(User user);
}
