package com.wulang.rename.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wulang
 * @create 2021/1/29/11:24
 */
public class TableNameFactory {
    private TableNameFactory() {

    }

    private static final Map<String, String> MAP = new HashMap<>();

    static {
        MAP.put("t_user", "t2_user");
        MAP.put("t1_user", "t3_user");
    }

    public static String getValue(String key) {
        return MAP.get(key);
    }

    public static Map<String, String> getMap() {
        return MAP;
    }
}
