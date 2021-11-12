package com.wulang.bitmap.pojo;

import org.roaringbitmap.RoaringBitmap;

/**
 * @author wulang
 * @date 2021/11/12/14:19
 */
public class TestBitMap {
    private Long id;

    private RoaringBitmap roaringBitmap;

    public TestBitMap(Long id, RoaringBitmap roaringBitmap) {
        this.id = id;
        this.roaringBitmap = roaringBitmap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoaringBitmap getRoaringBitmap() {
        return roaringBitmap;
    }

    public void setRoaringBitmap(RoaringBitmap roaringBitmap) {
        this.roaringBitmap = roaringBitmap;
    }
}
