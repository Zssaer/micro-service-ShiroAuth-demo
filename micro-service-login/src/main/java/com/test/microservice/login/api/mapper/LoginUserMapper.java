package com.test.microservice.login.api.mapper;


import com.test.microservice.login.api.model.LoginUser;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;

public interface LoginUserMapper extends ConditionMapper<LoginUser> {
}