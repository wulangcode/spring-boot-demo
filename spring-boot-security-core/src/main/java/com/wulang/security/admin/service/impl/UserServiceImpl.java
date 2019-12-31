package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.entity.collection.User;
import com.wulang.security.admin.dao.UserDao;
import com.wulang.security.admin.entity.collection.UserRole;
import com.wulang.security.admin.service.UserRoleService;
import com.wulang.security.admin.service.UserService;
import com.wulang.security.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2019-11-19 10:44:11
 */
@Service
@Transactional(rollbackFor = BusinessException.class)
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService{

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public int addSysUser(String username, Integer roleId) {
    Long id = IdWorker.getId();
    //如果用户名存在，返回错误
        if (this.getOne(new QueryWrapper<User>().eq("username",username)) != null) {
        return -1;
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encode = encoder.encode("123456");
    User user = new User().setId(id).setUsername(username).setPassword(encode).setState(true).setCreateTime(new Date());
    UserRole role = new UserRole();
        role.setUserId(id);
        role.setRoleId(roleId);
        role.setCreateTime(new Date());
        if(this.save(user)){
        userRoleService.save(role);
        return 1;
    }
        return -1;
}
}