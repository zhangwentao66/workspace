package com.huiminpay.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @AUTHOR: yadong
 * @DATE: 2021/8/17 11:50
 * @DESC:
 */
@SpringBootApplication
@EnableSwagger2  //这里因为之前打不开swagger页面在这里添加一个注解
public class MerchantApplicationBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(MerchantApplicationBootstrap.class,args);
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}