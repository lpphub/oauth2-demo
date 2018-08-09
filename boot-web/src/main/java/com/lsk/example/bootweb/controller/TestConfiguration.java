package com.lsk.example.bootweb.controller;

import com.lsk.example.bootweb.TestPostProcessor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
    @Bean
    public BeanFactoryPostProcessor custom() {
        return new TestPostProcessor();
    }

}
