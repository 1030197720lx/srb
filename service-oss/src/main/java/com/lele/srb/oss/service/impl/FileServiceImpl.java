package com.lele.srb.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.lele.srb.oss.service.FileService;
import com.lele.srb.oss.util.OssProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(InputStream inputStream, String module, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(OssProperties.ENDPOINT,
                OssProperties.ACCESS_KEY
                , OssProperties.ACCESS_SECRET);
        //判断BUCKET_NAME是否存在
        if(!ossClient.doesBucketExist(OssProperties.BUCKET_NAME)){
            ossClient.createBucket(OssProperties.BUCKET_NAME);
            ossClient.setBucketAcl(OssProperties.BUCKET_NAME, CannedAccessControlList.PublicRead);
        }

            //文件目录结构“module/year/month/day/uuid.jpg”
            //构建日期路径
            String timeFolder = new DateTime().toString("/yyyy/MM/dd/");
            fileName = UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
            String path = module+timeFolder+fileName;

            // 创建PutObject请求。
            ossClient.putObject(OssProperties.BUCKET_NAME, path, inputStream);

                ossClient.shutdown();


        return "https://" +OssProperties.BUCKET_NAME+"."+OssProperties.ENDPOINT+"/"+path;
    }

    @Override
    public void removeFile(String url) {
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(OssProperties.ENDPOINT,
                OssProperties.ACCESS_KEY
                , OssProperties.ACCESS_SECRET);

        String host = "https://" +OssProperties.BUCKET_NAME+"."+OssProperties.ENDPOINT+"/";

        String objectName = url.substring(host.length());

        ossClient.deleteObject(OssProperties.BUCKET_NAME,objectName);

        ossClient.shutdown();
    }
}
