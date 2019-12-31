package com.wulang.security.distributed.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 授权服务配置总结：授权服务配置分成三大块，可以关联记忆。
 *
 * 既然要完成认证，它首先得知道客户端信息从哪儿读取，因此要进行客户端详情配置。
 * 既然要颁发token，那必须得定义token的相关endpoint，以及token如何存取，以及客户端支持哪些类型的token。
 * 既然暴露除了一些endpoint，那对这些endpoint可以定义一些安全上的约束等。
 *
 * @author wulang
 * @create 2019/12/21/11:21
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
/*
    public class AuthorizationServerConfigurerAdapter implements AuthorizationServerConfigurer {
        public AuthorizationServerConfigurerAdapter() {}
        /**
        *AuthorizationServerSecurityConfigurer：用来配置令牌端点的安全约束.
        * /
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {}
        /**
        *ClientDetailsServiceConfigurer：用来配置客户端详情服务（ClientDetailsService），客户端详情信息在
        *这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
        * /
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {}
        /**
        *AuthorizationServerEndpointsConfigurer：用来配置令牌（token）的访问端点和令牌服务(token services)。
        * /
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {}
    }
 */

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     *客户端详情服务:用来配置客户端详情服务（ClientDetailsService）
     *
     * clientId：（必须的）用来标识客户的Id。
     * secret：（需要值得信任的客户端）客户端安全码，如果有的话。
     * scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     * authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
     * authorities：此客户端可以使用的权限（基于Spring Security authorities）。
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
         /* clients.inMemory()// 使用in-memory存储
                .withClient("c1")// client_id
                .secret(new BCryptPasswordEncoder().encode("secret"))//客户端密钥
                .resourceIds("res1")//资源列表
                .authorizedGrantTypes("authorization_code", "password","client_credentials","implicit","refresh_token")// 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .scopes("all")// 允许的授权范围
                .autoApprove(false)//false跳转到授权页面
                //加上验证回调地址
                .redirectUris("http://www.baidu.com")*/
        ;
    }

    /**
     * 用来配置令牌端点的安全约束
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //认证管理器
                .authenticationManager(authenticationManager)
                //授权码服务
                .authorizationCodeServices(authorizationCodeServices)
                //令牌管理服务
                .tokenServices(tokenService())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     *用来配置令牌端点(Token Endpoint)的安全约束
     *
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security
                //oauth/token_key是公开
                .tokenKeyAccess("permitAll()")
                //oauth/check_token公开
                .checkTokenAccess("permitAll()")
                //表单认证（申请令牌）
                .allowFormAuthenticationForClients()
        ;
    }

    //将客户端信息存储到数据库
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        //设置授权码模式的授权码如何存取
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service=new DefaultTokenServices();
        //客户端详情服务
        service.setClientDetailsService(clientDetailsService);
        //支持刷新令牌
        service.setSupportRefreshToken(true);
        //令牌存储策略
        service.setTokenStore(tokenStore);

        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);

        // 令牌默认有效期2小时
        service.setAccessTokenValiditySeconds(7200);
        // 刷新令牌默认有效期3天
        service.setRefreshTokenValiditySeconds(259200);
        return service;
    }

    //设置授权码模式的授权码如何存取，暂时采用内存方式
/*    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }*/
}
