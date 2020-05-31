package com.wulang.affair.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * fail-fast，就是不给fallback降级逻辑，
 * HystrixCommand.run()，直接报错，直接会把这个报错抛出来，给你的tomcat调用线程
 * <p>
 * fail-silent，给一个fallback降级逻辑，如果HystrixCommand.run()，报错了，
 * 会走fallback降级，直接返回一个空值，HystrixCommand，就给一个null
 *
 * @author wulang
 * @create 2020/5/24/16:09
 */
public class FailureModeCommand extends HystrixCommand<Boolean> {

    private boolean failure;

    public FailureModeCommand(boolean failure) {
        super(HystrixCommandGroupKey.Factory.asKey("FailureModeGroup"));
        this.failure = failure;
    }

    @Override
    protected Boolean run() throws Exception {
        if (failure) {
            throw new Exception();
        }
        return true;
    }

    @Override
    protected Boolean getFallback() {
        return false;
    }

}
