package com.wulang.affair.dao;

/**
 * redis本身有各种各样的api和功能
 * <p>
 * 可以做出来很多很多非常花哨的功能，但是我们这个课程，是有侧重点的，不是讲解redis基础知识的
 * <p>
 * 是讲解大规模缓存架构
 *
 * @author Administrator
 */
public interface RedisDAO {

    void set(String key, String value);

    String get(String key);

    void delete(String key);
}
