package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.dao.UserRoleDao;
import com.wulang.security.admin.entity.collection.UserRole;
import com.wulang.security.admin.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author: wulang
 * @date: 2019/11/23
 * @description:
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {
}
