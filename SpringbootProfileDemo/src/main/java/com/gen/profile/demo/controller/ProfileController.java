package com.gen.profile.demo.controller;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("application-prod.properties")
public class ProfileController {

    @Value("${spring.profile.default}")
    String message;

    @Value("${prod.property}")
    String prodProperty;

    @PostConstruct
    public void show(){
        System.out.println("Post Construct message : "+ message);

    }

    @PreDestroy
    public void show1(){
        System.out.println("PreDestroy message : "+ message);

    }
}
