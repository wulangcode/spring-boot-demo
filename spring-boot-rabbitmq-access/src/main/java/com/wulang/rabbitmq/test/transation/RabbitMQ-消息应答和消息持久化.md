#**1.消息应答**

###Ack (Message Acknowledgement) 消息应答默认打开 false
- autoAck = true：(自动确认模式) 一旦rabbitMQ将消息分发给消费者，就会从内存中删除，这种情况下，如果消费者未处理完消息就异常结束，则会丢失正在处理的消息
- autoAck = false：将 autoAck 设置为 false(手动确认模式) 如果一个消费者挂掉，就会交付给其他消费者，rabbitMQ支持消息应答，消费者发送一个消息应答告诉rabbitMQ当前消息已经处理完成，rabbitMQ得到消息后，再去删除内存中的消息,这种情况下如果rabbitMQ挂了,消息仍会丢失，rabbitMQ支持持久化操作，将队列持久化操作可解决问题

###消息持久化
  ```
    //声明队列
    //持久化参数 durable改为true即可支持持久化
    boolean durable = false;
    channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
   ```
   >***ps:如果队列已经声明成功,则改变durable的值重新声明队列是不允许的，会抛出异常，rabbitMQ不允许重新定义同一个已经存在但是参数不同的队列***