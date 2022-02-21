package com.test.microservice.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 调用授权微服务，验证连接是否授权
 * @author: Zhaotianyi
 * @time: 2022/1/20 11:17
 */
@FeignClient(value = "micro-service-auth")
public interface AuthorizeClient {
    @GetMapping("/login/isPermitted")
    boolean isPermitted(@RequestParam String requestURI,@RequestParam String httpMethod, @RequestParam String token);
}
