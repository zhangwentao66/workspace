package com.huiminpay.merchant.service;

import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.exception.BusinessCast;
import com.huiminpay.common.cache.util.PhoneUtil;
import com.huiminpay.common.cache.util.QiniuUtil;
import com.huiminpay.merchant.api.MerchantServiceApi;
import com.huiminpay.merchant.convert.MerchantRegisterConvert;
import com.huiminpay.merchant.dto.MerchantDTO;
import com.huiminpay.merchant.vo.MerchantRegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;


/**
 * @AUTHOR: yadong
 * @DATE: 2021/8/19 11:11
 * @DESC:
 */
@Service
@Slf4j
public class MerchantService {

    @Autowired
    private RestTemplate restTemplate;

    @Reference
    private MerchantServiceApi merchantServiceApi;

    //http://localhost:56085/sailing/
    @Value("${sailing.url}")
    private  String url;

    @Value("${yun.qiniu.accessKey}")
    private String accessKeyId;
    @Value("${yun.qiniu.secretKey}")
    private String secretKeySecret;
    @Value("${yun.qiniu.bucket}")
    private String bucket;
    @Value("${yun.qiniu.domain}")
    private String domain;

    public MerchantRegisterVO register(MerchantRegisterVO merchantRegisterVO){

        if(merchantRegisterVO == null){
            BusinessCast.cast(CommonErrorCode.E_100101);
        }
        if(StringUtils.isEmpty(merchantRegisterVO.getMobile())){
            BusinessCast.cast(CommonErrorCode.E_100112);
        }
        if(!PhoneUtil.isMatches(merchantRegisterVO.getMobile())){
            BusinessCast.cast(CommonErrorCode.E_100109);
        }
        if(StringUtils.isEmpty(merchantRegisterVO.getUsername())){
            BusinessCast.cast(CommonErrorCode.E_100110);
        }
        if(StringUtils.isEmpty(merchantRegisterVO.getPassword())){
            BusinessCast.cast(CommonErrorCode.E_100111);
        }

        //远程调用sailing服务校验验证码
        this.checkMsmCode(merchantRegisterVO.getVerifiykey(),merchantRegisterVO.getVerifiyCode());

       /* MerchantDTO merchantDTO = new MerchantDTO();
        BeanUtils.copyProperties(merchantRegisterVO,merchantDTO);*/

        MerchantDTO merchantDTO = MerchantRegisterConvert.INSTANCE.vo2dto(merchantRegisterVO);
        //验证码校验通过，远程调用service添加商户信息
        merchantServiceApi.registerMerchant(merchantDTO);

        return merchantRegisterVO;
    }
    ////远程调用sailing服务校验验证码
    private void checkMsmCode(String key,String code){
        // http://localhost:56085/sailing/verify?name=sms&verificationCode=qq&verificationKey=22
        String msmUrl = url+"verify?name=sms&verificationCode="+code+"&verificationKey="+key;
        try {
            ResponseEntity<Map> responseEntity = restTemplate.exchange(msmUrl,
                    HttpMethod.POST, HttpEntity.EMPTY, Map.class);
            if (responseEntity == null){
                log.error("校验验证码出错");
                BusinessCast.cast(CommonErrorCode.E_100102);
                //throw new BusinessException(CommonErrorCode.E_100102);
               // throw new RuntimeException("校验验证码出错");

            }
            Map entityBody = responseEntity.getBody();
            if(entityBody == null || entityBody.get("result")==null){
                log.error("校验验证码出错");
                BusinessCast.cast(CommonErrorCode.E_100102);
                //throw new BusinessException(CommonErrorCode.E_100102);
               // throw new RuntimeException("校验验证码出错");
            }
            boolean result = (boolean)entityBody.get("result");
            if(!result){
                log.error("校验验证码出错");
                BusinessCast.cast(CommonErrorCode.E_100102);
//                throw new BusinessException(CommonErrorCode.E_100102);
               // throw new RuntimeException("校验验证码出错");
            }
        }catch (Exception e){
            log.error("校验验证码出错:{}",e.getMessage());
            BusinessCast.cast(CommonErrorCode.E_100102);
//            throw new BusinessException(CommonErrorCode.E_100102);
            //throw new RuntimeException("校验验证码出错");
        }


    }



    public MerchantDTO queryMerchantById(Long merchantId) {
        MerchantDTO merchantDTO = merchantServiceApi.findMerchantById(merchantId);
        return merchantDTO;
    }

    public String upload(MultipartFile multipartFile) {

        //必要字段判断
        if (multipartFile == null){
            BusinessCast.cast(CommonErrorCode.E_110006);
        }

        try {
            //获取上传文件名称
            String originalFilename = multipartFile.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() +
                    originalFilename.substring(originalFilename.lastIndexOf("."));  //111.jpg
            byte[] bytes = multipartFile.getBytes();
        //文件上传
        QiniuUtil.upload(accessKeyId,secretKeySecret,bucket,fileName,bytes);
        return domain + fileName;
        } catch (IOException e) {
            log.error("上传文件一场{}" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
