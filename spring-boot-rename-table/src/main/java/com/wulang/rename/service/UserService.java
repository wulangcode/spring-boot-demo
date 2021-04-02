package com.wulang.rename.service;

import com.wulang.rename.pojo.User;

import java.util.List;

/**
 * @author wulang
 * @create 2021/3/31/21:36
 */
public interface UserService {

    boolean save(User record);

    boolean saveBatch(List<User> records);

    User testJoin();

    User testLimit();

    void testUpdate(User user);
}
