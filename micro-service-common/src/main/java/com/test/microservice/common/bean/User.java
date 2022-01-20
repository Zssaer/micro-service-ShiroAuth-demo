package com.test.microservice.common.bean;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/1/19 10:43
 */
public class User implements Serializable {
    private String userName;
    private String password;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
