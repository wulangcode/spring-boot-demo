### 1、客户端ASK重定向流程

    Redis集群支持在线迁移槽（slot）和数据来完成水平伸缩，当slot对应的数据从源节点到目标节点迁移过程中，客户端需要做到智能识别，保证键
    命令可正常执行。例如当一个slot数据从源节点迁移到目标节点时，期间可能出现一部分数据在源节点，而另一部分在目标节点。
    当出现上述情况时，客户端键命令执行流程将发生变化，如下所示：
        1）客户端根据本地slots缓存发送命令到源节点，如果存在键对象则直接执行并返回结果给客户端。
        2）如果键对象不存在，则可能存在于目标节点，这时源节点会回复ASK重定向异常。格式如下：（error）ASK{slot}{targetIP}：{targetPort}。
        3）客户端从ASK重定向异常提取出目标节点信息，发送asking命令到目标节点打开客户端连接标识，再执行键命令。如果存在则执行，不存在则返
        回不存在信息。
    ASK与MOVED虽然都是对客户端的重定向控制，但是有着本质区别。ASK重定向说明集群正在进行slot数据迁移，客户端无法知道什么时候迁移
    完成，因此只能是临时性的重定向，客户端不会更新slots缓存。但是MOVED重定向说明键对应的槽已经明确指定到新的节点，因此需要更新
    slots缓存。

### 2、节点内部处理

    * 为了支持ASK重定向，源节点和目标节点在内部的clusterState结构中维护当前正在迁移的槽信息，用于识别槽迁移情况，结构如下：
        ```
              typedef struct clusterState {
              clusterNode *myself; /* 自身节点 /
              clusterNode *slots[CLUSTER_SLOTS]; /* 槽和节点映射数组 */
              clusterNode *migrating_slots_to[CLUSTER_SLOTS];/* 正在迁出的槽节点数组 */
              clusterNode *importing_slots_from[CLUSTER_SLOTS];/* 正在迁入的槽节点数组*/
              ...
              } clusterState;
        ```
        
    * 节点每次接收到键命令时，都会根据clusterState内的迁移属性进行命令处理，如下所示：
        ·如果键所在的槽由当前节点负责，但键不存在则查找migrating_slots_to数组查看槽是否正在迁出，如果是返回ASK重定向。
        ·如果客户端发送asking命令打开了CLIENT_ASKING标识，则该客户端下次发送键命令时查找importing_slots_from数组获取clusterNode，如果指向
          自身则执行命令。
        ·需要注意的是，asking命令是一次性命令，每次执行完后客户端标识都会修改回原状态，因此每次客户端接收到ASK重定向后都需要发送asking命令。
        ·批量操作。ASK重定向对单键命令支持得很完善，但是，在开发中我们经常使用批量操作，如mget或pipeline。当槽处于迁移状态时，批量操作会受到影响。
            当在集群环境下使用mget、mset等批量操作时，slot迁移数据期间由于键列表无法保证在同一节点，会导致大量错误。
            Pipeline的代码中，由于Jedis没有开放slot到Jedis的查询，使用了匿名内部类暴露JedisSlotBasedConnectionHandler。通过Jedis获取Pipeline对象
            组合3条get命令一次发送。运行结果如下：
            redis.clients.jedis.exceptions.JedisAskDataException: ASK 4096 127.0.0.1:6379
            redis.clients.jedis.exceptions.JedisAskDataException: ASK 4096 127.0.0.1:6379
            value:5028
            结果分析：
                返回结果并没有直接抛出异常，而是把ASK异常JedisAskDataException包含在结果集中。但是使用Pipeline的批量操作也无法支持由于slot迁移导致
            的键列表跨节点问题。
            得益于Pipeline并没有直接抛出异常，可以借助于JedisAskDataException内返回的目标节点信息，手动重定向请求给目标节点，修改后的程序如下：
            --------------------------------------------------------------
            @Test
            public void pipelineOnAskTestV2() {
            JedisSlotBasedConnectionHandler connectionHandler = new JedisCluster(new Host
            AndPort("127.0.0.1", 6379)) {
            public JedisSlotBasedConnectionHandler getConnectionHandler() {
            return (JedisSlotBasedConnectionHandler) super.connectionHandler;
            }
            }.getConnectionHandler();
            List<String> keys = Arrays.asList("key:test:68253", "key:test:79212", "key:
            test:5028");
            Jedis jedis = connectionHandler.getConnectionFromSlot(JedisClusterCRC16.get
            Slot(keys.get(2)));
            try {
            Pipeline pipelined = jedis.pipelined();
            for (String key : keys) {
            pipelined.get(key);
            }
            List<Object> results = pipelined.syncAndReturnAll();
            for (int i = 0; i < keys.size(); i++) {
            // 键顺序和结果顺序一致
            Object result = results.get(i);
            if (result != null && result instanceof JedisAskDataException) {
            JedisAskDataException askException = (JedisAskDataException) result;
            HostAndPort targetNode = askException.getTargetNode();
            Jedis targetJedis = connectionHandler.getConnectionFromNode(tar
            getNode);
            try {
            // 执行asking
            targetJedis.asking();
            // 获取key并执行
            String key = keys.get(i);
            String targetResult = targetJedis.get(key);
            System.out.println(targetResult);
            } finally {
            targetJedis.close();
            }
            } else {
            System.out.println(result);
            }
            }
            } finally {
            jedis.close();
            }
            }
            
            --------------------------------------------------------------
            根据结果，我们成功获取到了3个键的数据。以上测试能够成功的前提是：
                1）Pipeline严格按照键发送的顺序返回结果，即使出现异常也是如此
                2）理解ASK重定向之后，可以手动发起ASK流程保证Pipeline的结果正确性

            综上所处，使用smart客户端批量操作集群时，需要评估mget/mset、Pipeline等方式在slot迁移场景下的容错性，防止集群迁移造成大量错误和数
            据丢失的情况。



开发提示：
        集群环境下对于使用批量操作的场景，建议优先使用Pipeline方式，在客户端实现对ASK重定向的正确处理，这样既可以受益于批量操作的IO优
        化，又可以兼容slot迁移场景。



      Pipeline说明：
        ·原生批量命令是原子的，Pipeline是非原子的。
        ·原生批量命令是一个命令对应多个key，Pipeline支持多个命令。
        ·原生批量命令是Redis服务端支持实现的，而Pipeline需要服务端和客户端的共同实现。
