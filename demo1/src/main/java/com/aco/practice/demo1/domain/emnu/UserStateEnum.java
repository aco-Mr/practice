package com.aco.practice.demo1.domain.emnu;

/**
 * @author XHaoJian
 * @data 2020/7/14 21:52
 */
public enum UserStateEnum {
    NORMAL("正常",0),
    DISABLE("禁用",1);

    private String msg;
    private int code;

    UserStateEnum() {
    }

    UserStateEnum(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
