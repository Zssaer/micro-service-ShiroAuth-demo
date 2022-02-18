package com.test.microservice.auth.config;

import com.test.microservice.common.exception.ServiceException;
import com.test.microservice.common.result.Result;
import com.test.microservice.common.result.ResultBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/2/18 15:38
 */
@ControllerAdvice
public class ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * 服务操作报错拦截
     *
     * @param ex 错误类型
     * @return Result
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceException.class)
    public Result serviceExceptionHandle(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return ResultBuilder.failResult(ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public Result AuthenticationExceptionHandle(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return ResultBuilder.failResult("登录失败!");
    }
}
