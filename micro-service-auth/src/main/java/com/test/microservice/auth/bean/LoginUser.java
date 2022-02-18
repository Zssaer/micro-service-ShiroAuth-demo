package com.test.microservice.auth.bean;


import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;


public class LoginUser {
    private Integer id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String loginPasswd;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 用户状态(1:正常 2:封停)
     */
    private Integer userStatus;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取登录名
     *
     * @return login_name - 登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录名
     *
     * @param loginName 登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * 获取密码
     *
     * @return login_passwd - 密码
     */
    public String getLoginPasswd() {
        return loginPasswd;
    }

    /**
     * 设置密码
     *
     * @param loginPasswd 密码
     */
    public void setLoginPasswd(String loginPasswd) {
        this.loginPasswd = loginPasswd == null ? null : loginPasswd.trim();
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取密码盐值
     *
     * @return salt - 密码盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置密码盐值
     *
     * @param salt 密码盐值
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * 获取用户状态(1:正常 2:封停)
     *
     * @return user_status - 用户状态(1:正常 2:封停)
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * 设置用户状态(1:正常 2:封停)
     *
     * @param userStatus 用户状态(1:正常 2:封停)
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 获取用户手机号
     *
     * @return user_phone - 用户手机号
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * 设置用户手机号
     *
     * @param userPhone 用户手机号
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}