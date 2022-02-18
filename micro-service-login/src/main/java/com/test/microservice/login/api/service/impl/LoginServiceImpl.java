package com.test.microservice.login.api.service.impl;

import com.test.microservice.login.api.mapper.LoginRoleMapper;
import com.test.microservice.login.api.mapper.LoginUserMapper;
import com.test.microservice.login.api.model.LoginRole;
import com.test.microservice.login.api.model.LoginUser;
import com.test.microservice.login.api.service.LoginSerivce;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2021/11/15 17:01
 */
@Service
public class LoginServiceImpl implements LoginSerivce {
    @Resource
    private LoginUserMapper loginUserMapper;
    @Resource
    private LoginRoleMapper loginRoleMapper;

    /**
     * 通过用户名获取角色信息
     *
     * @param userName
     * @return
     */
    @Override
    public LoginUser selecUserByAccount(String userName) {
        LoginUser user = null;
        Example example = new Example(LoginUser.class);
        example.createCriteria().andEqualTo("loginName", userName);
        List<LoginUser> users = loginUserMapper.selectByExample(example);
        if (!users.isEmpty()) {
            user = users.get(0);
        }
        return user;
    }

    /**
     * 通过用户名获取角色列表
     *
     * @param userName
     * @return
     */
    @Override
    public HashMap<String, List<String>> selecRolesAndPermissions(String userName) {
        HashMap<String, List<String>> map = new HashMap<>();
        Example example = new Example(LoginUser.class);
        example.createCriteria().andEqualTo("loginName", userName);
        List<LoginUser> users = loginUserMapper.selectByExample(example);
        // 获取用户的角色
        // 拥有多个角色以|来进行划分
        String[] roles = users.get(0).getUserType().split("|");
        ArrayList<String> loginRoles = new ArrayList<>();
        for (String role : roles) {
            LoginRole loginRole = loginRoleMapper.selectByPrimaryKey(Integer.valueOf(role));
            loginRoles.add(loginRole.getRoleName());
        }
        map.put("roles", loginRoles);

        // 权限部分省略
        return map;
    }
}
