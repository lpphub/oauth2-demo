package com.lsk.example.msdemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/callback")
    public String callback() {
        return "callback";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/aa")
    public String a() {
        return "aaa";
    }
}
