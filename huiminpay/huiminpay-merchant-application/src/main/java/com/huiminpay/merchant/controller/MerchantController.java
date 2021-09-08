package com.huiminpay.merchant.controller;


import com.huiminpay.common.cache.util.SecurityUtil;
import com.huiminpay.merchant.api.MerchantServiceApi;
import com.huiminpay.merchant.dto.MerchantDTO;
import com.huiminpay.merchant.service.MerchantService;
import com.huiminpay.merchant.service.SmsService;
import io.swagger.annotations.*;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.huiminpay.merchant.vo.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @AUTHOR: yadong
 * @DATE: 2021/8/17 15:45
 * @DESC:
 */
@RestController
@Api("商户管理系统controller接口，包含商户注册，资质申请，获取验证码等。。。" )
public class MerchantController {

    @Reference
    private MerchantServiceApi merchantServiceApi;

    @Autowired
    private SmsService smsService;

    @Autowired
    private MerchantService merchantService;

    @GetMapping("find/{id}")
    public MerchantDTO findMerchantById(@PathVariable("id") Long id){
       return merchantServiceApi.findMerchantById(id);
    }

    /**
     * 给
     * @param mobile
     * @return
     */
    @GetMapping("sms")
    @ApiImplicitParam(name = "phone" ,value = "手机号",dataType = "String",required = true,paramType="query")
    public String getSendSms(@RequestParam("phone") String mobile){
        String msmCodeKey = smsService.sendMsmCode(mobile);
        return msmCodeKey;
    }

    /**
     * 商户注册
     * @param merchantRegisterVO
     * @return
     */
    @ApiOperation("商户注册")
    @ApiImplicitParam(name = "merchantRegisterVO" ,value = "商户注册信息",dataType = "MerchantRegisterVO",required = true,paramType="body")
    @PostMapping("/merchants/register")
    public MerchantRegisterVO registerMerchant(@RequestBody MerchantRegisterVO merchantRegisterVO){
        MerchantRegisterVO registerVO = merchantService.register(merchantRegisterVO);
        return registerVO;
    }

    //这里要用@ApiParam  用 @ApiImplicitParam前端显示的是普通输入框
    @ApiOperation("商户文件上传")
    @ApiParam(name = "multipartFile" ,value = "上传的文件的信息",type = "MultipartFile")
    @PostMapping("/upload")
    public String upLoadImg(MultipartFile multipartFile){
        return merchantService.upload(multipartFile);
    }


    @ApiOperation("商户资质申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantDTO" ,value = "商户资质申请的信息",dataType = "MerchantDTO")
    })
    @PostMapping("/my/merchants/save")
    public MerchantDTO applyMerchant(@RequestBody MerchantDTO merchantDTO){   //@RequestBody加上注解 前端以json字符串传过来 按指定的java类型转换
        //这里的merchantDto是从登陆成功后的令牌中拿到的

        Long merchantId = SecurityUtil.getMerchantId();
        return merchantServiceApi.applyMerchant(merchantId,merchantDTO);
    }
}
