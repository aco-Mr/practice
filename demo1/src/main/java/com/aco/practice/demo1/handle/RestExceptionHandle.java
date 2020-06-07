package com.aco.practice.demo1.handle;

import com.aco.practice.demo1.exception.CustomException;
import com.aco.practice.demo1.response.ErrorInfo;
import com.aco.practice.demo1.util.ApiHttpCode;
import com.aco.practice.demo1.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: HaoJianXu
 * @Date: 2020/6/5 21:49
 */
@RestControllerAdvice
public class RestExceptionHandle {

    /**
     * 业务异常处理
     * @param request
     * @param customException
     * @return
     */
    @ExceptionHandler({CustomException.class})
    public ApiResponse<CustomException> customExceptionHandle(HttpServletRequest request,CustomException customException){
        return new ApiResponse<CustomException>(ApiHttpCode.ERROR,new CustomException(ApiHttpCode.ERROR.getCode(),customException.getMessage()));
    }

    /**
     * 只要抛出该类型异常,则由此方法处理
     * 并由此方法响应出异常状态码及消息
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ApiResponse<ErrorInfo> exceptionHandle(HttpServletRequest request,Exception exception){
        ErrorInfo body = new ErrorInfo();
        body.setCode(ApiHttpCode.ERROR.getCode());
        body.setMsg(exception.getMessage());
        return new ApiResponse<ErrorInfo>(ApiHttpCode.ERROR,body);
    }
}
