package com.otcyan.jnet;

/**
 * Created by 01096612 on 2016/6/8.
 * 自定义异常类
 */
public class APIException extends RuntimeException {

//    public  int type ; //异常的类型 是登录还是修改密码
    public  int code ;
    public String message;

    public APIException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
