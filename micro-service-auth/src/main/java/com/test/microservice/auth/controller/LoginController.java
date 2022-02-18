package com.test.microservice.auth.controller;

import com.test.microservice.auth.bean.AuthInfo;
import com.test.microservice.auth.dto.LoginUserDTO;
import com.test.microservice.auth.feign.LoginClient;
import com.test.microservice.common.result.Result;
import com.test.microservice.common.result.ResultBuilder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @description: 授权请求API
 * @author: Zhaotianyi
 * @time: 2022/1/19 10:28
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginClient loginClient;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 通过用户名密码进行登录
     *
     * @param user_name 用户名
     * @param Pwd       密码
     */
    @PostMapping("/loginByPwd")
    public Result loginPCByPwd(@RequestParam String user_name, @RequestParam String Pwd) {

        // 认证 Subject：主体
        Subject subject = SecurityUtils.getSubject();
        // 根据用户信息，组成用户令牌token
        UsernamePasswordToken Token = new UsernamePasswordToken(user_name, Pwd, false);
        // 登录操作
        try {
            subject.login(Token);
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(),e);
            return ResultBuilder.failResult("登陆失败,请检查用户名和密码是否正确!");
        }

        LoginUserDTO userDTO = (LoginUserDTO) subject.getPrincipal();
        String token = subject.getSession().getId().toString();
        AuthInfo authInfo = new AuthInfo();
        authInfo.setUserDate(userDTO);
        authInfo.setToken(token);
        return ResultBuilder.successResult(authInfo);
    }

    /**
     * @Author zty
     * @Description: 退出登录
     * @Return
     */
    @RequestMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return ResultBuilder.successResult();
    }

    /**
     * 验证是否授权
     *
     * @param requestURI
     * @param token
     * @return
     */
    @GetMapping("/isPermitted")
    public boolean isPermitted(@RequestParam String requestURI, @RequestParam String token) {
        System.out.println("isPermitted");
        logger.info("进入授权,访问路径：{}", requestURI);
        //方案一，不灵活（对于get请求，不允许在url通过/拼接参数，可以通过?拼接）、不易排查问题
        boolean permitted = SecurityUtils.getSubject().isPermitted(requestURI);
        System.out.println("是否授权：" + permitted);
        return permitted;
        //方案二，不灵活，且无权限时报的异常无法捕获
//        subject.checkPermissions(requestURI);

        //方案三，过于灵活
//      User parse = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
//        List<String> resources = parse.getResources();
//        if("admin".equals(parse.getRole_name())){
//            System.out.println("放行管理员");
//            return true;
//
//        }else
//            if(requestURI.endsWith(".js") || requestURI.endsWith(".css")|| requestURI.endsWith(".gif")){
//            System.out.println("放行资源路径");
//            return true;
//        }else {
//            //开关
//            boolean flag = false;
//            for(String resource:resources){ //判断当前访问的 URI 是否在功能数据中包含
//                if(requestURI.indexOf(resource) !=-1){
//                    flag = true;
//                    System.out.println(requestURI+"--->"+resource);
//                    break;
//                }
//            }
//            return flag;
//        }
    }


}
