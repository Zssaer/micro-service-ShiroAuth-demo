package com.test.microservice.login.api.feign;

import com.test.microservice.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/1/19 11:40
 */
@FeignClient(value = "micro-service-auth")
public interface AuthLoginClient {
    @PostMapping("/login/loginByPwd")
    Result loginPCByPwd(@RequestParam String user_name, @RequestParam String Pwd);
}
