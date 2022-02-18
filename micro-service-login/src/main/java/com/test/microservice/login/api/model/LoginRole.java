package com.test.microservice.login.api.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "login_role")
public class LoginRole {
    @Id
    private Integer id;

    /**
     * 登录名
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 密码
     */
    @Column(name = "is_sys")
    private String isSys;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getIsSys() {
        return isSys;
    }

    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }
}
