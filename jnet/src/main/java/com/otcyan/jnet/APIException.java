package com.otcyan.jnet;

/**
 * Created by 01096612 on 2016/6/8.
 * 自定义异常类
 */
public class APIException extends RuntimeException {

    public  int errorCode;
    public String errorMsg;

    public APIException(int code, String message) {
        super(message);
        this.errorCode = code;
        this.errorMsg = message;
    }

//    @IntDef(value = {TYPE_HTTP_ERROR , TYPE_INTERFACE_ERROR})
//    @Target(ElementType.PARAMETER)
//    @Retention(RetentionPolicy.SOURCE)
//    public @interface TypeFlags{
//        int TYPE_HTTP_ERROR = 0;
//        int TYPE_INTERFACE_ERROR = 1;
//    }
}
