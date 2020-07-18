package com.study.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.FeignClient;

@SpringBootApplication
@EnableDiscoveryClient
@FeignClient
public class TalkApplication {
    public static void main(String[] args) {
        SpringApplication.run(TalkApplication.class, args);
    }
}
