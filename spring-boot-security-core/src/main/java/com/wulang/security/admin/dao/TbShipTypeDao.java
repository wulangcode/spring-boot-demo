package com.wulang.security.admin.dao;

import com.wulang.security.admin.entity.monitor.TbShipType;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 船舶类型(TbShipType)表数据库访问层
 *
 * @author makejava
 * @since 2019-11-29 10:24:10
 */
public interface TbShipTypeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param shipTypeId 主键
     * @return 实例对象
     */
    TbShipType queryById(Integer shipTypeId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TbShipType> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbShipType 实例对象
     * @return 对象列表
     */
    List<TbShipType> queryAll(TbShipType tbShipType);

    /**
     * 新增数据
     *
     * @param tbShipType 实例对象
     * @return 影响行数
     */
    int insert(TbShipType tbShipType);

    /**
     * 修改数据
     *
     * @param tbShipType 实例对象
     * @return 影响行数
     */
    int update(TbShipType tbShipType);

    /**
     * 通过主键删除数据
     *
     * @param shipTypeId 主键
     * @return 影响行数
     */
    int deleteById(Integer shipTypeId);

}