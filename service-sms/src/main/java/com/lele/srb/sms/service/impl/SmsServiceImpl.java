package com.lele.srb.sms.service.impl;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.lele.common.exception.BusinessException;
import com.lele.common.result.ResponseEnum;
import com.lele.srb.sms.service.SmsService;
import com.lele.srb.sms.util.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public void send(String mobile, String[] param) {
        //生产环境请求地址：app.cloopen.com
        String serverIp = SmsProperties.SERVER_IP;
        //请求端口
        String serverPort = SmsProperties.SERVER_PORT;
        //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = SmsProperties.ACCOUNT_SID;
        String accountToken = SmsProperties.ACCOUNT_TOKEN;
        //请使用管理控制台中已创建应用的APPID
        String appId = SmsProperties.APP_ID;
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        String to = mobile;
        String templateId= SmsProperties.TEMPLATE_ID;
        String[] datas = param;
        String subAppend="1234";  //可选 扩展码，四位数字 0~9999
        String reqId= "1234";  //可选 第三方自定义消息id，最大支持32位英文数字，同账号下同一自然天内不允许重复
        try {
            HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas,subAppend,reqId);
            if("000000".equals(result.get("statusCode"))){
                //正常返回输出data包体信息（map）
                HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
                Set<String> keySet = data.keySet();
                for(String key:keySet){
                    Object object = data.get(key);
                    System.out.println(key +" = "+object);
                }
            }else{
                throw new BusinessException(result.get("statusMsg").toString(),(Integer) result.get("statusCode"));
            }
        }catch(Exception e){
            log.error("阿里云短信发送sdk调用失败");
            throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR,e);
        }

    }
}
