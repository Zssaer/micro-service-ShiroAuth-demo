package com.test.microservice.gateway.config;

import com.test.microservice.common.exception.ServiceException;
import com.test.microservice.gateway.utils.AutowiredBean;
import com.test.microservice.gateway.feign.AuthorizeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * @description: 全局过滤器配置
 * 主要用于授权、日志记录等功能
 * @author: Zhaotianyi
 * @time: 2022/1/20 14:01
 */
@Configuration
public class GlobalFilterConfig {

    private static final Logger logger = LoggerFactory.getLogger(GlobalFilterConfig.class);
    public final static String ATTRIBUTE_IGNORE_AUTH_GLOBAL_FILTER = "@IgnoreAuthFilter";

    /**
     * 授权拦截
     */
    @Bean
    @Order(-1)
    public GlobalFilter auth() {
        return (exchange, chain) -> {
            /* 授权放行检查 */
            if (exchange.getAttribute(ATTRIBUTE_IGNORE_AUTH_GLOBAL_FILTER) != null) {
                return chain.filter(exchange);
            } else {
                logger.info("进行授权认证操作...");
                // 获取请求微服务的请求路径
                String path = exchange.getRequest().getPath().toString();
                // 获取请求头部的Token
                String token = exchange.getRequest().getHeaders().getFirst("token");
                if (token == null) {
                    token = "";
                }
                AuthorizeClient authorizeClient = AutowiredBean.getBean(AuthorizeClient.class);
                /** 进行授权验证操作 */
                boolean authResult = authorizeClient.isPermitted(path, token);
                if (!authResult) {
                    throw new ServiceException("未授权，无法访问！");
                }
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    logger.info("授权认证通过");
                }));
            }
        };
    }
}
