package com.wulang.security.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wulang.security.admin.entity.collection.Role;
import com.wulang.security.admin.entity.collection.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: wulang
 * @date: 2019/11/22
 * @description:
 */
@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //从数据库尝试读取该用户
        User user = userService.getOne(new QueryWrapper<User>().select("id", "username", "password", "state").eq("username", s));
        //用户不存在抛出异常
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //将数据库形式的roles解析为UserDetails的权限集
        List<Role> roles = roleService.listByUserId(user.getId());
        user.setRoles(roles);
        /**
         * AuthorityUtils.commaSeparatedStringToAuthorityList 是spring security提供的
         * 该方法用于将逗号隔开的权限集字符串切割成可用权限对象列表
         * （数据库定义为 roles ：用户角色，多个角色之间用逗号隔开）
         */
        AuthorityUtils.commaSeparatedStringToAuthorityList("");
        return user;
    }
}
