package com.huiminpay.merchant.api;

import com.huiminpay.common.cache.exception.BusinessException;
import com.huiminpay.merchant.dto.AppDTO;

import java.util.List;

/**
 * 应用管理相关的接口
 */
public interface AppService {
    /**
     * 商户应用创建
     * @param merchantId  商户id
     * @param appDTO      应用appDto对象
     * @return
     */
    AppDTO createApp(Long merchantId,AppDTO appDTO) throws BusinessException;

    /**
     * 通过appId查询app
     * @param id
     * @return
     * @throws BusinessException
     */
    AppDTO getAppByAppId(String id) throws BusinessException;

    /**
     * 查询商户下的所有应用
     * @return
     * @throws BusinessException
     */
    List<AppDTO> queryAppsByMerchantId(Long merchantId) throws BusinessException;
}
