package com.test.microservice.login.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/1/19 15:10
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.test.microservice.login.api.feign")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
