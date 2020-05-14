package com.wulang.affair.service.impl;

import com.alibaba.fastjson.JSON;
import com.wulang.affair.dao.RedisDAO;
import com.wulang.affair.mapper.UserMapper;
import com.wulang.affair.model.User;
import com.wulang.affair.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户Service实现类
 * @author Administrator
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private RedisDAO redisDAO;

	@Override
	public User findUserInfo() {
		return userMapper.findUserInfo();
	}

	@Override
	public User getCachedUserInfo() {
        System.out.println("-----进到方法");
        User user1 = new User();
        user1.setAge(10);
        user1.setName("test");
        String jsonString = JSON.toJSONString(user1);
        redisDAO.set("test",jsonString);
        System.out.println("------设置完成");
		String userJSON = redisDAO.get("test");
        System.out.println("----------拿到对象："+userJSON);
        User user = JSON.parseObject(userJSON, User.class);
        return user;
	}

}
