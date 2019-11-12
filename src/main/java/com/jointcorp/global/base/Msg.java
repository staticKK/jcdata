package com.jointcorp.global.base;

/**
 * 返回状态
 *
 * @author Xiao
 * @create 2017-05-17 14:07
 **/
public enum Msg {

    SUCCESS(1,"成功"),
    ERROR(2,"失败"),

    DATA_NULL(2200,"没有对应的数据"),

    PARAM_ERR(1119,"参数不正确"),
    SIGN_ERROR(1031,"Sign未通过验证"),
    REQUEST_ERROR(1032,"请求已经过期"),
    TOKEN_ILLEGAL(1500,"token已过期");


    private int code;
    private String info;

    Msg(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
