package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
                            String passwd=MD5Util.getMd5(userLoginVo.getUserPassword(),16);
                            if(passwd.equals(insurance.getUserpasspword())){
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
                            }else {
                                    //获取浏览器头信息
                                    String requestHade = request.getHeader("user-agent");
                                    //生成token
                                    String token = TokenUtil.getTokenGenerator(requestHade, insurance);
                                    //token存入redis和浏览器的cookie
                                    jedis.setex(insurance.getUsercode(), 7200, token);
                                    //将koken作为key,将对象作为value,判断对象是否登录
                                    String user = JSON.toJSONString(insurance);
                                    jedis.setex(token, 7200, user);
                                    Cookie cookie = new Cookie("token", token);
                                    cookie.setMaxAge(60 * 60 * 2);
                                    response.addCookie(cookie);
                                    return DtoUtil.returnSuccess("登录成功");
                                }
                            }else{
                                return DtoUtil.returnfail("登录失败,密码错误",ErrorCode.AUTH_LOGIN_PASSWORD);
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
    //验证token方法
    @RequestMapping(value = "/UserToken")
    public Dto UserToken(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("验证token方法" +request.getHeader("Cookie"));
        String tokean=request.getHeader("Cookie").substring(6);
        String usertokean=jedis.get(tokean);
        if(usertokean!=null){
            Insurance insurance=JSONArray.parseObject(jedis.get(tokean).toString(),Insurance.class);
            System.out.println("insurance"+insurance);
            return DtoUtil.returnSuccess("欢迎来到主页面",insurance);
        }
        return  DtoUtil.returnfail("该用户已经异地登录，请注意帐户安全",ErrorCode.AUTH_ACTIVATE_NULL);
    }
    //修改密码发送验证码的方法
    @RequestMapping(value = "/yanzhengma1")
    public void yanzheng(HttpServletRequest request, HttpServletResponse response,@RequestBody InsuranceUserVo insuranceUserVo) {
        System.out.println("修改密码发送验证码" + insuranceUserVo.toString());
        PhoneOrEmail.chazhao(insuranceUserVo);
        System.out.println("修改密码发送验证码成功");
    }
    //修改密码发送验证码的方法
    @RequestMapping(value = "updatepasswd/{activatied}")
    public Dto updatepasswd(HttpServletRequest request, HttpServletResponse response,@PathVariable String activatied, @RequestBody InsuranceUserVo insuranceUserVo) {
        System.out.println("修改密码的方法" + activatied);
        if(insuranceUserVo.getUserCode()!=null){
            Insurance insurance1 = iInsuranceUserService.show(insuranceUserVo.getUserCode());
            if(insurance1!=null){
                if(activatied.equals(jedis.get(insuranceUserVo.getUserCode()))){
                    insurance1.setUserpasspword(insuranceUserVo.getUserPassword());
                    insurance1.setModifiedBy("管理员");
                    insurance1.setModifyDate(new Date());
                    int code=iInsuranceUserService.updatepasswd(insurance1);
                    if(code>0){
                        PhoneOrEmail.delete(insuranceUserVo);
                        return DtoUtil.returnSuccess("修改成功");
                    }
                }else{
                    if (jedis.get(insuranceUserVo.getUserCode()) != null) {
                        return DtoUtil.returnfail("激活失败,激活码错误,请重新输入验证码，", ErrorCode.AUTH_ACTIVATE_NULL);
                    } else {
                        PhoneOrEmail.chazhao(insuranceUserVo);
                        return DtoUtil.returnfail("激活失败,激活码错误，已重新发送", ErrorCode.AUTH_ACTIVATE_NULL);
                    }
                }

            }else{
                return DtoUtil.returnfail("帐户名不正确,请输入正确的帐户名",ErrorCode.AUTH_ACTIVATE_ERRORFAILED);
            }

        }else{
            return DtoUtil.returnfail("帐户名为空",ErrorCode.AUTH_ACTIVATE_ERRORFAILED);
        }
        return DtoUtil.returnSuccess("发送成功");
    }
    //退出登录的方法
    @RequestMapping(value = "/loginout")
    public Dto loginout(HttpServletRequest request, HttpServletResponse response,@RequestBody InsuranceUserVo insuranceUserVo) {
        System.out.println("退出登录的方法" + insuranceUserVo.toString());
        if(insuranceUserVo.getUserCode()!=null){
            //判断redis中有token
            String tokean=jedis.get(insuranceUserVo.getUserCode());
            if(tokean!=null){
               TokenUtil.delete(request,response,tokean,insuranceUserVo.getUserCode());
                return DtoUtil.returnSuccess("退出登录,跳转登录页面");
            }else{
                return DtoUtil.returnSuccess("退出登录,跳转登录页面");
            }
        }
        return DtoUtil.returnfail("退出失败",ErrorCode.AUTH_ILLEGAL_USERCODE);
    }

}
