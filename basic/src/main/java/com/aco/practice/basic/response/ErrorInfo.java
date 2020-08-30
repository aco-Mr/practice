package com.aco.practice.basic.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: HaoJianXu
 * @Date: 2020/6/5 22:00
 */
@Data
public class ErrorInfo implements Serializable {

    @ApiModelProperty(value = "返回错误码")
    private Integer code;

    @ApiModelProperty(value = "返回错误信息")
    private String msg;
}
