package com.test.microservice.login.api.service.impl;

import com.test.microservice.login.api.mapper.LoginPermissionMapper;
import com.test.microservice.login.api.mapper.LoginRoleMapper;
import com.test.microservice.login.api.mapper.LoginUserMapper;
import com.test.microservice.login.api.mapper.RolePermissionMapper;
import com.test.microservice.login.api.model.LoginPermission;
import com.test.microservice.login.api.model.LoginRole;
import com.test.microservice.login.api.model.LoginUser;
import com.test.microservice.login.api.model.RolePermission;
import com.test.microservice.login.api.service.LoginSerivce;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Resource
    private LoginPermissionMapper loginPermissionMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;

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
        // 获取用户的角色 和 用户的权限
        // 拥有多个角色以|来进行划分
        String[] roles = users.get(0).getUserType().split("|");
        ArrayList<String> loginRoles = new ArrayList<>();
        ArrayList<String> loginPermissions = new ArrayList<>();
        for (String role : roles) {
            LoginRole loginRole = loginRoleMapper.selectByPrimaryKey(Integer.valueOf(role));
            loginRoles.add(loginRole.getRoleName());

            Example example1 = new Example(RolePermission.class);
            example1.createCriteria().andEqualTo("roleId", role);
            Stream<RolePermission> rolePermissionStream = rolePermissionMapper.selectByExample(example1).stream();
            List<Integer> permissionIdList = rolePermissionStream.map(RolePermission::getPermissionId).collect(Collectors.toList());
            for (Integer permissionId : permissionIdList) {
                LoginPermission permission = loginPermissionMapper.selectByPrimaryKey(permissionId);
                String requestPath = permission.getPath() + ":" + permission.getMethod();
                loginPermissions.add(requestPath);
            }
        }
        map.put("roles", loginRoles);
        map.put("permissions", loginPermissions);
        // 权限部分省略
        return map;
    }
}
