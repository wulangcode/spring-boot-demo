package com.wulang.redis.controller;

import com.wulang.redis.common.CallWithJedis;
import com.wulang.redis.common.Holder;
import com.wulang.redis.common.RedisPool;
import redis.clients.jedis.Jedis;

/**
 * 我们通过一个特殊的自定义的 RedisPool 对象将 JedisPool 对象隐藏起来，
 * 避免程序员直接使用它的 getResource 方法而忘记了归还。程序员使用 RedisPool
 * 对象时需要提供一个回调类来才能使用 Jedis 对象。
 *
 * @author wulang
 * @create 2019/11/26/16:15
 */
public class JedisTest {
    public static void main(String[] args) {
        RedisPool redis = new RedisPool();
        redis.execute(new CallWithJedis() {
            @Override
            public void call(Jedis jedis) {
                // do something with jedis
            }
        });
        /**
         * 但是每次访问 Redis 都需要写一个回调类，真是特别繁琐，
         * 代码也显得非常臃肿。幸好 Java8 带来了 Lambda 表达式，
         * 我们可以使用 Lambda 表达式简化上面的代码。
         */
        redis.execute(jedis -> {
            // do something with jedis
        });
        /**
         * 这样看起来就简洁优雅多了。但是还有个问题，Java 不允许在闭包里修改闭包外面的变量。
         *
         * 这里需要定义一个 Holder 类型，将需要修改的变量包装起来。
         */
        Holder<Long> countHolder = new Holder<>();
        redis.execute(jedis -> {
            long count = jedis.zcard("codehole");
            countHolder.value(count);
        });
        System.out.println(countHolder.value());
    }
}
