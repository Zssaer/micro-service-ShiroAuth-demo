package com.test.microservice.login.api.dto;


/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2021/11/15 15:18
 */
public class LoginUserDTO {
    private Integer id;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 用户状态(1:正常 2:封停)
     */
    private Integer userStatus;

    /**
     * 用户类型
     */
    private Integer userType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
