package com.test.microservice.login.api.service.impl;

import com.test.microservice.common.exception.ServiceException;
import com.test.microservice.login.api.controller.response.LoginResp;
import com.test.microservice.login.api.mapper.LoginUserMapper;
import com.test.microservice.login.api.model.LoginUser;
import com.test.microservice.login.api.service.LoginSerivce;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
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

    @Override
    public LoginUser selecUserByAccount(String userName) {
        LoginUser user = null;
        Condition condition = new Condition(LoginUser.class);
        condition.createCriteria().andEqualTo("loginName", userName);
        List<LoginUser> users = loginUserMapper.selectByCondition(condition);
        if (!users.isEmpty()) {
            user = users.get(0);
        }
        return user;
    }
}
