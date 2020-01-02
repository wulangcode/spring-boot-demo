# spring-boot-demo
spring boot demo 是一个用来深度学习并实战 spring boot 的项目，目前总共包含 **`14`** 个集成demo，已经完成 **`14`** 个。

该项目已成功集成batis-aop(`AOP自定义多数据源`)、multi-datasource-mybatis(`使用Mybatis集成多数据源`)、quartz(`定时任务`)、rabbitmq-access(`Rabbitmq手动确认模式`)、rabbitmq-availability(`RabbitMQ消息100%可靠性投递`)、redis(`单机||哨兵||集群||Lettuce`)、RocketMQ-demo(`RocketMQ，实现消息的发送和接收`)、RocketMQ-shop-project(`RocketMQ模拟电商网站购物`)、Spring Security(`实现动态权限的实现||Spring Cloud Security OAuth2`)、websocket(`服务端向客户端推送消息`)。

## 开发计划

网上部分项目demo太过基本，只适合平时学习demo，无法实际用到生产环境中。故开出次项目，希望能朋友们能见到生产过程中的使用。项目中许多工具的封装，均来自于实际生产项目，但由于隐秘保护，不能全部给出，若学习此项目中遇到问题，也可在issue中提出。

## 分支介绍

- master 分支：基于 Spring Boot 版本 `2.0.4.RELEASE`，每个 Module 的 parent 依赖根目录下的 pom.xml，主要用于管理每个 Module 的通用依赖版本，方便大家学习。
- master-wulang 分支：本地开发版本，一些未完成项目的部分代码，会先上传。🙂


## 开发环境

- **JDK 1.8 +**
- **Maven 3.5 +**
- **IntelliJ IDEA ULTIMATE 2018.2 +** (*注意：务必使用 IDEA 开发，同时保证安装 `lombok` 插件*)
- **Mysql 5.7 +** (*尽量保证使用 5.7 版本以上，因为 5.7 版本加了一些新特性，同时不向下兼容。本 demo 里会尽量避免这种不兼容的地方，但还是建议尽量保证 5.7 版本以上*)

## 运行方式

1. `git clone https://github.com/sanliangitch/spring-boot-demo.git`
2. 使用 IDEA 打开 clone 下来的项目
3. 在 IDEA 中 Maven Projects 的面板导入项目根目录下 的 `pom.xml` 文件
4. Maven Projects 找不到的童鞋，可以勾上 IDEA 顶部工具栏的 View -> Tool Buttons ，然后 Maven Projects 的面板就会出现在 IDEA 的右侧
5. 找到各个 Module 的 Application 类就可以运行各个 demo 了
6. **`注意：每个 demo 均有详细的 README 配套，食用 demo 前记得先看看哦~`**(部分未完善，后期会补上，但是项目全部代码已全部上传)
7. **`注意：运行各个 demo 之前，有些是需要事先初始化数据库数据的，亲们别忘记了哦~`**


## 各 Module 介绍

| Module 名称                                                  | Module 介绍                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [spring-boot-batis-aop](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-batis-aop) | spring-boot 使用AOP切面，满足项目中程序运行过程中需要连接多个数据库，可高度定制                                |
| [spring-boot-multi-datasource-mybatis](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-multi-datasource-mybatis) | spring-boot 使用Mybatis集成多数据源，使用 Mybatis-Plus 提供的开源解决方案实现                            |
| [spring-boot-quartz](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-quartz) | spring-boot 整合 quartz，并实现对定时任务的管理，包括新增定时任务，删除定时任务，暂停定时任务，恢复定时任务，修改定时任务启动时间，以及定时任务列表查询，`提供前端页面`                             |
| [spring-boot-rabbitmq-access](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-rabbitmq-access) | spring-boot 整合 RabbitMQ，提供send接口，提供consume接口 ，保证消息的事务性处理                            |
| [spring-boot-rabbitmq-availability](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-rabbitmq-availability) | spring-boot 整合 RabbitMQ，提供保证项目100%可靠性投递方案，confirm机制+数据库+定时任务等实现方案                           |
| [spring-boot-redis](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-redis) | spring-boot 整合 Redis单机版，项目中封装redis的工具类，[RedisUtil.java](https://github.com/sanliangitch/spring-boot-demo/blob/master/spring-boot-redis/src/main/java/com/wulang/boot/redis/utils/RedisUtil.java)，有最全的redis操作封装                       |
| [spring-boot-redis-sentinel](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-redis-sentinel) | spring-boot 整合 Redis Sentinel，使用连接池方式连接哨兵，对释放连接等操作也做了封装，让开发人员在开发中无用关心资源的释放，哨兵常见的原理和问题分析可参考给提供的[.md文档](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-redis-sentinel/md)                     |
| [spring-boot-redis-cluster](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-redis-cluster) | spring-boot 整合 Redis Cluster，提供了集群[配置文件](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-redis-cluster/cluster%E9%9B%86%E7%BE%A4%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6)，对集群的连接也提供了多种示例文件，对集群环境的开发和运维问题可参考给提供的[.md文档](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-redis-cluster/md)                     |
| [spring-boot-redis-lettuce](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-redis-lettuce) | spring-boot 使用Lettuce连接Redis，spring-boot在后面版本已经将spring-data-redis的驱动包替换为Lettuce，相信以后Lettuce也会成为一种趋势                   |
| [spring-boot-security-core](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-security-core) | 项目中重写ObjectPostProcessor方法，重写SecurityMetadataSource用来动态获取url权限配置，AccessDecisionManager来进行权限判断，核心配置类中已给出详细注释。                   |
| [sprng-boot-security-distributed](https://github.com/sanliangitch/spring-boot-demo/tree/master/sprng-boot-security-distributed) | 项目集成spring cloud注册中心，zuul网关等配置，为在分布式中验证与授权的集成方案。实现方式为UAA认证服务负责认证授权，所有请求经过网关到达微服务，网关负责鉴权客户端以及请求转发，网关将token解析后传给微服务，微服务进行授权。                   |
| [spring-boot-roctetmq-demo](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-roctetmq-demo) | 项目中提供发送异步消息、广播消息、顺序消息、延时消息、过滤消息、事物消息等示例。适合平时工作中API调用时的参考代码。                   |
| [spring-boot-roctetmq-shop-project](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-roctetmq-shop-project) | 模拟电商网站购物场景中的【下单】和【支付】业务，使用MQ保证在下单失败后系统数据的完整性，通过MQ进行数据分发，提高系统处理性能。项目中技术选型为SpringBoot、Dubbo、Zookeeper、RocketMQ、Mysql等。                   |
| [spring-boot-websocket-reamtime](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-websocket-reamtime) | 此 demo 主要演示了 Spring Boot 如何集成 WebSocket，实现后端主动往前端推送数据。网上大部分websocket的例子都是聊天室，本例主要是推送服务器状态信息。前端页面基于vue和element-ui实现。                   |
| [spring-boot-zuul](https://github.com/sanliangitch/spring-boot-demo/tree/master/spring-boot-zuul) | 此 demo 主要演示了 Spring Boot 如何改进zuul网关，实现对 zuul 进行改造实现动态路由与灰度发布。是生产和企业中非常实用的功能。                   |

## 联系方式(加好友，备注来源)
![weix](vx.jpg)
