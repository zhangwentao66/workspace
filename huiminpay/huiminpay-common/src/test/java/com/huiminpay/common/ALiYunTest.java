package com.huiminpay.common;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ALiYunTest {
    //节点
     static String ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    //桶  存储服务名称id
     static String BUCKET = "huiminpay-zs";
    //域名
    static String DOMAIN = "huiminpay-zs.oss-cn-beijing.aliyuncs.com";
    static String ACCESSKEY_ID = "LTAI5tEqNa5rzdohc1HZQEEc";
    static String ACCESSKEY_SECRET = "XR7LChjc9TdS1OJ5W20EeYYRIz3nBS";

    public static void main(String[] args) throws FileNotFoundException {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ENDPOINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ACCESSKEY_ID;
        String accessKeySecret = ACCESSKEY_SECRET;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        InputStream inputStream = new FileInputStream("D:\\aliyun.png");
        /**
         * 参数一：Bucket名称
         * 参数二： 文件名称   Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
         */
        ossClient.putObject(BUCKET, "aliyun.png", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
