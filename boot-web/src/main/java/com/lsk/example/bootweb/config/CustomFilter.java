package com.lsk.example.bootweb.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

public class CustomFilter extends AbstractAuthenticationProcessingFilter {

    public CustomFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/callback"));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return new UsernamePasswordAuthenticationToken("admin", 1, Collections.emptyList());
    }
}
