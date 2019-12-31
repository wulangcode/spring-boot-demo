package com.wulang.security.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wulang.security.admin.entity.collection.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: wulang
 * @date: 2019/11/22
 * @description:
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {
    List<Role> listByUserId(@Param("id") Long id);
}
