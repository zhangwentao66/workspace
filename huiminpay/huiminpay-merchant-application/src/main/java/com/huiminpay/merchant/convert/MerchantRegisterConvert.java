package com.huiminpay.merchant.convert;

import com.huiminpay.merchant.dto.MerchantDTO;
import com.huiminpay.merchant.vo.MerchantRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @AUTHOR: yadong
 * @DATE: 2021/9/6 11:10
 * @DESC:
 */
@Mapper
public interface MerchantRegisterConvert {

    //创建转换构建器
    MerchantRegisterConvert INSTANCE = Mappers.getMapper(MerchantRegisterConvert.class);

    public MerchantDTO vo2dto(MerchantRegisterVO merchantRegisterVO);

    public MerchantRegisterVO dto2vo(MerchantDTO merchantDTO);
}
