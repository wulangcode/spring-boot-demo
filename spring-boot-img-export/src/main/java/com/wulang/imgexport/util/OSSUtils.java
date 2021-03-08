package com.wulang.imgexport.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @author wulang
 * @create 2021/3/7/21:39
 */
public class OSSUtils {

    public static OSSClient getOSSClient() {
        String ossEndPoint = ConfigUtils.getProperty("aliyun.oss.endpoint");
        String ossAccessKeyId = ConfigUtils.getProperty("aliyun.oss.accessKeyId");
        String ossAccessKeySecret = ConfigUtils.getProperty("aliyun.oss.accessKeySecret");
        // 创建OSSClient实例
        return new OSSClient(ossEndPoint, ossAccessKeyId, ossAccessKeySecret);
    }

    /**
     * 字节流
     *
     * @param bytes 图片字节流
     * @return
     */
    public static String byteUploadFile(byte[] bytes) {
        // 创建OSSClient实例
        OSSClient ossClient = getOSSClient();
        String ossBucket = ConfigUtils.getProperty("aliyun.oss.bucket");
        String path = "ali" + "/";

        StringBuilder filePath = new StringBuilder();
        filePath.append(path).append(UUID.randomUUID()).append(".jpg");
        if (bytes != null) {
            try {
                ossClient.putObject(ossBucket, filePath.toString(), new ByteArrayInputStream(bytes));
                ossClient.shutdown();

                // 设置URL过期时间为1小时。
                Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
                // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
                URL url = ossClient.generatePresignedUrl(ossBucket, filePath.toString(), expiration);
                ossClient.shutdown();
                String fileUrl = url.toString();
                if (fileUrl.contains("?")) {
                    fileUrl = fileUrl.substring(0, fileUrl.indexOf("?"));
                }
                return fileUrl;
            } catch (OSSException | ClientException e) {
                e.printStackTrace();
            } finally {
                ossClient.shutdown();
            }
        }

        // 关闭OSSClient。
        ossClient.shutdown();
        return null;
    }
}
