package com.test.microservice.login.api.service;

import com.test.microservice.common.bean.User;
import com.test.microservice.login.api.model.LoginUser;

public interface LoginSerivce {
    LoginUser selecUserByAccount(String userName);
}
