package com.test.microservice.login.api.controller;

import com.test.microservice.common.result.Result;
import com.test.microservice.common.result.ResultBuilder;
import com.test.microservice.login.api.feign.AuthLoginClient;
import com.test.microservice.login.api.model.LoginUser;
import com.test.microservice.login.api.service.LoginSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;

/**
 * @description: 登录操作Api
 * @author: Zhaotianyi
 * @time: 2021/11/15 16:27
 */
@Api(value = "登录模块",tags = {"登录模块"})
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthLoginClient authLoginClient;
    @Resource
    private LoginSerivce loginSerivce;

    /**
     * 用户登录
     * @param user_name 用户名
     * @param Pwd 密码
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping
    public Result login(String user_name, String Pwd) {
        return authLoginClient.loginPCByPwd(user_name, Pwd);
    }

    /**
     * 获取登录信息
     */
    @ApiOperation(value = "获取当前登录信息",notes = "需要登录")
    @GetMapping("/getLoginInfo")
    public Result getLoginInfo() {
        return authLoginClient.LoginInfo();
    }

    /**
     * 通过用户名查询用户
     * @param userName 用户名
     * @return
     */
    @GetMapping("/selecUserByAccount")
    public Result selecUserByAccount(@RequestParam String userName) {
        LoginUser user = loginSerivce.selecUserByAccount(userName);
        return ResultBuilder.successResult(user);
    }

    /**
     * 通过用户名查询用户角色和权限
     * @param userName 用户名
     * @return
     */
    @GetMapping("/selecRolesAndPermissions")
    public Result selecRolesAndPermissions(@RequestParam String userName) {
        HashMap<String, List<String>> map = loginSerivce.selecRolesAndPermissions(userName);
        return ResultBuilder.successResult(map);
    }
}
