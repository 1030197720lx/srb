package com.lele.srb.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lele.common.result.R;
import com.lele.srb.core.pojo.entity.UserInfo;
import com.lele.srb.core.pojo.query.UserInfoQuery;
import com.lele.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author lele
 * @since 2022-03-27
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/admin/core/userInfo")
@Slf4j
//@CrossOrigin
public class AdminUserInfoController {

    @Resource
    private UserInfoService userInfoService;


    @ApiOperation("获取用户列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(
            @ApiParam(value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(value = "每页个数", required = true)
            @PathVariable Long limit,
            @ApiParam(value = "查询对象", required = false)
                    UserInfoQuery userInfoQuery) {

        Page<UserInfo> userInfoPage = new Page<>(page,limit);
        IPage<UserInfo> pageModel = userInfoService.listPage(userInfoPage,userInfoQuery);
        return R.ok().data("pageModel",pageModel);
    }

    @ApiOperation("锁定/解锁用户")
    @PutMapping("/lock/{id}/{status}")
    public R lock(
            @ApiParam(value = "id", required = true)
            @PathVariable Long id,
            @ApiParam(value = "状态", required = true)
            @PathVariable Integer status) {

        userInfoService.lock(id,status);
        return R.ok().message(status==1?"解锁成功":"锁定成功");
    }
}

