package com.wulang.affair.request;

/**
 * 请求接口
 *
 * @author wulang
 * @create 2020/5/13/7:38
 */
public interface Request {
    /**
     * 处理请求的方法
     */
    void process();

    /**
     * 获取一个商品ID
     *
     * @return
     */
    Integer getProductId();

    /**
     * 是否强制刷新缓存
     *
     * @return
     */
    boolean isForceRefresh();
}
