package com.util;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.po.Insurance;
import com.util.vo.InsuranceUserVo;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCVALTYPE;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class Smsutil {
    public static String testcheck(InsuranceUserVo insuranceUserVo){

       //生产环境请求地址：app.cloopen.com
        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = "8aaf07087bc82708017bec87cce3098c";
        String accountToken = "8da013296bbe470eb8a9cdaa4b16f207";
        //请使用管理控制台中已创建应用的APPID
        String appId = "8aaf07087bc82708017bec87cdda0992";
        //初始化SDk
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        //接件人的信心
        String to = "18395451421";
        //发送时使用的模板号
        String templateId= "1";
        //获取系统当前时间
        Date date=new Date();
        long datetime=date.getTime();
        String strdate=datetime+"";
        String smsdk=strdate.substring(strdate.length()-4,strdate.length());
        String[] datas = {smsdk,"2"};
        //HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        //设置内容与时长
        HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        //判断短信是否发送成功
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
      return smsdk;
    }
}
