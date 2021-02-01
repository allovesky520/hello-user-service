package com.springboot.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author zihui
 * @version 1.0
 * @title: WebSecurityConfig
 * @description: TODO
 * @date 2021-01-18 10:52:16
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication() // 项目启动 账户-密码-角色 信息保存进内存中
                .withUser("zhangsan").password("{noop}123456").roles("VIP1")
                .and().withUser("lisi").password("{noop}123456").roles("VIP2")
                .and().withUser("wangwu").password("{noop}123456").roles("VIP1", "VIP2");

        /*
          说明：
            1.这里采用的的是把用户角色保存在内存中，数据是写死的，当然数据可以从数据库中查出再写入内存中；
            2.随后定义的三个用户，没有用户定义了其用户名，密码和角色
            3.Security5默认要求密码使用加密，不加密的话就使用"{noop}123456"这样的写法，加密的话需要使用
                PasswordEncoder的实现类进行加密
         */
    }

    /**
     * 授权
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁止隧道（跨站防护）、禁止跨域、禁止头部
//        http.csrf().disable().cors().disable().headers().disable();
        http.csrf().disable();
        // 所有的人都可以访问的路径
        http.authorizeRequests()// 定义哪些URL需要被保护、哪些不需要被保护
//                .antMatchers(HttpMethod.OPTIONS).permitAll()    // 直接放过 OPTIONS 请求
//                .antMatchers("/**").permitAll()  // 不检查登录令牌，放行所有请求
                .antMatchers("/static/**", "/", "/csrf").permitAll()
//                .antMatchers("/v2/api-docs","/swagger-ui.html","/webjars/**","/swagger-resources/**").permitAll()
                .antMatchers("/favicon.ico", "/actuator/**").permitAll()
                .antMatchers("/v2/**").permitAll()
//                .antMatchers("/login").permitAll()
                // VIP1的用户可以访问v1下的所有路径
                .antMatchers("/v1/test/hello").hasRole("VIP1")
                .anyRequest()// 任何请求,登录后可以访问
                .authenticated()
        ;

//        /*
//         开启自动配置的登录功能，如果没有登录就会来到登录页面
//            1. 会自动生成登录页面 /login
//            2. 登录失败会自动重定向到 /login?error
//         */
         http.formLogin()
//                 /*
//                自定义登录页面设置
//                    1. 登录的路径还是设置成/login，否则算是自定义登录路径，其他的设置也需要改变
//                       /login（get）：到登录页，， 自定义的话就是 /authenticate（get）
//                       /login（post）：登录检查，，自定义的话就是 /authenticate（post）
//                    2. 可以自定义form表达提交的参数名称
//                       默认username字段提交用户名，可以通过usernameParameter自定义
//                       默认password字段提交密码，可以用过passwordParameter自定义
//                    3. loginProcessingUrl("/xxx") 可以自定义登录成功后跳转的路径
//                 */
//             .loginPage("/v1/user/login")
//             .loginProcessingUrl("/v1/user/login/process")
        ;
//        /*
//         开启自动配置的退出功能：
//            1. 访问/logout请求，用户注销，清楚session
//            2. 注销成功后重定向到 login?logout，，可以通过logoutSuccessUrl("/")自定义
//         */
        http.logout()
//                .logoutSuccessUrl("/");
        ;
    }
}
