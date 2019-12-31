###RabbitMQ-消息确认机制(事务+confirm)

- 在rabbitMQ中我们可通过持久化数据，解决rabbitMQ因为服务器异常造成的数据丢失问题:生产者将消息发送出去之后，消息是否到底rabbitMQ服务器?默认情况下不可得知的
     
 - 有两种方式可以获得状态
    >AMQP协议:AMQP实现了事务机制
    
    >Confirm模式
    
 #### 事务机制
 ```
 TxSelect TxCommit TxRollBack
 
 TxSelect : 用于将当前channel设置成transation模式
 TxCommit : 用于提交事务
 TxRollBack : 用于回滚事务
  ```
  
  #### Confirm模式
  ```
   生产者将信道设置成Confirm模式，一旦信道进入confirm模式，所有在该信道上面发布的消息都会被指派一个唯一的ID(从1开始)，
   一旦消息被投递到所有匹配的队列之后，broker就会发送一个确认给生产者(包含消息的唯一ID)，这就使得生产者知道消息已经正确到达目的队列了，
   如果消息和队列是可持久化的，那么确认消息会将消息写入磁盘之后发出，broker回传给生产者的确认消息中deliver-tag域包含了确认消息的序列号，
   此外broker也可以设置basic.ack的multiple域，标识到这个序列号之前的所有消息都已经得到了处理
   ```
   >Confirm模式最大的好处是在于是异步处理
   >>开启confrim模式
   ```
   channel.confirmSelect()
   ```
   
   编程模式:
   1. 普通 发送一条调用waitForConfirms()
   2. 批量 发送一批调用waitForConfirms()
   3. 异步confirm模式:提供一个回调
   
   >ps:tx模式效率比较差 建议使用后两种