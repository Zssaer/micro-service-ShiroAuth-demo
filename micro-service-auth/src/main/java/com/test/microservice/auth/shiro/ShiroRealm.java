package com.test.microservice.auth.shiro;

import com.test.microservice.auth.service.ShiroService;
import com.test.microservice.common.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/1/19 10:52
 */
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ShiroService shiroService;


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken){
        System.out.println("进入shiroRealm");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 从数据库获取对应用户名密码的用户
        System.out.println(token.toString());
        System.out.println(token.getUsername());
        User user = shiroService.selecUserByAccount(token.getUsername());
        if (user != null) {
//            user.setResources(shiroService.getResourceUrlByUserName(user.getUser_name()));
            this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());

            //单用户登录
            //处理session
            DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
            SessionManager sessionManager = (SessionManager) securityManager.getSessionManager();
            //获取当前已登录的用户session列表
            Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
            User temp;
            for(Session session : sessions) {

                Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if (attribute == null) {
                    continue;
                }

                temp = (User) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
                logger.info("当前已登录的用户user: "+temp.toString());
                //清除该用户以前登录时保存的session，强制退出
                if (token.getUsername().equals(temp.getUserName())) {
                    sessionManager.getSessionDAO().delete(session);
                }
            }

            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user, //用户
                    user.getPassword(), //密码
                    getName()  //realm name
            );
            return authenticationInfo;
        }
        throw new AuthenticationException();
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
//        // TODO Auto-generated method stub
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取登录时查询到的用户对象
        User user = (User)arg0.getPrimaryPrincipal();
        //把用户的所有权限添加到info中
        logger.info("当前正在授权的用户信息: "+user.toString());
//        List<String> resources = user.getResources();
//        if(resources!=null&&resources.size()>0){
//            for(String URL : user.getResources()){
//                info.addStringPermission(URL);
//            }
//        }
        //后台只需要做登录认证即可，权限部分由前端动态遍历授权页面即可

        return info;
    }

    /**
     * 管理员授权
     * @param principals
     * @param permission
     * @return
     */
    @Override
    public  boolean isPermitted(PrincipalCollection principals, String permission){
        User user = (User)principals.getPrimaryPrincipal();
        return isAdmin(user)||super.isPermitted(principals,permission);
    }
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        User user = (User)principals.getPrimaryPrincipal();
        return isAdmin(user)||super.hasRole(principals,roleIdentifier);
    }

    public boolean isAdmin( User user) {
        return "admin".equals(user.getRole());
    }
}