package com.wulang.security.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wulang.security.admin.entity.collection.Concurrent;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: wulang
 * @date: 2019/12/9
 * @description:
 */
@Mapper
public interface ConcurretDao extends BaseMapper<Concurrent> {
}
