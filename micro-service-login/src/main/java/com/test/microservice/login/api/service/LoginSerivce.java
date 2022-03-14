package com.test.microservice.login.api.service;

import com.test.microservice.login.api.controller.requst.RegisterReq;
import com.test.microservice.login.api.model.LoginUser;

import java.util.HashMap;
import java.util.List;

public interface LoginSerivce {
    LoginUser selecUserByAccount(String userName);
    HashMap<String,List<String>> selecRolesAndPermissions(String userName);

    void register(RegisterReq req);
}
