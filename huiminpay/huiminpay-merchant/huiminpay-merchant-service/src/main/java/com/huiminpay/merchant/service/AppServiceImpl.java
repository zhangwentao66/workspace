package com.huiminpay.merchant.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.exception.BusinessException;
import com.huiminpay.merchant.api.AppService;
import com.huiminpay.merchant.convert.AppConvert;
import com.huiminpay.merchant.dto.AppDTO;
import com.huiminpay.merchant.entity.App;
import com.huiminpay.merchant.entity.Merchant;
import com.huiminpay.merchant.mapper.AppMapper;
import com.huiminpay.merchant.mapper.MerchantMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@org.apache.dubbo.config.annotation.Service  //dubbo提供的 内部远程调用使用
public class AppServiceImpl implements AppService {

    @Autowired
    MerchantMapper merchantMapper;

    @Autowired
    AppMapper appMapper;

    @Override
    public AppDTO createApp(Long merchantId, AppDTO appDTO) throws BusinessException{
        //根据id拿到merchant商户对象信息
        Merchant merchant = merchantMapper.selectById(merchantId);
        /**
         * 必要字段非空判断
         */
        // merchant商户判断
        if (merchant == null){
            throw new BusinessException(CommonErrorCode.E_110006);
        }
        if (!"2".equals(merchant.getAuditStatus())){
            //审核状态 0-未申请,1-已申请待审核,2-审核通过,3-审核拒绝
            throw new BusinessException(CommonErrorCode.E_200003);
        }
        if (isExitAppName(appDTO.getAppName(),merchantId)){
            throw new BusinessException(CommonErrorCode.E_200004);
        }

        appDTO.setAppId(UUID.randomUUID().toString());
        appDTO.setMerchantId(merchantId);
        App app = AppConvert.INSTANCE.dto2entity(appDTO);
        appMapper.insert(app);
        return AppConvert.INSTANCE.entity2dto(app);
    }
    /**
     * 校验应用名称是否已存在
     * @return
     */
    public Boolean isExitAppName(String appName,Long merchantId){
        //这里使用mybatis-plus 根据条件查询数据条数  这里好像找不到appName
//        Integer count = appMapper.selectCount(new QueryWrapper<App>()
//                .eq("appName", appName)
//                .eq("merchantId",merchantId));
//        return count > 0;
        Integer count = appMapper.selectCount(new QueryWrapper<App>
                ().lambda().eq(App::getAppName, appName));
        return count.intValue() > 0;
    }

    /**
     * 通过appId查询app
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public AppDTO getAppByAppId(String id) throws BusinessException {
        App app = appMapper.selectOne(
                new QueryWrapper<App>().lambda().eq(App::getAppId, id));
        return AppConvert.INSTANCE.entity2dto(app);
    }

    /**
     * 查询商户下的所有应用
     * @return
     * @throws BusinessException
     */
    @Override
    public List<AppDTO> queryAppsByMerchantId(Long merchantId) throws BusinessException {
        List<App> apps = appMapper.selectList(new QueryWrapper<App>().lambda()
                .eq(App::getMerchantId, merchantId));
        List<AppDTO> appDTOS = AppConvert.INSTANCE.listentity2listDto(apps);
        return appDTOS;
    }

}
