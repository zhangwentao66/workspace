package com.huiminpay.common.cache.exception;

import com.huiminpay.common.cache.domain.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @AUTHOR: yadong
 * @DATE: 2021/9/6 14:54
 * @DESC: 自定义异常类
 */
@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException{

    private ErrorCode errorCode;

}
