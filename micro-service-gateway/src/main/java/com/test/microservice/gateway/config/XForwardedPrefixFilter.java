package com.test.microservice.gateway.config;

import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * @description: 删除 X-Forwarded-Prefix的多出的参数 ','符号
 * @author: Zhaotianyi
 * @time: 2022/3/15 17:43
 */
@Component
public class XForwardedPrefixFilter implements HttpHeadersFilter, Ordered {

    @Override
    public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
        List<String> xForwareds = input.get("X-Forwarded-Prefix");
        String forward = xForwareds.get(0);
        if (forward.contains(",")) {
            forward = forward.replaceAll(",", "");
        }

        input.set("X-Forwarded-Prefix", forward);
        return input;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
