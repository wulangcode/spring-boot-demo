package com.wulang.pg.mapper;

import com.wulang.pg.pojo.T1;

import java.util.List;

/**
 * @author wulang
 * @date 2022-09-03 19:28
 **/
public interface PgMapper {
    /**
     * 查询
     */
    List<T1> list();

    /**
     * 插入
     */
    void insTest(T1 t1Do);
}
