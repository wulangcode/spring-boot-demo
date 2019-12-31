官方提供了redis-trib.rb脚本配置Redis的cluster，为我们提供了方便，但是在自己搭建过程中，遇到很多问题，在此做一个安装教程。

Redis版本升级中一些东西会有出入，如本人测试环境中的Redis3.2版本任然支持 ./redis-trib.rb create --replicas 1，
但在Redis5.0中创建集群已经使用“redis-cli”来实现，对于更高版本的安装，本人精力有限，可有其他朋友补充。

前提说明：在全部搭建好之前请不要设置密码，可以等搭建好之后通过命令行设置。
         如果你设置了，那么你使用redis的很多命令都需要通过密码认证，一般是加 ‘-a 密码’的形式。
         若真的需要在设置密码后，再要通过工具来握手，可 vim client.rb 来配置当中的password来改为自己的密码。

         ——使用redis-trib.rb构建集群,完成前不要配置密码

         ——集群构建完再通过config set + config rewrite命令逐个实例设置密码

         ——对集群设置密码，requirepass和masterauth都需要设置

         ——各个节点密码都必须一致，否则Redirected就会失败

1：下载源码包：
    https://cache.ruby-lang.org/pub/ruby/2.2/ruby-2.2.3.tar.gz

2：解压安装：
    tar xzvf ruby-2.2.3.tar.gz -C /root/redis
    cd /root/redis/ruby-2.2.3/
    ./configure
    make
    make install

3：安装Ruby之后，因redis-trib.rb依赖于gem包redis，因此需要执行命令：gem  install redis，报错：
ERROR:  Loading command: install (LoadError)
        cannot load such file -- zlib
ERROR:  While executing gem ... (NoMethodError)
    undefined method `invoke_with_build_args' for nil:NilClass

    解决方法：
    //这个是ubuntu系统中的命令，而网上教程大多是以这个为环境搭建，下面提供 centos7 的安装方法，若不需要可直接跳过
    apt-get install zlib1g-dev
    cd /root/redis/ruby-2.2.3/ext/zlib
    ruby ./extconf.rb
    make
    make install
    {
     centos7中安装步骤：

        curl -O http://www.zlib.net/zlib-1.2.11.tar.gz

        tar xvfz zlib-1.2.11.tar.gz

        ./configure

        sudo make && sudo make install
    }

4：再次执行命令gem  install  redis，再次报错：
    ERROR:  While executing gem ... (Gem::Exception)
        Unable to require openssl, install OpenSSL and rebuild ruby (preferred) or use non-HTTPS sources

    解决方法：
    //此处 apt-get 仍然为ubuntu系统中的命令，centos7的命令也在大括号中给出
    apt-get install libssl-dev
    {
        centos7中运行
        yum install openssl-devel
    }
    cd /root/redis/ruby-2.2.3/ext/openssl
    ruby ./extconf.rb
    ln -s /root/redis/ruby-2.2.3/include /
    make
    make install

5：再次执行命令gem  install  redis，竟然还是报错，错误信息是：(这一步在两台服务器做搭建环境时，一台出了这个错误，另外一台没有出现，但是还是给出解决方法)
    Errno::ECONNRESET: Connection reset by peer

    查了一下原因，竟然是伟大的墙做的贡献(https://ruby.taobao.org/)，解决办法如下：

    #gem sources --add https://ruby.taobao.org/ --remove https://rubygems.org/
    https://ruby.taobao.org/ added to sources
    https://rubygems.org/ removed from sources

    #gem sources -l
    *** CURRENT SOURCES ***

    https://ruby.taobao.org/

 6：再次执行命令gem  install  redis，终于成功，此时，就可以运行redis-trib.rb脚本了。
     可能有部分朋友发现依旧执行不了这个命令，那是因为没有到redis-trib.rb所在得文件目录
     执行
     find / * -name redis-trib.rb
     找到所在文件夹目录

     1.要在redis-trib.rb所在得文件目录下运行该命令；
     2.要在命令前加上 ./


补充：
    在第一次安装时一直报错连接不上，最后排查其中原因，redis版本有很大的问题，redis 3.2之前，配置文件中提供bind（绑定地址）和requirepass（身份密码）来维护redis的安全，
    而在redis 3.2之后提供protected-mode（保护模式）参数来维护安全，在节点握手时应把参数设置为 no，本项目中提供的配置文件也仅在3.2后的版本适用。
