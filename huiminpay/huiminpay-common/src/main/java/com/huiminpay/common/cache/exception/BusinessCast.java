package com.huiminpay.common.cache.exception;

import com.huiminpay.common.cache.domain.ErrorCode;

/**
 * @AUTHOR: yadong
 * @DATE: 2021/9/6 15:19
 * @DESC:
 */
public class BusinessCast {

    public static void cast(ErrorCode errorCode){
        throw new BusinessException(errorCode);
    }
}
