package com.wulang.bullet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author wulang
 * @create 2021/1/1/11:20
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketAutoConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 添加这个Endpoint,这样在页面就可以通过websocket连接上服务
     * 也就是我们配置websocket的服务地址,并且可以指定是否使用socketJS
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            //将/bullet路径注册为stomp的端点,用户连接了这个端点就可以进行websocket通讯,支持socketJS
            .addEndpoint("/bullet")
            //允许跨域访问
            .setAllowedOrigins("*")
            //使用sockJS
            .withSockJS();
    }

   /* *//**
     * 输入通道参数配置 TODO
     *
     * @param registration
     *//*
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                // 判断是否为链接,如果是,需要获取token,并且设置用户对象
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String token = accessor.getFirstNativeHeader("Auth-Token");
                    // TODO 设置用户信息
                    accessor.setUser(null);
                }
                return message;
            }
        });
    }*/

    /**
     * 配置消息代理
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //订阅Broker名称
        //registry.enableSimpleBroker("/topic","/user");
        // 配置代理域,可以配置多个,配置代理目的地址前缀为/toAll,可以再配置域上向客户端推送消息
        registry.enableSimpleBroker("/toAll");
        //全局使用的订阅前缀（客户端订阅路径上会体现出来）
        //registry.setApplicationDestinationPrefixes("/app/");
        //
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        //registry.setUserDestinationPrefix("/user/");
    }
}
