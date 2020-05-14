package com.wulang.affair.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wulang.affair.request.Request;
import com.wulang.affair.request.RequestQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 请求处理线程池：单例
 *
 * @author wulang
 * @create 2020/5/13/7:39
 */
public class RequestProcessorThreadPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestProcessorThreadPool.class);

    /**
     * 线程池
     */
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
        .setNameFormat("Rx-MSG-%d").build();
    private ExecutorService threadPool = new ThreadPoolExecutor(10, 10,
        3, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1024), namedThreadFactory,
        new ThreadPoolExecutor.AbortPolicy());

    public RequestProcessorThreadPool() {
        RequestQueue requestQueue = RequestQueue.getInstance();
        /**
         * 初始化时就将线程池填满
         */
        for (int i = 0; i < 10; i++) {
            LOGGER.info("===========日志===========: 初始化线程，" + Thread.currentThread().getName());
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(100);
            requestQueue.addQueue(queue);
            threadPool.submit(new RequestProcessorThread(queue));
        }
    }

    /**
     * 单例有很多种方式去实现：我采取绝对线程安全的一种方式
     * <p>
     * 静态内部类的方式，去初始化单例
     */
    private static class Singleton {

        private static RequestProcessorThreadPool instance;

        static {
            instance = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance() {
            return instance;
        }

    }

    /**
     * jvm的机制去保证多线程并发安全
     * <p>
     * 内部类的初始化，一定只会发生一次，不管多少个线程并发去初始化
     *
     * @return
     */
    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 初始化的便捷方法
     */
    public static void init() {
        getInstance();
    }

}
