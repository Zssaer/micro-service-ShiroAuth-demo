package com.test.microservice.gateway.config;

import com.alibaba.fastjson.JSON;
import com.test.microservice.common.result.Result;
import com.test.microservice.common.result.ResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/1/20 14:36
 */
@Configuration
public class GlobalExceptionConfig implements ErrorWebExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionConfig.class);
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        // 修改response结果头部，设置ContentType为JSON类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }
        logger.error(ex.getMessage(), ex);

        return response
                .writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = response.bufferFactory();
                    Result result = ResultBuilder.failResult(ex.getMessage());
                    DataBuffer wrap = bufferFactory.wrap(JSON.toJSONBytes(result));
                    return wrap;
                }));
    }
}
