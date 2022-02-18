package com.test.microservice.auth.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 登录用户DTO
 * @author: Zhaotianyi
 * @time: 2022/2/18 14:14
 */
public class LoginUserDTO  implements Serializable {
    private Integer id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户角色
     */
    private List<String> Roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getRoles() {
        return Roles;
    }

    public void setRoles(List<String> roles) {
        Roles = roles;
    }
}
