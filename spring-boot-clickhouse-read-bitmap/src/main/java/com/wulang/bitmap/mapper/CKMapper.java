package com.wulang.bitmap.mapper;

import com.wulang.bitmap.pojo.TestBitMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wulang
 * @date 2021/11/9/16:48
 */
@Mapper
public interface CKMapper {

    void insertBitMap(TestBitMap testBitMap);

    TestBitMap findById(@Param("id") Long id);
}
