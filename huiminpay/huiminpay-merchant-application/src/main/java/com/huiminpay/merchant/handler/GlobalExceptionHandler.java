package com.huiminpay.merchant.handler;

import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.domain.RestErrorResponse;
import com.huiminpay.common.cache.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @AUTHOR: yadong
 * @DATE: 2021/9/6 14:59
 * @DESC:全局异常处理类
 */
@ControllerAdvice  //该异常对Controller切面的增强
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) //指定该全局异常处理类的这个方法来处理异常
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse processException(Exception exception){
        //判断异常是否为自定义异常
        if (exception instanceof BusinessException){
            BusinessException be = (BusinessException)exception;

            RestErrorResponse response = new RestErrorResponse(be.getErrorCode().getCode(),
                    be.getErrorCode().getDesc());
            return response;
        }

        return new RestErrorResponse(CommonErrorCode.UNKNOWN.getCode(),CommonErrorCode.UNKNOWN.getDesc());
    }
}
