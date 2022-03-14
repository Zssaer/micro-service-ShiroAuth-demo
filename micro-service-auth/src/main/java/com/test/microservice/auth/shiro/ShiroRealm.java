package com.test.microservice.auth.shiro;

import com.test.microservice.auth.bean.LoginUser;
import com.test.microservice.auth.dto.LoginUserDTO;
import com.test.microservice.auth.service.ShiroService;
import com.test.microservice.common.bean.User;
import com.test.microservice.common.exception.ServiceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @description: Shiro自定义Realm
 * @author: Zhaotianyi
 * @time: 2022/1/19 10:52
 */
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ShiroService shiroService;

    /**
     * 登录认证配置
     *
     * @param authcToken
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        System.out.println("进入Shiro登录认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        // 从数据库获取对应用户名密码的用户
        LoginUser loginUser = null;
        loginUser = shiroService.selecUserByAccount(token.getUsername());

        if (loginUser != null) {
            LoginUserDTO loginUserDTO = new LoginUserDTO();
            BeanUtils.copyProperties(loginUser, loginUserDTO);
            loginUserDTO.setRoles(Arrays.asList(loginUser.getUserType().split("|")));

            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    loginUserDTO,
                    loginUser.getLoginPasswd(),
                    ByteSource.Util.bytes(loginUser.getSalt()),
                    getName()
            );
            singlePortLogin(token);
            return authenticationInfo;
        } else {
            throw new AuthenticationException();
        }
    }

    /**
     * 获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Session session = SecurityUtils.getSubject().getSession();
        if (session.getAttribute("simpleAuthorizationInfo") != null) {
            return (SimpleAuthorizationInfo) session.getAttribute("simpleAuthorizationInfo");
        }

        //获取登录用户名
        LoginUserDTO loginUserDTO = (LoginUserDTO) principalCollection.getPrimaryPrincipal();

        HashMap<String, List<String>> map =
                shiroService.getRolesAndPermissions(loginUserDTO.getLoginName());
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //添加拥有角色列表
        simpleAuthorizationInfo.addRoles(map.get("roles"));
        //添加拥有权限
        simpleAuthorizationInfo.addStringPermissions(map.get("permissions"));
        session.setAttribute("simpleAuthorizationInfo", simpleAuthorizationInfo);
        return simpleAuthorizationInfo;
    }


    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        return super.isPermitted(principals, permission);
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        return super.hasRole(principals, roleIdentifier);
    }

    /**
     * 实现单用户登录
     * 若用户已经登录则挤掉前个登录状态
     *
     * @param token 身份认证令牌
     */
    private void singlePortLogin(UsernamePasswordToken token) {
        // 清除认证缓存信息
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
        //处理session
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        SessionManager sessionManager = (SessionManager) securityManager.getSessionManager();
        //获取当前已登录的用户session列表
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
        LoginUserDTO temp;
        for (Session session : sessions) {

            Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null) {
                continue;
            }

            temp = (LoginUserDTO) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            logger.info("当前已登录的用户user: " + temp.toString());
            //清除该用户以前登录时保存的session，强制退出
            if (token.getUsername().equals(temp.getLoginName())) {
                sessionManager.getSessionDAO().delete(session);
            }
        }
    }
}