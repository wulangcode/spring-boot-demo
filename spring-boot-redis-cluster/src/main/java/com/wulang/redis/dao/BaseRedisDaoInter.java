package com.wulang.redis.dao;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author wulang
 * @create 2019/11/25/17:40
 */
public interface BaseRedisDaoInter<K, V> {

    /**
     * 获取Redis中所有的键的key
     *
     * @return
     */
    Set<K> getAllKeys();

    /**
     * 获取所有的普通key-value
     * @return
     */
    Map<K, V> getAllString();

    /**
     * 获取所有的Set -key-value
     * @return
     */
    Map<K, Set<V>> getAllSet();

    /**
     * 获取所有的List -key-value
     * @return
     */
    Map<K, List<V>> getAllList();

    /**
     * 获取所有的Map -key-value
     * @return
     */
    Map<K, Map<K, V>> getAllMap();

    /**
     * 添加一个list
     *
     * @param key 键
     * @param objectList 值集合
     */
    void addList(K key, List<V> objectList);

    /**
     * 向list中增加值
     *
     * @param key 键
     * @param obj 值
     * @return 返回在list中的下标
     */
    long addList(K key, V obj);

    /**
     * 向list中增加 多个值
     *
     * @param key 键
     * @param obj 多个对象可变数组
     * @return 返回在list中的下标
     */
    long addList(K key, V... obj);

    /**
     * 获取list
     *
     * @param key List的key
     * @param s 开始下标
     * @param e 结束的下标
     * @return 数据集合
     */
    List<V> getList(K key, long s, long e);

    /**
     * 获取完整的list
     *
     * @param key 键
     * @return 数据集合
     */
    List<V> getList(K key);

    /**
     * 获取list集合中元素的个数
     *
     * @param key 键
     * @return 集合的个数
     */
    long getListSize(K key);

    /**
     * 移除list中某值集合
     *  移除list中 count个value为object的值,并且返回移除的数量,
     *  如果count为0,或者大于list中为value为object数量的总和,
     *  那么移除所有value为object的值,并且返回移除数量
     *
     * @param key 键
     * @param object 值
     * @return 返回移除数量
     */
    long removeListValue(K key, V object);

    /**
     * 移除list中某值
     *
     * @param key 键
     * @param object 值
     * @return 返回移除数量
     */
    long removeListValue(K key, V... object);

    /**
     * 批量删除key对应的value
     *
     * @param keys 键
     */
    void remove(final K... keys);

    /**
     * 删除数据
     *  根据key精确匹配删除
     *
     * @param key
     */
    void remove(final K key);

    /**
     * 设置Set的过期时间
     *
     * @param key 键
     * @param time 过期时间长度
     * @return 是否成功
     */
    Boolean setSetExpireTime(String key, Long time);

    /**
     * 获取所有的ZSet正序  -key-value 不获取权重值
     *
     * @return Map集合
     */
    Map<K, Set<V>> getAllZSetReverseRange();

    /**
     * 获取所有的ZSet倒序  -key-value 不获取权重值
     *
     * @return Map集合
     */
    Map<K, Set<V>> getAllZSetRange();

    /**
     * 通过分数删除ZSet中的值
     *
     * @param key 键
     * @param s 最小
     * @param e 最大
     */
    void removeZSetRangeByScore(String key, double s, double e);

    /**
     * 设置ZSet的过期时间
     *
     * @param key 键
     * @param time 过期时间 -单位s
     * @return 是否成功
     */
    Boolean setZSetExpireTime(String key, Long time);

    /**
     * 判断缓存中是否有key对应的value
     *
     * @param key 键
     * @return 是否存在
     */
    boolean exists(final K key);

    /**
     * 读取String缓存可以是对象
     *
     * @param key 键
     * @return 值
     */
    V get(final K key);

    /**
     * 读取String缓存集合 可以是对象
     *
     * @param key 键可变数组
     * @return 值集合
     */
    List<V> get(final K... key);

    /**
     * 读取缓存 可以是对象 根据正则表达式匹配
     *
     * @param regKey 正则键
     * @return 值集合
     */
    List<Object> getByRegular(final K regKey);

    /**
     * 写入缓存 可以是对象
     *
     * @param key 键
     * @param value 值
     */
    void set(final K key, V value);

    /**
     * 写入缓存
     *
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间 -单位s
     */
    void set(final K key, V value, Long expireTime);

    /**
     * 设置一个key的过期时间（单位：秒）
     *
     * @param key 键
     * @param expireTime 过期时间 -单位s
     * @return 是否成功
     */
    boolean setExpireTime(K key, Long expireTime);

    /**
     * 获取key的类型
     *
     * @param key 键
     * @return 键的类型
     */
    DataType getType(K key);

    /**
     * 删除Map中的某个对象
     *
     * @param key Map对应的key
     * @param field Map中该对象的key
     */
    void removeMapField(K key, V... field);

    /**
     * 获取Map对象
     *
     * @param key Map对应的key
     * @return Map数据
     */
    Map<K, V> getMap(K key);

    /**
     * 获取Map对象的长度
     *
     * @param key Map对应的key
     * @return Map的长度
     */
    Long getMapSize(K key);

    /**
     * 获取Map缓存中的某个对象
     *
     * @param key Map对应的key
     * @param field Map中该对象的key
     * @return Map中的Value
     */
    <T> T getMapField(K key, K field);

    /**
     * 判断Map中对应key的key是否存在
     *
     * @param key Map对应的key
     * @param field Map中的key
     * @return 是否存在
     */
    Boolean hasMapKey(K key, K field);

    /**
     * 获取Map对应key的value
     *
     * @param key Map对应的key
     * @return Map中值的集合
     */
    List<V> getMapFieldValue(K key);

    /**
     * 获取Map的key
     *
     * @param key Map对应的key
     * @return Map中key的集合
     */
    Set<V> getMapFieldKey(K key);

    /**
     * 添加Map
     *
     * @param key 键
     * @param map 值
     */
    void addMap(K key, Map<K, V> map);

    /**
     * 向key对应的Map中添加缓存对象
     *
     * @param key cache对象key
     * @param field Map对应的key
     * @param value 值
     */
    void addMap(K key, K field, Object value);

    /**
     * 向key对应的Map中添加缓存对象，带过期时间
     *
     * @param key cache对象key
     * @param field Map对应的key
     * @param time 过期时间-整个Map的过期时间
     * @param value 值
     */
    void addMap(K key, K field, V value, long time);

    /**
     * 向Set中加入对象
     *
     * @param key 对象key
     * @param obj 值
     */
    void addSet(K key, V... obj);

    /**
     * 移除Set中的某些值
     *
     * @param key 对象key
     * @param obj 值
     */
    long removeSetValue(K key, V obj);

    /**
     * 移除Set中的某些值可变数组
     *
     * @param key 对象key
     * @param obj 值
     */
    long removeSetValue(K key, V... obj);

    /**
     * 获取Set的对象数
     *
     * @param key 对象key
     */
    long getSetSize(K key);

    /**
     * 判断Set中是否存在这个值
     *
     * @param key 对象key
     */
    Boolean hasSetValue(K key, V obj);

    /**
     * 获得整个Set
     *
     * @param key 对象key
     */
    Set<V> getSet(K key);

    /**
     * 获得Set并集
     *
     * @param key 键
     * @param otherKey 其他的键
     * @return 获取两个Set类型值的集合
     */
    Set<V> getSetUnion(K key, K otherKey);

    /**
     * 获得Set并集
     *
     * @param key 键
     * @param set 一个Set数据
     * @return 缓存中的Set数据和传入的Set数据的并集
     */
    Set<V> getSetUnion(K key, Set<Object> set);

    /**
     * 获得Set交集
     *
     * @param key 键
     * @param otherKey 其他的键
     * @return 获取两个Set类型值的交接
     */
    Set<V> getSetIntersect(K key, K otherKey);

    /**
     * 获得Set交集
     *
     * @param key 键
     * @param set 一个Set数据
     * @return 缓存中的Set数据和传入的Set数据的交集
     */
    Set<V> getSetIntersect(K key, Set<Object> set);

    /**
     * 模糊移除 支持*号等匹配移除
     *
     * @param blear 模糊参数
     */
    void removeBlear(K blear);

    /**
     * 多个模糊条件移除 支持*号等匹配移除
     *
     * @param blears 模糊参数可变数组
     */
    void removeBlear(K... blears);

    /**
     * 修改key名 如果不存在该key或者没有修改成功返回false
     *
     * @param oldKey 原有的key名称
     * @param newKey 新key名称
     * @return 是否修改成功
     */
    Boolean renameIfAbsent(String oldKey, String newKey);

    /**
     * 根据正则表达式来移除key-value
     *
     * @param blears 正则
     */
    void removeByRegular(String blears);

    /**
     * 根据正则表达式来移除key-value
     *
     * @param blears 正则可变数组
     */
    void removeByRegular(String... blears);

    /**
     * 根据正则表达式来移除 Map中的key-value
     *
     * @param key 键
     * @param blear 正则
     */
    void removeMapFieldByRegular(K key, K blear);

    /**
     * 根据正则表达式来移除 Map中的key-value
     *
     * @param key 键
     * @param blears 正则可变数组
     */
    void removeMapFieldByRegular(K key, K... blears);

    /**
     * 移除key 对应的value
     *
     * @param key 键
     * @param value 值可变数组
     * @return 移除了多少个
     */
    Long removeZSetValue(K key, V... value);

    /**
     * 移除key ZSet
     *
     * @param key 键
     */
    void removeZSet(K key);

    /**
     *删除，键为K的集合，索引start<=index<=end的元素子集
     *
     * @param key 键
     * @param start 开始删除索引
     * @param end 结束删除索引
     */
    void removeZSetRange(K key, Long start, Long end);

    /**
     * 并集 将key对应的集合和key1对应的集合合并到key2中
     *  如果分数相同的值，都会保留
     *  原来key2的值会被覆盖
     *
     * @param key 键
     * @param key1 键1
     * @param key2 键2
     */
    void setZSetUnionAndStore(String key, String key1, String key2);

    /**
     * 获取整个有序集合ZSET，正序
     *
     * @param key 键
     * @return 有序集合
     */
    <T> T getZSetRange(K key);

    /**
     * 获取有序集合ZSET
     *  键为K的集合，索引start<=index<=end的元素子集，正序
     *
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return 有序集合
     */
    <T> T getZSetRange(K key, long start, long end);

    /**
     * 获取整个有序集合ZSET，倒序
     *
     * @param key 键
     * @return 有序集合
     */
    Set<Object> getZSetReverseRange(K key);

    /**
     * 获取有序集合ZSET
     *  键为K的集合，索引start<=index<=end的元素子集，倒序
     *
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return 有序集合
     */
    Set<V> getZSetReverseRange(K key, long start, long end);

    /**
     * 通过分数(权值)获取ZSET集合 正序 -从小到大
     *
     * @param key 键
     * @param start 开始
     * @param end 结束
     * @return 正序集合
     */
    Set<V> getZSetRangeByScore(String key, double start, double end);

    /**
     * 通过分数(权值)获取ZSET集合 倒序 -从大到小
     *
     * @param key 键
     * @param start 开始
     * @param end 结束
     * @return 倒序集合
     */
    Set<V> getZSetReverseRangeByScore(String key, double start, double end);

    /**
     * 键为K的集合，索引start<=index<=end的元素子集
     *  返回泛型接口（包括score和value），正序
     *
     * @param key 键
     * @param start 开始
     * @param end 结束
     * @return 正序集合
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetRangeWithScores(K key, long start, long end);

    /**
     * 键为K的集合，索引start<=index<=end的元素子集
     *  返回泛型接口（包括score和value），倒序
     *
     * @param key 键
     * @param start 开始
     * @param end 结束
     * @return 倒序集合
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetReverseRangeWithScores(K key, long start, long end);

    /**
     * 键为K的集合
     *  返回泛型接口（包括score和value），正序
     *
     * @param key 键
     * @return 正序集合
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetRangeWithScores(K key);
    /**
     * 键为K的集合
     *  返回泛型接口（包括score和value），倒序
     *
     * @param key 键
     * @return 倒序集合
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetReverseRangeWithScores(K key);

    /**
     * 键为K的集合，sMin<=score<=sMax的元素个数
     *
     * @param key 键
     * @param sMin 最小
     * @param sMax 最大
     * @return 个数
     */
    long getZSetCountSize(K key, double sMin, double sMax);

    /**
     * 获取Zset 键为K的集合元素个数
     *
     * @param key 键
     * @return 集合的长度
     */
    long getZSetSize(K key);

    /**
     * 获取键为K的集合，value为obj的元素分数
     *
     * @param key 键
     * @param value 值
     * @return 分数
     */
    double getZSetScore(K key, V value);

    /**
     * 元素分数增加，delta是增量
     *
     * @param key 键
     * @param value 值
     * @param delta 增分
     * @return 分数
     */
    double incrementZSetScore(K key, V value, double delta);

    /**
     * 添加有序集合ZSET
     *  默认按照score升序排列，存储格式K(1)==V(n)，V(1)=S(1)
     *
     * @param key 键
     * @param score 分数
     * @param value 值
     * @return 是否成功
     */
    Boolean addZSet(String key, double score, Object value);

    /**
     * 添加有序集合ZSET
     *
     * @param key 键
     * @param value 值
     * @return 长度
     */
    Long addZSet(K key, TreeSet<V> value);

    /**
     * 添加有序集合ZSET
     *
     * @param key 键
     * @param score 分数
     * @param value 值
     * @return 是否成功
     */
    Boolean addZSet(K key, double[] score, Object[] value);

}
