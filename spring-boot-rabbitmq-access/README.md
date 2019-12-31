# **对RabbitMQ的封装，有几个目标**
1. **提供send接口** 
2. **提供consume接口** 
3. **保证消息的事务性处理**
>所谓事务性处理，是指对一个消息的处理必须严格可控，必须满足原子性，只有两种可能的处理结果： \
>(1) 处理成功，从队列中删除消息   \
>(2) 处理失败(网络问题，程序问题，服务挂了)，将消息重新放回队列 为了做到这点，我们使用rabbitmq的手动ack模式，这个后面细说。
---
1. **send接口**    
    ```
      public interface MessageSender {    
          DetailRes send(Object message);
      }
    ```
send接口相对简单，我们使用spring的RabbitTemplate来实现，代码如下：    
 ```
     //1 构造template, exchange, routingkey等
     //2 设置message序列化方法
     //3 设置发送确认
     //4 构造sender方法
     public MessageSender buildMessageSender(final String exchange, final String routingKey, final String queue) throws IOException, TimeoutException {
         Connection connection = connectionFactory.createConnection();
         //1
         buildQueue(exchange, routingKey, queue, connection);
         final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
     
         rabbitTemplate.setMandatory(true);
         rabbitTemplate.setExchange(exchange);
         rabbitTemplate.setRoutingKey(routingKey);
         //2
         rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
     
         //3
         rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
             @Override
             public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                 if (!ack) {
                     log.info("send message failed: " + cause); //+ correlationData.toString());
                     throw new RuntimeException("send error " + cause);
                 }
             }
         });
     
         //4
         return new MessageSender() {
             @Override
             public DetailRes send(Object message) {
                 try {
                     rabbitTemplate.convertAndSend(message);
                 } catch (RuntimeException e) {
                     e.printStackTrace();
                     log.info("send failed " + e);
     
                     try {
                         //retry
                         rabbitTemplate.convertAndSend(message);
                     } catch (RuntimeException error) {
                         error.printStackTrace();
                         log.info("send failed again " + error);
     
                         return new DetailRes(false, error.toString());
                     }
                 }
     
                 return new DetailRes(true, "");
             }
         };
     }
 ```
     
2. **consume接口**
    ```
      public interface MessageConsumer {    
          DetailRes consume();
      }
    ```
在consume接口中，会调用用户自己的MessageProcess，接口定义如下：

       
          public interface MessageProcess<T> {    
              DetailRes process(T message);
          }
           
 consume的实现相对来说复杂一点，代码如下：
 ```
 //1 创建连接和channel
 //2 设置message序列化方法
 //3 构造consumer
 public <T> MessageConsumer buildMessageConsumer(String exchange, String routingKey,
                                                 final String queue, final MessageProcess<T> messageProcess) throws IOException {
     final Connection connection = connectionFactory.createConnection();
 
     //1
     buildQueue(exchange, routingKey, queue, connection);
 
     //2
     final MessagePropertiesConverter messagePropertiesConverter = new DefaultMessagePropertiesConverter();
     final MessageConverter messageConverter = new Jackson2JsonMessageConverter();
 
     //3
     return new MessageConsumer() {
         QueueingConsumer consumer;
 
         {
             consumer = buildQueueConsumer(connection, queue);
         }
 
         @Override
         //1 通过delivery获取原始数据
         //2 将原始数据转换为特定类型的包
         //3 处理数据
         //4 手动发送ack确认
         public DetailRes consume() {
             QueueingConsumer.Delivery delivery = null;
             Channel channel = consumer.getChannel();
 
             try {
                 //1
                 delivery = consumer.nextDelivery();
                 Message message = new Message(delivery.getBody(),
                         messagePropertiesConverter.toMessageProperties(delivery.getProperties(), delivery.getEnvelope(), "UTF-8"));
 
                 //2
                 @SuppressWarnings("unchecked")
                 T messageBean = (T) messageConverter.fromMessage(message);
 
                 //3
                 DetailRes detailRes = messageProcess.process(messageBean);
 
                 //4
                 if (detailRes.isSuccess()) {
                     channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                 } else {
                     log.info("send message failed: " + detailRes.getErrMsg());
                     channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                 }
 
                 return detailRes;
             } catch (InterruptedException e) {
                 e.printStackTrace();
                 return new DetailRes(false, "interrupted exception " + e.toString());
             } catch (IOException e) {
                 e.printStackTrace();
                 retry(delivery, channel);
                 log.info("io exception : " + e);
 
                 return new DetailRes(false, "io exception " + e.toString());
             } catch (ShutdownSignalException e) {
                 e.printStackTrace();
 
                 try {
                     channel.close();
                 } catch (IOException io) {
                     io.printStackTrace();
                 } catch (TimeoutException timeout) {
                     timeout.printStackTrace();
                 }
 
                 consumer = buildQueueConsumer(connection, queue);
 
                 return new DetailRes(false, "shutdown exception " + e.toString());
             } catch (Exception e) {
                 e.printStackTrace();
                 log.info("exception : " + e);
                 retry(delivery, channel);
 
                 return new DetailRes(false, "exception " + e.toString());
             }
         }
     };
 }
 ```
 3. **保证消息的事务性处理**  \
 rabbitmq默认的处理方式为auto ack，这意味着当你从消息队列取出一个消息时，ack自动发送，mq就会将消息删除。而为了保证消息的正确处理，我们需要将消息处理修改为手动确认的方式。  \
 (1) sender的手工确认模式 首先将ConnectionFactory的模式设置为publisherConfirms，如下
     ```
       connectionFactory.setPublisherConfirms(true);
     ```
之后设置rabbitTemplate的confirmCallback，如下：
```
rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
   @Override
   public void confirm(CorrelationData correlationData, boolean ack, String cause) {
      if (!ack) {
          log.info("send message failed: " + cause); //+ correlationData.toString());
          throw new RuntimeException("send error " + cause);
      }
   }
});
```
(2) consume的手工确认模式 首先在queue创建中指定模式
```
channel.exchangeDeclare(exchange, "direct", true, false, null);
/**
* Declare a queue
 * @see com.rabbitmq.client.AMQP.Queue.Declare
* @see com.rabbitmq.client.AMQP.Queue.DeclareOk
* @param queue the name of the queue
* @param durable true if we are declaring a durable queue (the queue will survive a server restart)
* @param exclusive true if we are declaring an exclusive queue (restricted to this connection)
* @param autoDelete true if we are declaring an autodelete queue (server will delete it when no longer in use)
* @param arguments other properties (construction arguments) for the queue
* @return a declaration-confirm method to indicate the queue was successfully declared
* @throws java.io.IOException if an error is encountered
*/
channel.queueDeclare(queue, true, false, false, null);
```
只有在消息处理成功后发送ack确认，或失败后发送nack使信息重新投递
```
if (detailRes.isSuccess()) {
    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
} else {
    log.info("send message failed: " + detailRes.getErrMsg());
    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
}
```
4. **自动重连机制**
为了保证rabbitmq的高可用性，我们使用rabbitmq Cluster模式，并配合haproxy。这样，在一台机器down掉时或者网络发生抖动时，就会发生当前连接失败的情况，如果不对这种情况做处理，就会造成当前的服务不可用。 在spring-rabbitmq中，已实现了connection的自动重连，但是connection重连后，channel的状态并不正确。因此我们需要自己捕捉ShutdownSignalException异常，并重新生成channel。如下： 
```
catch (ShutdownSignalException e) {
    e.printStackTrace();
    channel.close();
    //recreate channel
    consumer = buildQueueConsumer(connection, queue);
}
```

5. **consumer线程池**  
在对消息处理的过程中，我们期望多线程并行执行来增加效率，因此对consumer做了一个线程池的封装。 线程池通过builder模式构造，需要准备如下参数：
```
//线程数量
int threadCount;
//处理间隔(每个线程处理完成后休息的时间)
long intervalMils;
//exchange及queue信息
String exchange;
String routingKey;
String queue;
//用户自定义处理接口
MessageProcess<T> messageProcess;
```
核心循环也较为简单，代码如下：
```
public void run() {
    while (!stop) {
        try {
            //2
            DetailRes detailRes = messageConsumer.consume();

            if (infoHolder.intervalMils > 0) {
                try {
                    Thread.sleep(infoHolder.intervalMils);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.info("interrupt " + e);
                }
            }

            if (!detailRes.isSuccess()) {
                log.info("run error " + detailRes.getErrMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("run exception " + e);
        }
    }
}
```

**[测试用例](https://github.com/sanliangitch/spring-boot-demo/blob/master-wulang/spring-boot-rabbitmq-access/src/main/java/com/wulang/rabbitmq/example/ConsumerExample.java)**

参考项目：[rabbitmq手动确认模式java封装](https://github.com/littlersmall/rabbitmq-access)
