package com.zhuiquu.temexportexcel.controller;

/**
 * Created by Administrator on 2020/5/3 1:47
 */
public enum SexEnum {
    man("1","男"),
    woman("0","女")
    ;

    SexEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
