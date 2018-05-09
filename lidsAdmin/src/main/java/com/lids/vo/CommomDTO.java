package com.lids.vo;

/**
 * @author yaoyou
 * @description 统一数据传输类
 */
public class CommomDTO {

    private int code;
    private String msg;
    private Object result;

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

    public Object getResult() {
        return result;
    }

    public void setInfo(ResultEnum resultEnum,Object result){
        code = resultEnum.getCode();
        msg = resultEnum.getMsg();
        this.result = result;
    }

    public void setInfo(ResultEnum resultEnum){
        code = resultEnum.getCode();
        msg = resultEnum.getMsg();
    }
}
