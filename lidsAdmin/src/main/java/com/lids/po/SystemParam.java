package com.lids.po;

public class SystemParam {

    private int id;
    private String paramGroup;
    private String paramName;
    private String paramValue;
    private String paramType;
    private String paramDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParamGroupp() {
        return paramGroup;
    }

    public void setParamGroupp(String paramGroupp) {
        this.paramGroup = paramGroupp;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamDescription() {
        return paramDescription;
    }

    public void setParamDescription(String paramDescription) {
        this.paramDescription = paramDescription;
    }

    public void setInfo(String paramGroup,String paramName,String paramValue,String paramType,String paramDescription){
        this.paramGroup = paramGroup;
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.paramType = paramType;
        this.paramDescription = paramDescription;
    }
}
