package com.wulang.security.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wulang.security.admin.entity.collection.MenuRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: wulang
 * @date: 2019/11/26
 * @description:
 */
@Mapper
public interface MenuRoleDao extends BaseMapper<MenuRole> {
    int insertRoleAuths(Integer roleId, List<Integer> authIds);
}
