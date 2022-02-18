package com.test.microservice.auth.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.test.microservice.auth.bean.LoginUser;
import com.test.microservice.auth.feign.LoginClient;
import com.test.microservice.common.bean.User;
import com.test.microservice.common.exception.ServiceException;
import com.test.microservice.common.result.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @description: Shiro远程获取账户信息
 * @author: Zhaotianyi
 * @time: 2022/1/19 11:48
 */
@Service
public class ShiroService {
    @Autowired
    private LoginClient loginClient;

    /**
     * 通过Login模块进行账户查询
     * @param userName 用户名
     * @return
     */
    public LoginUser selecUserByAccount(String userName) throws ServiceException{
        User user = new User();
        Result result = loginClient.selecUserByAccount(userName);
        String jsonString = JSONArray.toJSONString(result.getData());
        LoginUser loginUser = JSONObject.parseObject(jsonString, LoginUser.class);
        if (loginUser != null) {
            user.setUserName(loginUser.getLoginName());
            user.setPassword(loginUser.getLoginPasswd());
            user.setRole("admin");
        }else {
            throw new AuthenticationException("账号不存在!");
        }
        return loginUser;
    }

    /**
     * 获取拥有角色列表
     * @param userName 用户名
     * @return
     */
    public List<String> getRoles(String userName) {
        Result result = loginClient.selecRolesAndPermissions(userName);
        HashMap<String, List<String>> data =(HashMap<String, List<String>>) result.getData();
        List<String> roles = data.get("roles");
        return roles;
    }

    /**
     * 获取拥有权限列表
     * @param userName 用户名
     * @return
     */
    public List<String> getPermissions(String userName) {
        Result result = loginClient.selecRolesAndPermissions(userName);
        HashMap<String, List<String>> data =(HashMap<String, List<String>>) result.getData();
        List<String> permissions = data.get("permissions");
        return permissions;
    }

}
