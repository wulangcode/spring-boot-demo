package com.wulang.bitmap;

import com.alibaba.fastjson.JSON;
import com.wulang.pg.Application;
import com.wulang.pg.mapper.PgMapper;
import com.wulang.pg.pojo.T1;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roaringbitmap.RoaringBitmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author wulang
 * @date 2022-09-03 19:44
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BitMapTest {

    @Autowired
    private PgMapper pgMapper;

    @Test
    public void list() {
        List<T1> list = pgMapper.list();

        System.out.println(list.size());
        list.forEach(e -> {
            System.out.println("id:" + e.getId() + ",bitmap:" + JSON.toJSONString(e.getBitmap().toArray()));
        });
    }

    @Test
    public void insterBitMap() {
        T1 t1 = new T1();
        t1.setId(666);
        RoaringBitmap roaringBitmap = RoaringBitmap.bitmapOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        t1.setBitmap(roaringBitmap);
        pgMapper.insTest(t1);

        List<T1> list = pgMapper.list();

        System.out.println(list.size());
        list.forEach(e -> {
            if (Objects.equals(e.getId(), 666)) {
                System.out.println("id:" + e.getId() + ",bitmap:" + JSON.toJSONString(e.getBitmap().toArray()));
            }
        });
    }
}
