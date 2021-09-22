package com.util;

/**
 * 系统错误编码，根据业务定义如下
 * 用户部分编码以10000开始
 *
 *
 */
public class ErrorCode {

	/*认证模块错误码-start*/
	public final static String AUTH_UNKNOWN="10000";//异常反馈
	public final static String AUTH_USER_ALREADY_EXISTS="10001";//注册失败，后台数据异常
	public final static String AUTH_AUTHENTICATION_FAILED="10002";//注册失败，用户已存在
    public final static String AUTH_AUTHENTICATION_UPDATE="10003";//注册失败，前台传入数据有误
	public final static String AUTH_PARAMETER_ERROR="10004";//用户名密码参数错误，为空
	public final static String AUTH_ACTIVATE_FAILED="10005";//用户注册。用户已注册且已激活
	public final static String AUTH_ACTIVATE_ERRORFAILED="10006";//用用户激活失败。用户不存在
	public final static String AUTH_REPLACEMENT_FAILED="10007";//置换token失败
	public final static String AUTH_TOKEN_INVALID="10008";//token无效
	public static final String AUTH_ILLEGAL_USERCODE = "10009";//非法的用户名
	public final static String AUTH_ACTIVATE_NULL="10010";//用户激活失败。激活码错误
	public final static String AUTH_ACTIVATE_AUTHENTICATION="10011";//用户注册失败。用户存在。激活码未未激活
	public final static String AUTH_ACTIVATE_NULL1="10012";//用户激活失败。激活码错误，请重新输入
	public final static String AUTH_LOGIN="10013";//用户登录失败。账户名错误；
	public final static String AUTH_LOGIN_NULL="10014";//用户登录失败。账户名时空；
	public final static String AUTH_LOGIN_USERCODE="10015";//用户登录失败。账户名不正确；
	public final static String AUTH_LOGIN_ACTIVATED="10016";//用户登录失败。账户名不正确
	public final static String AUTH_LOGIN_PASSWORD="10017";//用户登录失败。账户名不正确
	/*认证模块错误码-end*/
}
