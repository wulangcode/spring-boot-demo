package com.wulang.security.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wulang.security.admin.entity.collection.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: wulang
 * @date: 2019/11/23
 * @description:
 */
@Mapper
public interface UserRoleDao extends BaseMapper<UserRole> {
}
