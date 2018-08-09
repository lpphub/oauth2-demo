package com.lsk.example.authservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
public class UserController {
    @GetMapping("/test")
    public String user() {
        return "test";
    }

    @GetMapping("/me")
    public User current(Principal principal) {
        log.info(principal.getName());
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
