package com.lids.vo;

/**
 * @author yaoyou
 * @description 返回码与返回信息的枚举
 */
public enum ResultEnum {

    SUCCESS(0,"成功"),
    FAILED(1,"失败");

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
