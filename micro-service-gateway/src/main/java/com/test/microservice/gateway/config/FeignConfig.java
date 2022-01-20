package com.test.microservice.gateway.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/1/20 15:06
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLevel() {
        //这里记录所有
        return Logger.Level.FULL;
    }
}
