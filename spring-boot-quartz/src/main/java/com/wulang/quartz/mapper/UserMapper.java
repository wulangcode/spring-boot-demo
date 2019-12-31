package com.wulang.quartz.mapper;

import com.wulang.quartz.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int saveUser(User user);
}
