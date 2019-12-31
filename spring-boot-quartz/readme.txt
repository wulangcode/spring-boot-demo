数据库：spring-boot-quartz
建表sql：sql/create_table.sql

登录页：
    http://localhost:9001/quartz/login.shtml
    假的，登录效果没有实现，用户名密码无需输入，直接点登录就会跳转主页
主页：
    http://localhost:9001/quartz/main.shtml
    不从登录页跳转，直接访问主页也是可以的

最佳实践
    JobStore选择RAMJobStore；
    持久化我们自定义的job，应用启动的时候加载给quartz，作为quartz job的初始值
    quartz job无需注入到spring，但是job中能注入spring的常规bean