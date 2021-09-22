package com.util;

import com.po.Insurance;
import com.util.vo.InsuranceUserVo;
import redis.clients.jedis.Jedis;

public class PhoneOrEmail {
    private static Jedis jedis=new Jedis("127.0.0.1",6379);
    public static void chazhao(InsuranceUserVo insuranceUserVo){
        if(insuranceUserVo.getUserCode().indexOf("@")!=-1){//邮箱
            System.out.println("邮箱注册");
            String smadk=Emailutil.emailreglist(insuranceUserVo);
            //缓存验证码
            jedis.setex(insuranceUserVo.getUserCode(),120,smadk);
        }else{//手机号
            System.out.println("手机号注册");
            String smadk=Smsutil.testcheck(insuranceUserVo);
            System.out.println("smadk"+smadk);
            jedis.setex(insuranceUserVo.getUserCode(),120,smadk);
        }
    }
}
