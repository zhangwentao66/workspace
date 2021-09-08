package com.huiminpay.merchant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @AUTHOR: yadong
 * @DATE: 2021/9/6 10:03
 * @DESC:
 */
@ApiModel(value = "MerchantRegisterVo", description = "商户注册信息")
@Data
public class MerchantRegisterVO implements Serializable {
    @ApiModelProperty("商户手机号")
    private String mobile;
    @ApiModelProperty("商户用户名")
    private String username;
    @ApiModelProperty("商户密码")
    private String password;
    @ApiModelProperty("验证码的key")
    private String verifiykey;
    @ApiModelProperty("验证码")
    private String verifiyCode;
}