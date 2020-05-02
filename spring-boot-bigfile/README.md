# spring-boot-bigfile

## 需求：怎样快速读取大文件？

## 解决思路一：
* 多线程
* 内存的零拷贝技术

### BigFileReader.java
* 解决思路：
    * 大文件进行分割切片（此处切片只是打上标记）
    * 对于分割后的slice，使用线程池进行处理
    * 收集处理结果统计
```java
    public void start() {
         long everySize = this.fileLength / this.threadPoolSize;
         try {
             calculateStartEnd(0, everySize);
         } catch (IOException e) {
             e.printStackTrace();
             return;
         }

         final long startTime = System.currentTimeMillis();
         cyclicBarrier = new CyclicBarrier(startEndPairs.size(), () -> {
             System.out.println("use time: " + (System.currentTimeMillis() - startTime));
             System.out.println("all line: " + counter.get());
             shutdown();
         });
         for (StartEndPair pair : startEndPairs) {
             System.out.println("分配分片：" + pair);
             this.executorService.execute(new SliceReaderTask(pair));
         }
     }
```

```java
        public void run() {
            try {
                MappedByteBuffer mapBuffer = rAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, start, this.sliceSize);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                for (int offset = 0; offset < sliceSize; offset += bufferSize) {
                    int readLength;
                    if (offset + bufferSize <= sliceSize) {
                        readLength = bufferSize;
                    } else {
                        readLength = (int) (sliceSize - offset);
                    }
                    mapBuffer.get(readBuff, 0, readLength);
                    for (int i = 0; i < readLength; i++) {
                        byte tmp = readBuff[i];
                        //碰到换行符
                        if (tmp == '\n' || tmp == '\r') {
                            handle(bos.toByteArray());
                            bos.reset();
                        } else {
                            bos.write(tmp);
                        }
                    }
                }
                if (bos.size() > 0) {
                    handle(bos.toByteArray());
                }
                cyclicBarrier.await();//测试性能用
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
```

## 注意事项
1. 对于txt文件的读入，为了充分利用多线程读取，就需要把文件划分成多个区域，供每个线程读取。那么就需要有一个算法来计算出每个线程读取的开始位置和结束位置。那么首先根据配置的线程数和文件的总长度计，算出每个线程平均分配的读取长度。但是有一点，由于文件是纯文本文件，必须按行来处理，如果分割点在某一行中间，那么这一行数据就会被分成两部分，分别由两个线程同时处理，这种情况是不能出现的。**所以各个区域的结束点上的字符必须是换行符**。第一个区域的开始位置是0，结束位置首先设为（文件长度/线程数），如果结束点位置不是换行符，就只能加1，直到是换行符位置。第一个区域的结束位置有了，自然我们就能求出第二个区域的开始位置了，同理根据上边算法求出第二个区域的结束位置，然后依次类推第三个、第四个......
2. 主要不要使单个切片过大，不然会报错(单个切片size不要大于Integer.MAX_VALUE)
3. 如果业务同时操作这个表数据，很容易造成死锁，注意生产中的使用。
4. 注意读取的线程数，多个线程读取到内存，也会造成服务器内存溢出。

## 解决思路二：
* 使用多线程设计模式中的Producer-Consumer模式
1. 首先，专门开辟一个线程 (一个够用,多了无益,以下称之为Reader线程),该线程负责读取Excel文件中的记录。比如使用第三方工具POI ,此时读取到的Excel记录是一个Java对象。 该线程每次读取到记录都将其存入队列(如ArrayBlockingQueue)。它仅负责读取记录并将其存入队列，其它的事情它不做。

2. 其次,再设置若干个线程(如果一个够用,就一个。 数量最好不要超过系统的CPU个数,以下称为Processor线程) , 这些线程负责从上述队列中取出记录(对象),然后对记录中的数据进行校验,写入数据库。

3. 最后，Reader线程读取完所以记录之后,要”通知”Processor线程 :等你处理完所有记录后,你可以停止了。这点,可以借助多线程设计模式中的Two- phase Termination模式来实现。其主要思想是为要停止的线程(目标线程,这里就是Processor线程)设置一个停止标志 ,并设置一个表示目标线程的工作任务数(这里值有多少条记录需要它处理)的变量。当目标线程侦测到其待处理的任务数为0 ,且线程停止标志已置为true的情况下,该线程就可以停止了。


## 解决思路三：
1. 缓存行对齐的概念：引用disruptor框架

## 参考
1. disruptor框架说明文档：https://tech.meituan.com/2016/11/18/disruptor.html
