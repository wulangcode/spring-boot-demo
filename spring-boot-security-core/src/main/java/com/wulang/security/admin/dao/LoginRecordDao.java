package com.wulang.security.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wulang.security.admin.entity.collection.LoginRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: wulang
 * @date: 2019/11/27
 * @description:
 */
@Mapper
public interface LoginRecordDao extends BaseMapper<LoginRecord> {
}
