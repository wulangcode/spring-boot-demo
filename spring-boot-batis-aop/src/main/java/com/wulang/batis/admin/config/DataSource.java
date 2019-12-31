package com.wulang.batis.admin.config;

import java.lang.annotation.*;

/**
 * @author wulang
 * @create 2019/12/10/17:23
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DataSourceEnum value() default DataSourceEnum.DB1;
}
