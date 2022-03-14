package com.test.microservice.login.api.controller;

import com.test.microservice.common.result.Result;
import com.test.microservice.common.result.ResultBuilder;
import com.test.microservice.common.utils.ValidateUtil;
import com.test.microservice.login.api.controller.requst.RegisterReq;
import com.test.microservice.login.api.service.LoginSerivce;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.test.microservice.common.utils.ValidateUtil.REGEX_PASS_WORD;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/3/14 16:02
 */
@RequestMapping("/register")
@RestController
public class RegisterController {
    @Resource
    private LoginSerivce loginSerivce;

    /**
     * 注册用户
     * 要求密码长度在6-20位之间。
     *
     * @param req 注册请求类
     * @return Result结果
     */
    @PostMapping
    public Result register(@RequestBody RegisterReq req) {
        if (ValidateUtil.isEmpty(req.getPasswd())) {
            return ResultBuilder.failResult("密码不能设置为空!");
        }
        if (!req.getPasswd().matches(REGEX_PASS_WORD)) {
            return ResultBuilder.failResult("密码不规范！密码格式长度为6位-20位");
        }
        if (!req.getRePasswd().equals(req.getPasswd())) {
            return ResultBuilder.failResult("两次密码输入不相同！请检查后再注册");
        }
        loginSerivce.register(req);
        return ResultBuilder.successResult();
    }
}
