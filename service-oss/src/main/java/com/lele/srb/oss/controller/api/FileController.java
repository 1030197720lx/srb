package com.lele.srb.oss.controller.api;

import com.lele.common.exception.BusinessException;
import com.lele.common.result.R;
import com.lele.common.result.ResponseEnum;
import com.lele.srb.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@Api(tags = "文件管理")
@Slf4j
@RequestMapping("/api/oss/file")
@RestController
//@CrossOrigin
public class FileController {
    @Resource
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(value = "文件",required = true)
            @RequestParam("file") MultipartFile file,
            @ApiParam(value = "模块",required = true)
            @RequestParam("module") String module){

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String url = fileService.upload(inputStream,module,originalFilename);
            return R.ok().message("文件上传成功").data("url",url);

        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR,e);
        }

    }

    @ApiOperation("文件删除")
    @DeleteMapping("/remove")
    public R remove(
            @ApiParam(value = "地址",required = true)
            @RequestParam("url") String url){

            fileService.removeFile(url);
            return R.ok().message("文件删除成功");

    }

}
