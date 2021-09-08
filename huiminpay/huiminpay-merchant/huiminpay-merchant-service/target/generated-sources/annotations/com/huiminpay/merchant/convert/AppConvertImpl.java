package com.huiminpay.merchant.convert;

import com.huiminpay.merchant.dto.AppDTO;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-08-31T10:25:34+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
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
    public List<AppDTO> listentity2listdto(List<App> list) {
        if ( list == null ) {
            return null;
        }

        List<AppDTO> list1 = new ArrayList<AppDTO>( list.size() );
        for ( App app : list ) {
            list1.add( entity2dto( app ) );
        }

        return list1;
    }

    @Override
    public List<App> listdto2listentity(List<AppDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<App> list1 = new ArrayList<App>( list.size() );
        for ( AppDTO appDTO : list ) {
            list1.add( dto2entity( appDTO ) );
        }

        return list1;
    }
}
