package com.lele.srb.core.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 借款人上传资源表 前端控制器
 * </p>
 *
 * @author lele
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/api/core/borrowerAttach")
public class BorrowerAttachController {

    @GetMapping("/test")
    public void test(){
        return;
    }
}

