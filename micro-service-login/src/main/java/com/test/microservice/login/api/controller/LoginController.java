package com.test.microservice.login.api.controller;

import com.test.microservice.common.result.Result;
import com.test.microservice.common.result.ResultBuilder;
import com.test.microservice.login.api.feign.AuthLoginClient;
import com.test.microservice.login.api.model.LoginUser;
import com.test.microservice.login.api.service.LoginSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2021/11/15 16:27
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthLoginClient authLoginClient;
    @Resource
    private LoginSerivce loginSerivce;

    @PostMapping
    public Result login(String user_name, String Pwd) {
        return authLoginClient.loginPCByPwd(user_name, Pwd);
    }

    @GetMapping("/selecUserByAccount")
    public Result selecUserByAccount(@RequestParam String userName) {
        LoginUser user = loginSerivce.selecUserByAccount(userName);
        return ResultBuilder.successResult(user);
    }
}
