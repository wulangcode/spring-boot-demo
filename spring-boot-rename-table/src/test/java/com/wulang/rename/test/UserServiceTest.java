package com.wulang.rename.test;

import com.wulang.rename.pojo.User;
import com.wulang.rename.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * @author wulang
 * @create 2021/3/31/21:42
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testJoin() {
        User user = userService.testJoin();
        System.out.println(user);
        Assert.notNull(user, "查询出错");
    }

    @Test
    public void testLimit() {
        User user = userService.testLimit();
        System.out.println(user);
        Assert.notNull(user, "查询出错");
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(5L);
        user.setUserName("测试修改名称2");
        userService.testUpdate(user);
    }
}
