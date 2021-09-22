package com.util.vo;

import java.io.Serializable;

public class UserLoginVo implements Serializable {
    private  String userCode;
    private  String userPassword;

    public UserLoginVo() {
    }

    public UserLoginVo(String userCode, String userPassword) {
        this.userCode = userCode;
        this.userPassword = userPassword;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "userloginVo{" +
                "userCode='" + userCode + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
