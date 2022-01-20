package com.test.microservice.auth.feign;

import com.test.microservice.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/1/19 14:41
 */
@FeignClient(value = "micro-service-login-api")
public interface LoginClient {
    @GetMapping("/login/selecUserByAccount")
    Result selecUserByAccount(@RequestParam String userName);
}
