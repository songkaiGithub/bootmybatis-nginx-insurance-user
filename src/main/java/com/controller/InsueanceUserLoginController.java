package com.controller;

import com.alibaba.fastjson.JSON;
import com.po.Dto;
import com.po.Insurance;
import com.service.IInsuranceUserService;
import com.util.*;
import com.util.vo.InsuranceUserVo;
import com.util.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping(value ="/login")
public class InsueanceUserLoginController {
    @Autowired
    private IInsuranceUserService iInsuranceUserService;
    private Jedis jedis = new Jedis("127.0.0.1", 6379);

    /******用户登录的方法*******/
    @RequestMapping(value = "/Userlogin")
    public Dto addUser(HttpServletRequest request, HttpServletResponse response, @RequestBody UserLoginVo userLoginVo) {
        System.out.println("登录方法方法"+userLoginVo.toString());
        try {
            if(userLoginVo!=null&&userLoginVo.getUserCode()!=null&&userLoginVo.getUserCode()!="") {
                Insurance insurance = iInsuranceUserService.show(userLoginVo.getUserCode());
                if (insurance != null) {
                    if(insurance.getActivated()==1){
                        //判断该客户是不是已经登录
                        if(jedis.get(insurance.getUsercode())!=null){
                            String oldtoken=jedis.get(insurance.getUsercode());
                            try {
                                //判断相关参数完成浏览器处理
                                String toke=TokenUtil.replaceToken(request.getHeader("user-agent"),oldtoken,response);
                                return DtoUtil.returnSuccess("登录成功");
                            } catch (TokenValidationFailedException e) {
                                e.printStackTrace();
                                return DtoUtil.returnfail(e.getMessage(),ErrorCode.AUTH_LOGIN_PASSWORD);

                            }
                        }else{
                            String passwd=MD5Util.getMd5(userLoginVo.getUserPassword(),16);
                            if(passwd.equals(insurance.getUserpasspword())){
                                //获取浏览器头信息
                                String requestHade=request.getHeader("user-agent");
                                //生成token
                                String token= TokenUtil.getTokenGenerator(requestHade,insurance);
                                //token存入redis和浏览器的cookie
                                jedis.setex(insurance.getUsercode(),7200,token);
                                //将koken作为key,将对象作为value,判断对象是否登录
                                String user= JSON.toJSONString(insurance);
                                jedis.setex(token,7200,user);
                                Cookie cookie=new Cookie("token",token);
                                cookie.setMaxAge(60*60*2);
                                response.addCookie(cookie);
                                return DtoUtil.returnSuccess("登录成功");
                            }else{
                                return DtoUtil.returnfail("登录失败,密码错误",ErrorCode.AUTH_LOGIN_PASSWORD);
                            }
                        }
                    }else{
                        return DtoUtil.returnfail("登录失败,帐户未激活",ErrorCode.AUTH_LOGIN_ACTIVATED);

                    }
                } else {
                    return DtoUtil.returnfail("登录失败,帐户名不正确,请检查账户名", ErrorCode.AUTH_LOGIN_USERCODE);
                }
            }else {
                return DtoUtil.returnfail("登录失败,帐户名是空的",ErrorCode.AUTH_LOGIN_NULL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnfail("登录异常",ErrorCode.AUTH_LOGIN);
        }
    }
}
