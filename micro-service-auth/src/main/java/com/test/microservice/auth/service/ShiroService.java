package com.test.microservice.auth.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.test.microservice.auth.bean.LoginUser;
import com.test.microservice.auth.feign.LoginClient;
import com.test.microservice.common.bean.User;
import com.test.microservice.common.exception.ServiceException;
import com.test.microservice.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: Shiro远程获取账户是否存在
 * @author: Zhaotianyi
 * @time: 2022/1/19 11:48
 */
@Service
public class ShiroService {
    @Autowired
    private LoginClient loginClient;

    public User selecUserByAccount(String userName) {
        User user = new User();
        Result result = loginClient.selecUserByAccount(userName);
        String jsonString = JSONArray.toJSONString(result.getData());
        LoginUser loginUser = JSONObject.parseObject(jsonString, LoginUser.class);
        if (loginUser != null) {
            user.setUserName(loginUser.getLoginName());
            user.setPassword(loginUser.getLoginPasswd());
            user.setRole("admin");
        }else {
            throw new ServiceException("账号不存在!");
        }
        return user;
    }
}
