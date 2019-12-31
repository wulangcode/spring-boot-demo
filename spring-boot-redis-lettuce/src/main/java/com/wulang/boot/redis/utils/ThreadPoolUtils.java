package com.wulang.boot.redis.utils;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolUtils {
    private static ThreadPoolExecutor threadPoolExecutor;
    static{
        threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),5,
                20, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new DefaultThreadFactory("Pj"));
    }

    public static ThreadPoolExecutor getThreadPool(){
        return threadPoolExecutor;
    }
    public static void closeThreadPool(){
        threadPoolExecutor.shutdown();
    }
}
