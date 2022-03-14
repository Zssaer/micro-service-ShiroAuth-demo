package com.test.microservice.login.api.controller.requst;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/3/14 16:04
 */
public class RegisterReq {
    private String loginName;
    private String userName;
    private String userPhone;
    private String passwd;
    private String rePasswd;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRePasswd() {
        return rePasswd;
    }

    public void setRePasswd(String rePasswd) {
        this.rePasswd = rePasswd;
    }
}
