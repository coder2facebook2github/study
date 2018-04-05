package com.spring.boot.study.model.master.vo;

import java.io.Serializable;

public class LoginVo implements Serializable {

    private static final long serialVersionUID = 8265480047074595304L;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
