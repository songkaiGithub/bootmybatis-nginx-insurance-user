package com.mapper;

import com.po.Insurance;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IInsuranceUserMapper {
    //注册加入
    public int save(Insurance insurance);
    /**注册前判断该用户是否存在*/
    public Insurance show(String usercode);
    /**修改注册用户的激活状态*/
    public  int updateactivatied(String usercode);
}
