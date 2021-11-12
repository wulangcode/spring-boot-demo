package com.wulang.bitmap;

import com.wulang.bitmap.mapper.CKMapper;
import com.wulang.bitmap.pojo.TestBitMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roaringbitmap.RoaringBitmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @author wulang
 * @date 2021/11/9/16:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BitMapTest {

    @Autowired
    private CKMapper ckMapper;

    @Test
    public void insterCkBitMap() {
        long parseLong = Long.parseLong(RandomStringUtils.randomNumeric(11));
        System.out.println("当前上下文ID为:" + parseLong);
        RoaringBitmap roaringBitmap = RoaringBitmap.bitmapOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ckMapper.insertBitMap(new TestBitMap(parseLong, roaringBitmap));

        TestBitMap testBitMap = ckMapper.findById(parseLong);
        System.out.println(testBitMap == null);
        System.out.println(Arrays.toString(testBitMap.getRoaringBitmap().toArray()));
    }
}
