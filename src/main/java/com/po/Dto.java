package com.po;
/**
 *  数据传输对象（后端输出对象）
 *  * 通用结果返回前台对象（通过ajax+json+String 返回后台数据部分）*/
public class Dto<T> {
    private String success;//判断系统是否出错做出相应的true或者false的返回，与业务无关，出现的各种异常
    private String errorcode;//该错误码为自定义，一般0表示无错
    private String msg;;//对应的提示信息
    private T data;//返回到前台的具体数据信息，

    public Dto() {
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
