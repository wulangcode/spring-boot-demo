package com.wulang.spider.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 京东返回的对象
 *
 * @author wulang
 * @create 2020/7/13/6:59
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    /**
     * 图片
     */
    private String img;
    /**
     * 价格
     */
    private String price;
    /**
     * 标题
     */
    String title;
}
