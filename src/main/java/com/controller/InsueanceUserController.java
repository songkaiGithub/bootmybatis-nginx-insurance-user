package com.controller;

import com.po.Dto;
import com.po.Insurance;
import com.service.IInsuranceUserService;
import com.util.DtoUtil;
import com.util.ErrorCode;
import com.util.MD5Util;
import com.util.PhoneOrEmail;
import com.util.vo.InsuranceUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping(value ="/api")
public class InsueanceUserController {
    @Autowired
    private IInsuranceUserService iInsuranceUserService;
    private Jedis jedis = new Jedis("127.0.0.1", 6379);

    /******用户注册的方法*******/
    @RequestMapping(value = "/registration")
    public Dto addUser(HttpServletRequest request, HttpServletResponse response, @RequestBody InsuranceUserVo insuranceUserVo) {
        try {
            if (insuranceUserVo != null && insuranceUserVo.getUserCode() != null && insuranceUserVo.getUserCode() != "") {
                Insurance insurance1 = iInsuranceUserService.show(insuranceUserVo.getUserCode());
                if (insurance1 != null) {
                    //判断已存在用户是否激活
                    if (insurance1.getActivated() == 1) {
                        return DtoUtil.returnfail("此用户已存在不允许注册已激活", ErrorCode.AUTH_AUTHENTICATION_FAILED);
                    } else {//已存在用户是否激活未激活，
                        PhoneOrEmail.chazhao(insuranceUserVo);
                        return DtoUtil.returnfail("此用户已存在不允许注册未激活", ErrorCode.AUTH_ACTIVATE_AUTHENTICATION);
                    }
                } else {
                    Insurance insurance = new Insurance();
                    //处理前台传入的信息
                    insurance.setUsercode(insuranceUserVo.getUserCode());
                    String passwd = MD5Util.getMd5(insuranceUserVo.getUserPassword(), 16);
                    insurance.setUserpasspword(passwd);
                    insurance.setUserName(insuranceUserVo.getUserName());
                    insurance.setWeChat(insuranceUserVo.getWeChat());
                    insurance.setIdnumber(insuranceUserVo.getIdnumber());
                    //自行处理的信息
                    insurance.setUserType(1);
                    insurance.setCreatedBy(insuranceUserVo.getUserName());
                    insurance.setCreationDate(new Date());
                    insurance.setModifiedBy(insuranceUserVo.getUserName());
                    insurance.setModifyDate(new Date());
                    insurance.setActivated(0);
                    int code = iInsuranceUserService.save(insurance);
                    if (code > 0) {
                        //还用户没有注册过，现在注册成功。未激活发送激活码
                        //判断注册账号是手机号还是很邮箱号
                        PhoneOrEmail.chazhao(insuranceUserVo);
                        return DtoUtil.returnSuccess("恭喜你注册成功");
                    } else {
                        return DtoUtil.returnfail("后台数据有误注册失败", ErrorCode.AUTH_USER_ALREADY_EXISTS);
                    }
                }
            } else {
                return DtoUtil.returnfail("前台传入空数据，注册失败",  ErrorCode.AUTH_AUTHENTICATION_UPDATE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnfail("用户注册异常:" + e.getMessage(), ErrorCode.AUTH_UNKNOWN);
        }
    }

    @RequestMapping(value = "/find")
    public Dto find(@RequestParam("usercode") String usercode) {
        System.out.println("查询方法" + usercode);
        if (usercode != null && usercode != "") {
            Insurance insurance1 = iInsuranceUserService.show(usercode);
            if (insurance1 != null) {
                return DtoUtil.returnfail("帐户名已存在",  ErrorCode. AUTH_AUTHENTICATION_FAILED);
            } else {
                return DtoUtil.returnSuccess();
            }
        } else {
            return DtoUtil.returnfail("帐户名不能为空",  ErrorCode.AUTH_PARAMETER_ERROR);
        }
    }

    /******用户激活的方法********/
    @RequestMapping(value = "activatedUpdate/{activated}")
    public Dto activatedUpdate(@PathVariable String activated, @RequestBody InsuranceUserVo insuranceUserVo) {
        System.out.println("用户激活方法" + activated + "======" + insuranceUserVo.toString());
        //判断该用户是否存在，是否激活
        Insurance isu = iInsuranceUserService.show(insuranceUserVo.getUserCode());
        if (isu != null) {//用户存在
            if (isu.getActivated() == 0) {// 在但未激活
                if (activated.equals(jedis.get(isu.getUsercode()))) {//激活成功
                       int code = iInsuranceUserService.updateactivatied(insuranceUserVo.getUserCode());
                        return DtoUtil.returnSuccess("恭喜你激活成功");
                    } else {
                    if (jedis.get(insuranceUserVo.getUserCode()) != null) {
                        return DtoUtil.returnfail("激活失败,激活码错误,请重新输入验证码，", ErrorCode.AUTH_ACTIVATE_NULL);
                    } else {
                        PhoneOrEmail.chazhao(insuranceUserVo);
                        return DtoUtil.returnfail("激活失败,激活码错误，已重新发送", ErrorCode.AUTH_ACTIVATE_NULL);
                    }
                }
                } else {//返回激活
                    return DtoUtil.returnfail("激活失败用户存在已经激活",  ErrorCode.AUTH_ACTIVATE_FAILED);
                }
            } else {
                return DtoUtil.returnfail("激活失败,该用户不存在",  ErrorCode.AUTH_ACTIVATE_ERRORFAILED);
            }
    }
}
