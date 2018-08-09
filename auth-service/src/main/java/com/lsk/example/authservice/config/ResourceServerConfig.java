package com.lsk.example.authservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * {@link EnableResourceServer order=3} 创建的filter配置优先级高于 {@link EnableWebSecurity order=100}
 * 如下配置/me，直接访问时会校验access_token，不合法时直接返回。但是当访问授权时如/oauth/authorize，
 * 因当前没有配置拦截，则会由EnableWebSecurity拦截处理，此时如果EnableWebSecurity配置了form登陆认证则重定向到/login，
 * 然后又先被EnableResourceServer拦到，如果当前没配置formLogin则直接404；如果配置了，但session策略配置不对，
 * 则数据不会传递到EnableWebSecurity,就通不过认证。
 * <p>
 * <p>
 * 如果通过Order将EnableWebSecurity排序提前，则/me请求也会被拦截到，除非过滤掉/me的请求交给当前类来处理，
 * 这样该类就不用再配置formLogin及session相关配置
 */
@Configuration
@EnableResourceServer
@Order(6)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 授权服务也作为资源服务，提供当前用户信息接口
        http.authorizeRequests().antMatchers("/me").authenticated()
//                .and().formLogin()
                .and().csrf().disable();
        // 配置session策略，否则form登陆认证数据无法往下传递
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }
}
