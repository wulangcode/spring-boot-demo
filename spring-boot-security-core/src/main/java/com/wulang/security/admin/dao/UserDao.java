package com.wulang.security.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wulang.security.admin.entity.collection.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2019-11-19 10:44:11
 */
@Mapper
public interface UserDao extends BaseMapper<User> {


}