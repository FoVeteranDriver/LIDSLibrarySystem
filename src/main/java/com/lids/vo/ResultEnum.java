package com.lids.vo;

/**
 * @author yaoyou
 * @description 返回码与返回信息的枚举
 */
public enum ResultEnum {

    SUCCESS(0,"成功"),
    FAILED(1,"失败"),
    UNKNOWN_ACCOUNT_ERROR(1,"账号不存在"),
    INCORRECT_CREDENTIALS_ERROR(2,"密码错误"),
    OTHER_LOGIN_ERRROR(4,"请联系管理员"),
    INCORRECT_STATE_ERROR(3,"账号已被锁定"),
    NO_LOGIN(5,"尚未登录"),
    NO_PERMISSION(6,"没有权限"),
    QRCODE_FAILURE(100,"二维码失效，请刷新二维码重新扫描"),
    NO_BINDING(101,"用户与借书证尚未绑定"),
    NOT_SCAN(102,"尚未进行扫码"),
    PARAMS_ERROR(400,"参数有误");

    private int code;
    private String msg;

    ResultEnum(){}

    ResultEnum(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
