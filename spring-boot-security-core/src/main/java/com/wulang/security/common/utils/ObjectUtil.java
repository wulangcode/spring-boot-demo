package com.wulang.security.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * @author wulang
 * @create 2019/12/11 0011/11:02
 */
public class ObjectUtil {
    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }

        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
