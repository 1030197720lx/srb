package com.lele.srb.sms.controller.api;

import com.lele.common.exception.Assert;
import com.lele.common.result.R;
import com.lele.common.result.ResponseEnum;
import com.lele.common.util.RandomUtils;
import com.lele.common.util.RegexValidateUtils;
import com.lele.srb.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/api/sms")
@Api(tags = "短信管理")
@Slf4j
public class ApiSmsController {

    @Resource
    private SmsService smsService;

    @Resource
    private RedisTemplate redisTemplate;
    @ApiOperation("获取验证码")
    @GetMapping("/send/{mobile}")
    public R send(@ApiParam(value = "手机号",required = true)
                  @PathVariable String mobile){
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile),ResponseEnum.MOBILE_ERROR);
        String code = RandomUtils.getSixBitRandom();
        String[] param = {code,"5"};
        //smsService.send("18756987512",param);

        //将验证码存入redis
        redisTemplate.opsForValue().set("srb:sms:code:"+mobile,code,5, TimeUnit.MINUTES);
        return R.ok().message("短信发送成功");
    }
}
