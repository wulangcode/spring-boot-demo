package com.wulang.security.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wulang.security.admin.entity.collection.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: wulang
 * @date: 2019/11/23
 * @description:
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu> {
    List<Menu> getAllMenu();

    List<Menu> listByRoleId(Integer roleId);
}
