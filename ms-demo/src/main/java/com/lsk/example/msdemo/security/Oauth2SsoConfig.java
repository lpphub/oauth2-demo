package com.lsk.example.msdemo.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * EnableOAuth2Sso将服务作为oauth客户端配置，用于拦截授权请求。与EnableResourceServer的区别在于其会把未授权用户
 * 重定向到授权服务器，而EnableResourceServer仅检验请求是否携带access_token，两者是冲突的，默认EnableResourceServer
 * 的排序优先级较高。
 * <p>
 * sso适合配置在网关层, resource配置在资源层
 */
@Configuration
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Oauth2SsoConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/test").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
    }
}
