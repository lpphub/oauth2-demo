package com.lsk.example.authservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.Resource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Resource(name = BeanIds.AUTHENTICATION_MANAGER)
    private AuthenticationManager authenticationManager;
    @Resource(name = BeanIds.USER_DETAILS_SERVICE)
    private UserDetailsService userDetailsService;

    // 配置客户端信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("test")
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "refresh_token")
                .scopes("all")
                .secret("{noop}123456")
                // 设置access-token, refresh-token的过期时间 默认12h, 30d
                .accessTokenValiditySeconds(3600).refreshTokenValiditySeconds(3600 * 12);
    }

    // 配置token节点的安全策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
                // 允许client表单验证，否则不能认证密码模式
                .allowFormAuthenticationForClients();
    }

    // 配置授权token的节点和token服务
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(new InMemoryTokenStore())
                // 基于密码认证时，需要配置authenticationManager
                .authenticationManager(authenticationManager)
                // refreshToken时，需要配置userDetailsService
                .userDetailsService(userDetailsService);
    }

}
