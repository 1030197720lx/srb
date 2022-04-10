package com.lele.srb.sms.util;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "yuntongxun")
public class SmsProperties implements InitializingBean {
    private String serverIp;
    private String serverPort;
    private String accountSId;
    private String accountToken;
    private String appId;
    private String templateId;

    public static String SERVER_IP;
    public static String SERVER_PORT;
    public static String ACCOUNT_SID;
    public static String ACCOUNT_TOKEN;
    public static String APP_ID;
    public static String TEMPLATE_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        SERVER_IP = serverIp;
        SERVER_PORT = serverPort;
        ACCOUNT_SID = accountSId;
        ACCOUNT_TOKEN = accountToken;
        APP_ID = appId;
        TEMPLATE_ID = templateId;
    }
}
