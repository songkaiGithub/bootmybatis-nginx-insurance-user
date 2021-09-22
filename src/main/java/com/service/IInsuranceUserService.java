package com.service;

import com.po.Insurance;

public interface IInsuranceUserService {
    /**用户注册的添加*/
    public int save(Insurance insurance);
    /**判断用户注册的是否存在*/
    public Insurance show(String insurance);
    /**修改注册用户的激活状态*/
    public  int updateactivatied(String usercode);
}
