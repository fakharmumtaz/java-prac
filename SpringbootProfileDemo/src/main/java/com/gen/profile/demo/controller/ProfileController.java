package com.gen.profile.demo.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProfileController {

    @Value("${spring.profile.default}")
    String message;

    @PostConstruct
    public void show(){
        System.out.println("message : "+ message);

    }
}
