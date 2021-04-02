package com.wulang.rename.dao;

import com.wulang.rename.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wulang
 * @create 2021/3/31/21:37
 */
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    int insertBatch(List<User> records);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User testJoin();

    User testLimit();

    void testUpdate(User user);
}
