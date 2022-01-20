package com.test.microservice.login.api.controller.response;


import com.test.microservice.login.api.dto.LoginUserDTO;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2021/11/15 16:56
 */
public class LoginResp {
    private LoginUserDTO loginUserDTO;
    private String token;

    public LoginUserDTO getLoginUserDTO() {
        return loginUserDTO;
    }

    public void setLoginUserDTO(LoginUserDTO loginUserDTO) {
        this.loginUserDTO = loginUserDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
