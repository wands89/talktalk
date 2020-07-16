package com.study.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UCApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCApplication.class,args);
    }
}
