package com.lsk.example.msdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsDemoApplication.class, args);
    }

}
