package com.wulang.rename.service.impl;

import com.wulang.rename.dao.UserMapper;
import com.wulang.rename.pojo.User;
import com.wulang.rename.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wulang
 * @create 2021/3/31/21:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public boolean save(User record) {
        int count = userMapper.insert(record);
        return count > 0;
    }

    @Override
    public boolean saveBatch(List<User> records) {
        return userMapper.insertBatch(records) > 0;
    }

    @Override
    public User testJoin() {
        return userMapper.testJoin();
    }

    @Override
    public User testLimit() {
        return userMapper.testLimit();
    }

    @Override
    public void testUpdate(User user) {
        userMapper.testUpdate(user);
    }
}
