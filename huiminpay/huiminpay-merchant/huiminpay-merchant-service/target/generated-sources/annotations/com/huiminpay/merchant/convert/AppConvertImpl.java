package com.huiminpay.merchant.convert;

import com.huiminpay.merchant.dto.AppDTO;
import com.huiminpay.merchant.entity.App;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-08T22:34:06+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
public class AppConvertImpl implements AppConvert {

    @Override
    public App dto2entity(AppDTO appDTO) {
        if ( appDTO == null ) {
            return null;
        }

        App app = new App();

        app.setId( appDTO.getId() );
        app.setAppId( appDTO.getAppId() );
        app.setAppName( appDTO.getAppName() );
        app.setMerchantId( appDTO.getMerchantId() );
        app.setPublicKey( appDTO.getPublicKey() );
        app.setNotifyUrl( appDTO.getNotifyUrl() );

        return app;
    }

    @Override
    public AppDTO entity2dto(App app) {
        if ( app == null ) {
            return null;
        }

        AppDTO appDTO = new AppDTO();

        appDTO.setId( app.getId() );
        appDTO.setAppId( app.getAppId() );
        appDTO.setAppName( app.getAppName() );
        appDTO.setMerchantId( app.getMerchantId() );
        appDTO.setPublicKey( app.getPublicKey() );
        appDTO.setNotifyUrl( app.getNotifyUrl() );

        return appDTO;
    }

    @Override
    public List<AppDTO> listentity2listDto(List<App> apps) {
        if ( apps == null ) {
            return null;
        }

        List<AppDTO> list = new ArrayList<AppDTO>( apps.size() );
        for ( App app : apps ) {
            list.add( entity2dto( app ) );
        }

        return list;
    }
}
