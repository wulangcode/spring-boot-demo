package com.wulang.batis.admin.config;

/**
 * 若有多个数据源，根据自己需求增加类型
 *
 *
 * @author wulang
 * @create 2019/12/10/17:22
 */
public enum  DataSourceEnum {
//    COLLECTION("collection"),MONITOR("monitor");
    DB1("db1"),DB2("db2");

    private String value;

    DataSourceEnum(String value){this.value=value;}

    public String getValue() {
        return value;
    }
}
