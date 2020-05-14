package com.wulang.affair.service;

import com.wulang.affair.request.Request;

/**
 * 请求异步执行的service
 *
 * @author wulang
 * @create 2020/5/13/21:01
 */
public interface RequestAsyncProcessService {

    void process(Request request);

}
