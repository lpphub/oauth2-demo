package com.lsk.example.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 指定拦截/oauth/**, /test, /login，这样可以把/me过滤掉，交给ResourceServerConfig配置处理
        http.requestMatchers().antMatchers("/oauth/**", "/test", "/login")
                .and().authorizeRequests().antMatchers("/test").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置密码加密方式
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("USER");
    }

    // 当oauth2使用密码认证时，需要配置
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 当oauth2 refresh token时，需要配置
    @Bean(name = BeanIds.USER_DETAILS_SERVICE)
    @Override
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }


}
