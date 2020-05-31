package com.wulang.affair.local;

import java.util.HashMap;
import java.util.Map;

/**
 * 品牌缓存
 *
 * @author wulang
 * @create 2020/5/23/13:30
 */
public class BrandCache {

    private static Map<Long, String> brandMap = new HashMap<Long, String>();
    private static Map<Long, Long> productBrandMap = new HashMap<Long, Long>();

    static {
        brandMap.put(1L, "iphone");
        productBrandMap.put(-1L, 1L);
    }

    public static String getBrandName(Long brandId) {
        return brandMap.get(brandId);
    }

    public static Long getBrandId(Long productId) {
        return productBrandMap.get(productId);
    }

}
