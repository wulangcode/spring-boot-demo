package com.wulang.pg.pojo;

import lombok.Data;
import org.roaringbitmap.RoaringBitmap;

/**
 * @author: wulang
 * @date: 2022-09-03 19:30
 **/
@Data
public class T1 {
    private Integer id;

    private RoaringBitmap bitmap;
}
