package com.test.microservice.login.api.feign;

import com.test.microservice.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @description: 远程调用Auth模块Client
 * @author: Zhaotianyi
 * @time: 2022/1/19 11:40
 */
@FeignClient(value = "micro-service-auth")
public interface AuthLoginClient {
    /**
     * 通过用户名密码进行登录
     */
    @PostMapping("/login/loginByPwd")
    Result loginPCByPwd(@RequestParam String user_name, @RequestParam String Pwd);
}
