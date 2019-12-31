package com.wulang.security.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wulang.security.serialPort.model.Com;
import org.apache.ibatis.annotations.Mapper;

/**
 * 船舶发动机数据(TbShipDeviceData)表数据库访问层
 *
 * @author makejava
 * @since 2019-11-29 10:52:54
 */
@Mapper
public interface CollectionDeviceDataDao extends BaseMapper<Com> {


}