package com.lsk.example.authservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * {@link EnableResourceServer order=3} 创建的filter配置优先级高于 {@link EnableWebSecurity order=100}
 * 如下配置/me，直接访问时会校验access_token，不合法时直接返回。但是当访问授权时如/oauth/authorize，
 * {@link EnableAuthorizationServer order=0} 的优先级更高，会对其作检验，无认证信息重定向到login。
 * 此时默认被优先级高的ResourceServerConfig拦截,如果当前没配置formLogin则直接404；如果配置了，但session策略配置不对，
 * 同样认证数据不会传递到EnableWebSecurity,也就通不过认证。
 * 但如果将EnableWebSecurity优先级提高，并指定其拦截/login登陆认证则可避免。
 * <p>
 * <p>
 * 所以通过Order将EnableWebSecurity排序提前，则/me请求也会被拦截到，但可过滤掉/me的请求交给当前类来处理，
 * 这样该类就不用再配置formLogin及session相关配置,只拦截资源相关的url并校验token。
 */
@Configuration
@EnableResourceServer
@Order(5)
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
