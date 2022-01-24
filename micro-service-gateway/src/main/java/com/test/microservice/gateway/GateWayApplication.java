package com.test.microservice.gateway;

import com.test.microservice.gateway.utils.AutowiredBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

/**
 * @description: 路由网关服务启动类
 * @author: Zhaotianyi
 * @time: 2021/11/15 10:59
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class GateWayApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(GateWayApplication.class, args);
        AutowiredBean.setApplicationContext(run);
    }

    /**
     * Spring-GateWay使用的WebFlux架构。
     * HttpMessage转换器，用于对Feign的请求转换。
     * @param converters
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }



}
