package com.atguigu.template;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

/**
 * Date:2022/7/1
 * Author:zh
 * Description:
 */
@Component
public class OssUtils {
    @Value("${Oss.endpoint}")
    private String endPoint;
    @Value("${Oss.accessKeyId}")
    private String accessKeyId;
    @Value("${Oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${Oss.bucketName}")
    private String bucketName;

    /**
     * 这是封装的Oss上传文件的方法
     *
     * @param fileInputStream
     * @param filename
     * @return
     */
    public String uploadFile(InputStream fileInputStream, String filename) {
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, filename, fileInputStream);
        ossClient.shutdown();
        return "https://" + bucketName + "." + endPoint + "/" + filename;
    }

    /**
     * 这是封装的Oss删除文件的方法
     * @param filename
     */
    public void deleteFile(String filename) {
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, filename);
        ossClient.shutdown();
    }
}
