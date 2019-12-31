package com.wulang.security.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wulang.security.admin.entity.monitor.TbTitleIcon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: wulang
 * @date: 2019/11/29
 * @description:
 */
@Mapper
public interface TitleIconDao extends BaseMapper<TbTitleIcon> {
    Boolean updateByTitle(@Param("title") String title, @Param("value") Double value);
}
