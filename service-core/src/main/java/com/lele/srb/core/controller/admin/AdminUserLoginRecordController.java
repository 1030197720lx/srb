package com.lele.srb.core.controller.admin;


import com.lele.common.result.R;
import com.lele.srb.core.pojo.entity.UserLoginRecord;
import com.lele.srb.core.service.UserLoginRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户登录记录表 前端控制器
 * </p>
 *
 * @author lele
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/admin/core/userLoginRecord")
@Api(tags = "会员登录日志接口")
@Slf4j
//@CrossOrigin
public class AdminUserLoginRecordController {

    @Resource
    private UserLoginRecordService userLoginRecordService;

    @ApiModelProperty("获取会员登录日志")
    @GetMapping("/listTop50/{userId}")
    public R listTop50(
            @ApiParam(value="用户ID",required = true)
            @PathVariable Long userId){
        List<UserLoginRecord> loginRecordList = userLoginRecordService.listTop50(userId);
        return R.ok().data("list",loginRecordList);
    }
}

