package com.huiminpay.merchant.controller;

import com.huiminpay.common.cache.util.SecurityUtil;
import com.huiminpay.merchant.api.AppService;
import com.huiminpay.merchant.dto.AppDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "商户平台，应用管理",tags = "商户平台，应用管理 ")
public class AppController {

    @Reference
    private AppService appService;

    /**
     * 商户创建应用
     * @param appDTO
     * @return
     */
    @ApiOperation("商户创建应用")
    //paramType: path 从请求路径后面../../1  query /..？name=zhangsan  body:form表单body体传入
    @ApiImplicitParam(name = "appDTO",value = "应用信息",dataType = "AppDTO"
                        ,paramType = "body",required = true)
    @PostMapping("/my/apps")
    public AppDTO createApp(@RequestBody AppDTO appDTO){
        Long merchantId = SecurityUtil.getMerchantId();
        AppDTO app = appService.createApp(merchantId, appDTO);
        return app;
    }

    /**
     * 根据商户id查询该商户下的应用列表
     * @return
     */
    @ApiOperation("查询商户下的应用列表")
    @GetMapping("/my/apps")
    public List<AppDTO> createApp(){
        Long merchantId = SecurityUtil.getMerchantId();
        return appService.queryAppsByMerchantId(merchantId);
    }

    @ApiOperation("根据appid获取应用的详细信息")
    @ApiImplicitParam(name = "appId",value = "商户应用id",required = true,
                        dataType = "String",paramType = "path")
    @GetMapping(value = "/my/apps/{appId}")
    public AppDTO getApp(@PathVariable String appId){
        return appService.getAppByAppId(appId);
    }
}
