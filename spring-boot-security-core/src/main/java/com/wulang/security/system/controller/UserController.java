package com.wulang.security.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wulang.security.admin.entity.collection.Role;
import com.wulang.security.admin.entity.collection.User;
import com.wulang.security.admin.entity.collection.UserPasswordVO;
import com.wulang.security.admin.entity.collection.UserRole;
import com.wulang.security.admin.service.RoleService;
import com.wulang.security.admin.service.UserRoleService;
import com.wulang.security.admin.service.UserService;
import com.wulang.security.common.PageResponse;
import com.wulang.security.common.ResultModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理
 */
@CrossOrigin(allowCredentials = "true")
@RestController
@Api(value = "用户管理", description = "用户管理接口")
@RequestMapping("/ship/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;


    @PostMapping(value = "/user")
    @ApiOperation(value = "添加用户", produces = "application/json")
    public ResultModel addUser(@RequestBody User user) {
        int i = userService.addSysUser(user.getUsername(), user.getRoleId());
        if (i == 1) {
            return ResultModel.success1("添加用户成功!,初始密码为123456");
        } else if (i == -1) {
            return ResultModel.failure1("用户已存在");
        }
        return ResultModel.failure();
    }

    @PutMapping(value = "/user")
    @ApiOperation(value = "修改用户", produces = "application/json")
    public ResultModel updateUser(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            return ResultModel.updateFailure();
        }
        User username = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (username != null) {
            return ResultModel.updateFailure("用户名已存在");
        }
        boolean b = userService.update(new UpdateWrapper<User>().set("username", user.getUsername()).eq("id", user.getId()));
        boolean update = userRoleService.update(new UpdateWrapper<UserRole>().set("role_id", user.getRoleId()).eq("user_id", user.getId()));
        if (b && update) {
            return ResultModel.updateSuccess();
        }
        return ResultModel.updateFailure();
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页查询用户列表", produces = "application/json")
    public ResultModel list(Integer limit, Integer page, String username) {
        PageHelper.startPage(page, limit);
        List<User> userIPage = null;
        if (StringUtils.isBlank(username)) {
            userIPage = userService.list();
        } else {
            userIPage = userService.list(new QueryWrapper<User>().eq("username", username));
        }
        List<User> userList = new ArrayList<>();
        for (User user : userIPage) {
            User newUser = new User();
            List<UserRole> userRole = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
            List<Role> roles = new ArrayList<>();
            for (UserRole role : userRole) {
                Role one = roleService.getOne(new QueryWrapper<Role>().eq("id", role.getRoleId()));
                roles.add(one);
            }
            BeanUtils.copyProperties(user, newUser);
            newUser.setRoles(roles);
            userList.add(newUser);
        }
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        PageResponse<Object> pages = new PageResponse(userPageInfo.getSize(), userPageInfo.getPageNum(), userPageInfo.getTotal(), userPageInfo.getPages(), userPageInfo.getList());

        return ResultModel.selectPaginationSuccess(pages, userList);
    }

    @PutMapping("/lock")
    @ApiOperation(value = "更改用户状态", produces = "application/json")
    public ResultModel lockUser(@RequestBody User user) {
        if (user.getId() == null || user.getState() == null) {
            return ResultModel.updateFailure();
        }
        boolean update = userService.update(new UpdateWrapper<User>().set("state", user.getState()).eq("id", user.getId()));
        if (update) {
            return ResultModel.updateSuccess();
        }
        return ResultModel.updateFailure();

    }

    @DeleteMapping("/user")
    @ApiOperation(value = "删除用户", produces = "application/json")
    public ResultModel deleteUser(@RequestBody User user) {
        if (user.getId() == null) {
            return ResultModel.deleteFailure();
        }
        boolean update = userService.remove(new QueryWrapper<User>().eq("id", user.getId()));
        if (update) {
            return ResultModel.deleteSuccess();
        }
        return ResultModel.deleteFailure();
    }

    @PutMapping("/resetPassword")
    @ApiOperation(value = "重置密码", produces = "application/json")
    public ResultModel resetPassword(@RequestBody User user) {
        if (user.getId() == null) {
            return ResultModel.failure();
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        boolean update = userService.update(new UpdateWrapper<User>().set("password", encode).eq("id", user.getId()));
        if (update) {
            return ResultModel.updateSuccess();
        }
        return ResultModel.failure();
    }

    @PutMapping("/updatePassword")
    @ApiOperation(value = "更改密码", produces = "application/json")
    public ResultModel updatePassword(@RequestBody UserPasswordVO user) {
        if (user.getId() == null) {
            return ResultModel.failure();
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPassword = encoder.encode(user.getNewPassword());
        User one = userService.getOne(new QueryWrapper<User>().eq("id", user.getId()));
        if (!encoder.matches(user.getOldPassword(), one.getPassword())) {
            return ResultModel.updateFailure("旧密码错误");
        }
        boolean update = userService.update(new UpdateWrapper<User>().set("password", newPassword).eq("id", user.getId()));
        if (update) {
            return ResultModel.updateSuccess();
        }
        return ResultModel.failure();
    }
}
