package com.lids.vo;

/**
 * @author yaoyou
 * @description 统一数据传输类
 */
public class CommomDTO {

    private int code;
    private String msg;
    private Object object;

    public CommomDTO(){
    }

    public CommomDTO(ResultEnum resultEnum){
        code = resultEnum.getCode();
        msg = resultEnum.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getObject() {
        return object;
    }

    public void setInfo(ResultEnum resultEnum,Object object){
        code = resultEnum.getCode();
        msg = resultEnum.getMsg();
        this.object = object;
    }

    public void setInfo(ResultEnum resultEnum){
        code = resultEnum.getCode();
        msg = resultEnum.getMsg();
    }
}
