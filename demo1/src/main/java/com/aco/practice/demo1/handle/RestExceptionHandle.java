package com.aco.practice.demo1.handle;

import com.aco.practice.basic.util.ApiHttpCode;
import com.aco.practice.basic.util.ApiResponseResult;
import com.aco.practice.demo1.exception.CustomException;
import com.aco.practice.basic.response.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: HaoJianXu
 * @Date: 2020/6/5 21:49
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandle {

    /**
     * 业务异常处理
     * @param request
     * @param customException
     * @return
     */
    @ExceptionHandler({CustomException.class})
    public ApiResponseResult<ErrorInfo> customExceptionHandle(HttpServletRequest request, CustomException customException){
        ErrorInfo body = new ErrorInfo();
        body.setCode(ApiHttpCode.ERROR.getCode());
        body.setMsg(customException.getMessage());
        return new ApiResponseResult<ErrorInfo>(ApiHttpCode.ERROR,body);
    }

    /**
     * 只要抛出该类型异常,则由此方法处理
     * 并由此方法响应出异常状态码及消息
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ApiResponseResult<ErrorInfo> exceptionHandle(HttpServletRequest request, Exception exception){
        log.error(exception.getMessage());
        ErrorInfo body = new ErrorInfo();
        body.setCode(ApiHttpCode.ERROR.getCode());
        body.setMsg(ApiHttpCode.ERROR.getMsg());
        return new ApiResponseResult<ErrorInfo>(ApiHttpCode.ERROR,body);
    }
}
