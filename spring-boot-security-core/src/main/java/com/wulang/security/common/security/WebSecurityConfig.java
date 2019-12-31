package com.wulang.security.common.security;

import com.wulang.security.admin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsUtils;

/**
 * @author: wulang
 * @date: 2019/11/22
 * @description:
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthService authService;

    @Autowired
    private UrlAccessDecisionManager urlAccessDecisionManager;

    @Autowired
    private CustomMetadataSource metadataSource;

    @Autowired
    private AuthenticationAccessDeniedHandler deniedHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationLogoutHandler customAuthenticationLogoutHandler;

    /**
     * 身份验证管理器生成器
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**", "/login_p",
                "/security/system/menu/list",
                "/favicon.ico",
                "login.html", "/**/*.html",
                "/v2/api-docs",
                "/swagger-resources/**",
                "/swagger-ui.html**",
                "/webjars/**");
    }

    /**
     * HttpSecurity实际上就是在配置Spring Security的过滤器链，诸如CSRF、CORS、表单登录等，每个
     * 配置器对应一个过滤器。我们可以通过 HttpSecurity 配置过滤器的行为，甚至可以像CRSF一样直接关
     * 闭过滤器。
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //authorizeRequests:所有security全注解配置实现的开端，表示开始说明需要的权限。
        //需要的权限两部分，第一部分是拦截的路径，第二部分访问该路径需要的权限
        //先授权，再认证
        //antMatchers：表示拦截什么路径。
        //permitAll：任何权限都可以访问，直接放行所有
        //anyRequest：任何的请求。
        //authenticated：认证后才能访问
        //and().csrf().disable():固定写法，表示使csrf拦截失效

        // 禁用缓存
//        http.headers().cacheControl();

//        http
//                .authorizeRequests()
//                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable();
        http     // 定义哪些url 是否需要保护
                .authorizeRequests()
                //对preflight（"预检"请求）放行
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                //基于spring security实现动态权限的方法
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        //重写SecurityMetadataSource用来动态获取url权限配置
                        o.setSecurityMetadataSource(metadataSource);
                        //AccessDecisionManager来进行权限判断
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                })
                .and()
                //允许表单登录
                    .formLogin()
                        //指定我们自己的登录页,spring security以重定向方式跳转到/login_p
                        .loginPage("/login_p")
                        //自定义表单提交登录的url默认 /login
                        .loginProcessingUrl("/security/login")
                        //自定义登录参数名 默认为 username
                        .usernameParameter("username")
                        //自定义登录参数名 默认为 password
                        .passwordParameter("password")
                        //登录失败处理
                        .failureHandler(customAuthenticationFailureHandler)
                        //登录成功处理器
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
                .and()
                    .logout()
                        //设置触发退出操作的URL (默认是/logout ).
                        .logoutUrl("/security/logout")
                        //添加一个LogoutHandler ，用于实现用户退出时的清理工作.默认SecurityContextLogoutHandler 会被添加为最后一个LogoutHandler 。
                        .logoutSuccessHandler(customAuthenticationLogoutHandler)
                        //指定是否在退出时让HttpSession 无效。 默认设置为 true。
                        .invalidateHttpSession(true)
                        .permitAll()
                .and()
                    // 关闭 CSRF
                    .csrf().disable()
                    //添加未授权处理   未登录或者登陆过期、未授权 的拦截回调
//                    .exceptionHandling().authenticationEntryPoint()
                    //权限不足处理
                    .exceptionHandling().accessDeniedHandler(deniedHandler);
    }

    // 添加JWT filter
    //        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

//    http
//            .authorizeRequests()
//            .antMatchers("/r/r1").hasAuthority("p1")  访问/r/r1资源的 url需要拥有p1权限。
//            .antMatchers("/r/**").authenticated() （1）url匹配/r/**的资源，经过认证后才能访问。
//            .anyRequest().permitAll() （2）其他url完全开放。
//            .and()
//            .formLogin().successForwardUrl("/login‐success"); （3）其他url完全开放。
}
