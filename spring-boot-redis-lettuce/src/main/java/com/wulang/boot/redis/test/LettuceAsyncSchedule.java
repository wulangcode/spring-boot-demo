package com.wulang.boot.redis.test;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 可以去寻找 CompletableFuture 的指南
 *
 * @author wulang
 * @create 2019/12/12/18:00
 */
public class LettuceAsyncSchedule {
    private RedisAsyncCommands<String,String> asyncCommands;

    @Autowired
    public LettuceAsyncSchedule(@Qualifier("asyncCommand") RedisAsyncCommands<String,String> redisCommands) {
        this.asyncCommands = redisCommands;
    }

    //@Scheduled(cron = "0 0 0 */3 * *")
    public void asyncTest() throws ExecutionException, InterruptedException {
        RedisFuture<String> result = asyncCommands.get("key");
        /**
         * 列表是保留插入顺序的字符串列表。从任一端插入或检索值：
         *
         * 在此示例中，nextTask等于“ firstTask ”。Lpush将值推送到列表的头部，然后rpop从列表的末尾弹出值。
         */
        asyncCommands.lpush("tasks", "firstTask");
        asyncCommands.lpush("tasks", "secondTask");
        RedisFuture<String> redisFuture = asyncCommands.rpop("tasks");

        String nextTask = redisFuture.get();

        /**
         * 我们也可以从另一端弹出元素：
         *
         * 我们通过删除带del的列表来启动第二个示例。
         * 然后我们再次插入相同的值，但是我们使用lpop来弹出列表头部的值，因此nextTask2包含“ secondTask ”文本。
         */
        asyncCommands.del("tasks");
        asyncCommands.lpush("tasks", "firstTask");
        asyncCommands.lpush("tasks", "secondTask");
        redisFuture = asyncCommands.lpop("tasks");

        String nextTask2 = redisFuture.get();

        /**
         * Redis集是与Java 集类似的无序字符串集合 ; 没有重复的元素：
         *
         * 当我们将Redis设置为Set时，大小为2，因为忽略了重复的“cat”。
         * 当我们用sismember查询Redis是否存在“dog”时，响应是真的。
         */
        asyncCommands.sadd("pets", "dog");
        asyncCommands.sadd("pets", "cat");
        asyncCommands.sadd("pets", "cat");

        RedisFuture<Set<String>> pets = asyncCommands.smembers("nicknames");
        RedisFuture<Boolean> exists = asyncCommands.sismember("pets", "dog");

        /**
         * Redis Hashes是具有String字段和值的记录。每条记录在主索引中也有一个键：
         *
         * 我们使用hset向哈希添加字段，传入哈希名称，字段名称和值。
         *
         * 然后，我们使用hget，记录名称和字段检索单个值。最后，我们使用hgetall将整个记录作为哈希获取。
         */
        asyncCommands.hset("recordName", "FirstName", "John");
        asyncCommands.hset("recordName", "LastName", "Smith");

        RedisFuture<String> lastName = asyncCommands.hget("recordName", "LastName");
        RedisFuture<Map<String, String>> record = asyncCommands.hgetall("recordName");

        /**
         *排序集包含值和排序，按顺序排序。等级是64位浮点值。
         *
         * 项目添加了排名，并在一个范围内检索：
         *
         * zadd的第二个参数是排名。我们按排名检索范围，zrange为升序，zrevrange为降序。
         *
         * 我们添加了等级为4的“ 零 ”，因此它将出现在valuesForward的末尾和valuesReverse的开头。
         */
        asyncCommands.zadd("sortedset", 1, "one");
        asyncCommands.zadd("sortedset", 4, "zero");
        asyncCommands.zadd("sortedset", 2, "two");

        RedisFuture<List<String>> valuesForward = asyncCommands.zrange("sortedset", 0, 3);
        RedisFuture<List<String>> valuesReverse = asyncCommands.zrevrange("sortedset", 0, 3);

        /**
         * 事务允许在单个原子步骤中执行一组命令。保证这些命令按顺序执行。在事务完成之前，不会执行来自其他用户的命令。
         *
         * 要么执行所有命令，要么都不执行。如果其中一个失败，Redis将不执行回滚。一旦执行exec（）被调用时，所有命令都以指定的顺序执行。
         *
         * 我们来看一个例子：
         * 对multi的调用启动了事务。启动事务时，在调用exec（）之前不会执行后续命令。
         *
         * 在同步模式下，命令返回null。在异步模式下，命令返回RedisFuture。Exec返回一个TransactionResult，其中包含一个响应列表。
         *
         * 由于RedisFutures也会收到结果，因此异步API客户端会在两个地方收到事务结果。
         */
        asyncCommands.multi();

        RedisFuture<String> result1 = asyncCommands.set("key1", "value1");
        RedisFuture<String> result2 = asyncCommands.set("key2", "value2");
        RedisFuture<String> result3 = asyncCommands.set("key3", "value3");

        RedisFuture<TransactionResult> execResult = asyncCommands.exec();

        TransactionResult transactionResult = execResult.get();

        String firstResult = transactionResult.get(0);
        String secondResult = transactionResult.get(0);
        String thirdResult = transactionResult.get(0);

        /**
         * Batching
         * 在正常情况下，Lettuce会在API客户端调用命令后立即执行命令。
         *
         * 这是大多数普通应用程序所需要的，特别是如果它们依赖于串行接收命令结果。
         *
         * 但是，如果应用程序不立即需要结果或批量上载大量数据，则此行为效率不高。
         *
         * 异步应用程序可以覆盖此行为：
         * 将setAutoFlushCommands设置为false时，应用程序必须手动调用flushCommands。
         * 在此示例中，我们将多个set命令排队，然后刷新通道。AwaitAll等待所有RedisFutures完成。
         *
         * 此状态基于每个连接设置，并影响使用该连接的所有线程。此功能不适用于同步命令。
         */
        asyncCommands.setAutoFlushCommands(false);

        List<RedisFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            futures.add(asyncCommands.set("key-" + i, "value-" + i));
        }
        asyncCommands.flushCommands();

        boolean resultBatching = LettuceFutures.awaitAll(5, TimeUnit.SECONDS,
                futures.toArray(new RedisFuture[0]));


    }
}
