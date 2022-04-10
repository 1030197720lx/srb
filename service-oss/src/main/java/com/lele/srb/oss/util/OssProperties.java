package com.lele.srb.oss.util;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun")
public class OssProperties implements InitializingBean {
    private String endpoint;
    private String accessKey;
    private String accessSecret;
    private String bucketName;

    public static String ENDPOINT;
    public static String ACCESS_KEY;
    public static String ACCESS_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        ACCESS_KEY = accessKey;
        ACCESS_SECRET = accessSecret;
        BUCKET_NAME = bucketName;
    }
}
