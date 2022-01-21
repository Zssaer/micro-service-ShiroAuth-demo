package com.test.microservice.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.test.microservice.gateway.config.GlobalFilterConfig.ATTRIBUTE_IGNORE_AUTH_GLOBAL_FILTER;

/**
 * @description: 忽略授权认证过滤器工程类，主要用于对不需要认证的开发接口跳过全局认证
 * @author: Zhaotianyi
 * @time: 2022/1/21 10:08
 */
@Component
public class IgnoreAuthFilterFactor extends AbstractGatewayFilterFactory<IgnoreAuthFilterFactor.Config> {
    public IgnoreAuthFilterFactor() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return this::filter;
    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(ATTRIBUTE_IGNORE_AUTH_GLOBAL_FILTER, true);
        return chain.filter(exchange);
    }

    public static class Config {

    }

    @Override
    public String name() {
        return "IgnoreAuthFilter";
    }

}
