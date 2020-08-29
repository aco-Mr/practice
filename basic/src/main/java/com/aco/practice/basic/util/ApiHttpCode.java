package com.aco.practice.basic.util;

/**
 * @Author: HaoJianXu
 * @Date: 2020/5/31 15:48
 */
public enum ApiHttpCode {
    // 成功
    SUCCESS(200,"ok"),

    // 错误
    ERROR(500,"服务器内部错误"),

    // 未授权
    UNAUTHORIZED(401,"未授权");

    private int code;

    private String msg;

    ApiHttpCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
