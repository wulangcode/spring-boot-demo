package com.wulang.security.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wulang.security.admin.entity.collection.ReferenceValue;
import org.apache.ibatis.annotations.Mapper;

/**
 * (FerroPara)表数据库访问层
 *
 * @author makejava
 * @since 2019-11-19 10:43:22
 */
@Mapper
public interface ReferenceDao extends BaseMapper<ReferenceValue> {


}