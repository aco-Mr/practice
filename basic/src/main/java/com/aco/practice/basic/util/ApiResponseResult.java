package com.aco.practice.basic.util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: HaoJianXu
 * @Date: 2020/5/31 15:52
 */
@Api(value = "全局返回对象")
public class ApiResponseResult<T> {
    private static final Object RESULT_OBJECT=new Object();

    @ApiModelProperty(value = "编码", name = "code", dataType = "int", example = "200")
    private Integer code;

    @ApiModelProperty(value = "提示", name = "msg", dataType = "string", example = "success")
    private String msg;

    @ApiModelProperty(value = "内容", name = "data", dataType = "t")
    private T data;

    public ApiResponseResult() {
    }

    public ApiResponseResult(ApiHttpCode code, T data) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }

    public ApiResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ApiResponseResult<Object> ok() {
        return ApiResponseResult.ok(ApiHttpCode.SUCCESS, RESULT_OBJECT);
    }

    public static <T> ApiResponseResult<T> ok(T data) {
        return ApiResponseResult.ok(ApiHttpCode.SUCCESS, data);
    }

    public static <T> ApiResponseResult<T> ok(ApiHttpCode code, T data) {
        return ApiResponseResult.ok(code.getCode(), code.getMsg(), data);
    }

    public static <T> ApiResponseResult<T> ok(Integer code, String msg, T data) {
        return new ApiResponseResult(code, msg, data);
    }

    public static ApiResponseResult<Object> error() {
        return ApiResponseResult.error(ApiHttpCode.ERROR, new Object());
    }

    public static <T> ApiResponseResult<T> error(T data) {
        return ApiResponseResult.error(ApiHttpCode.ERROR, data);
    }

    public static <T> ApiResponseResult<T> error(ApiHttpCode code) {
        return ApiResponseResult.error(code.getCode(),code.getMsg(),null);
    }

    public static <T> ApiResponseResult<T> error(ApiHttpCode code, T data) {
        return ApiResponseResult.error(code.getCode(), code.getMsg(), data);
    }

    public static <T> ApiResponseResult<T> error(Integer code, String msg) {
        return ApiResponseResult.error(code, msg, null);
    }

    public static <T> ApiResponseResult<T> error(Integer code, String msg, T data) {
        return new ApiResponseResult(code, msg, data);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
