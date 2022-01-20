package com.test.microservice.auth.bean;

import com.test.microservice.common.bean.User;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/1/19 10:47
 */
public class AuthInfo {
    private User userDate;
    private String token;

    public User getUserDate() {
        return userDate;
    }

    public void setUserDate(User userDate) {
        this.userDate = userDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
