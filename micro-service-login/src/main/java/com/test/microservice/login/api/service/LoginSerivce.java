package com.test.microservice.login.api.service;

import com.test.microservice.common.bean.User;
import com.test.microservice.login.api.model.LoginUser;

import java.util.HashMap;
import java.util.List;

public interface LoginSerivce {
    LoginUser selecUserByAccount(String userName);
    HashMap<String,List<String>> selecRolesAndPermissions(String userName);
}
