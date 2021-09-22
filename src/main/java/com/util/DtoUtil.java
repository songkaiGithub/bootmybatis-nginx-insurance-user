package com.util;

import com.po.Dto;

import java.util.function.DoubleToIntFunction;

/**用于返回的工具类Dto
 *提供各种情况返回结果的类型（成功，失败，带参，不带参）
 *
 * */

public class DtoUtil {
    //表示成功
    private static String success="true";
    //表示失败
    private  static String fail="false";
    //成功和失败的标识
    private static String errorCode="0";
    /**
     * 返回成功，不带提示信心和数据*/
    public static Dto returnSuccess(){
        Dto dto=new Dto();
        dto.setSuccess(success);
        dto.setErrorcode(errorCode);
        return dto;
    }
    /**
     *返回成功，带提示信息，不带返回数据
     * */
    public static Dto returnSuccess(String message){
        Dto dto=new Dto();
        dto.setSuccess(success);
        dto.setMsg(message);
        dto.setErrorcode(errorCode);
        return dto;
    }
    /**
     *返回成功，带提示信息，不带返回数据
     * */
    public static Dto returnSuccess(String message,Object data){
        Dto dto=new Dto();
        dto.setSuccess(success);
        dto.setMsg(message);
        dto.setData(data);
        dto.setErrorcode(errorCode);
        return dto;
    }
    /**
     *返回成功，b不带提示信息，带返回数据
     * */
    public static Dto returnSuccess(Object data){
        Dto dto=new Dto();
        dto.setSuccess(success);
        dto.setData(data);
        dto.setErrorcode(errorCode);
        return dto;
    }
    /**
     *返回失败，带提示信息，和错误编码
     * */
    public static Dto returnfail(String message,String errorCode){
        Dto dto=new Dto();
        dto.setSuccess(fail);
       dto.setMsg(message);
        dto.setErrorcode(errorCode);
        return dto;
    }
}
