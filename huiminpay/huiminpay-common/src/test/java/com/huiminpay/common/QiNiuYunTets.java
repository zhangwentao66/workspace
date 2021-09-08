package com.huiminpay.common;

import com.google.gson.Gson;
import com.huiminpay.common.cache.util.QiniuUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;

import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

public class QiNiuYunTets {
    public static void main(String[] args) throws IOException {
        //上传
//        test();
        //下载
//        testDownLoad();
//        System.out.println(UUID.randomUUID().toString());
        String fileName = "FiqnsIDzNrSNRzsDPenA83TeghiX";
        FileInputStream fileInputStream = new FileInputStream(fileName);
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        String zshuiminpay = QiniuUtil.uploadImg("Ku-IMDdx7DNCzc3jgv1HWdW2XTi3bqgz7Hv-rV1E"
                , "5O5gi2P-O4szIJ8NAq24GgW0iALZ5nxk4a0FImYg"
                , "zshuiminpay",
                bytes);
        System.out.println(zshuiminpay);
    }

    public static void test(){
        //构造一个带指定 Region范围 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        //域名：  qz2f0jds8.hn-bkt.clouddn.com  文件名：  FiqnsIDzNrSNRzsDPenA83TeghiX
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "Ku-IMDdx7DNCzc3jgv1HWdW2XTi3bqgz7Hv-rV1E";
        String secretKey = "5O5gi2P-O4szIJ8NAq24GgW0iALZ5nxk4a0FImYg";
        String bucket = "zshuiminpay";   //这里的桶就是存储空间名称

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        try {
            File file = new File("d://qiniuyun.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            //转换成字节数组
            byte[] uploadBytes = IOUtils.toByteArray(fileInputStream);

            //认证
            Auth auth = Auth.create(accessKey, secretKey);
            //生成令牌
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(uploadBytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                //因为上面没设置key，即文件的名称。所以会返回文件的hash值作为文件的名称
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
    }

    public static void testDownLoad() throws UnsupportedEncodingException {
        //要下载的文件名称
        String fileName = "FiqnsIDzNrSNRzsDPenA83TeghiX";
        //域名
        String domainOfBucket = "http://qz2f0jds8.hn-bkt.clouddn.com";
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

        //验证
        String accessKey = "Ku-IMDdx7DNCzc3jgv1HWdW2XTi3bqgz7Hv-rV1E";
        String secretKey = "5O5gi2P-O4szIJ8NAq24GgW0iALZ5nxk4a0FImYg";
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        System.out.println(finalUrl);

    }
}
