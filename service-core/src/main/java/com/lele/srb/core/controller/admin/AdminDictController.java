package com.lele.srb.core.controller.admin;


import com.alibaba.excel.EasyExcel;
import com.lele.common.exception.BusinessException;
import com.lele.common.result.R;
import com.lele.common.result.ResponseEnum;
import com.lele.srb.core.pojo.entity.Dict;
import com.lele.srb.core.pojo.dto.ExcelDictDto;
import com.lele.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author lele
 * @since 2022-03-27
 */
@Api(tags = "数据字典管理")
//@CrossOrigin
@RestController
@RequestMapping("/admin/core/dict")
@Slf4j
public class AdminDictController {
    @Resource
    DictService dictService;

    @ApiOperation("Excel数据导入")
    @PostMapping("import")
    public R batchImport(
            @ApiParam(value = "数据字典文件",required = true)
            @RequestParam("file") MultipartFile file){
        try {

            dictService.importData(file.getInputStream());
            return R.ok().message("数据字典批量导入成功");
        } catch (Exception  e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR,e);
        }

    }

    @ApiOperation("Excel数据导出")
    @GetMapping("export")
    public R export(HttpServletResponse response) throws IOException {

            List<ExcelDictDto> ExcelDictDto = dictService.listDictData();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("mydict", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), ExcelDictDto.class).autoCloseStream(Boolean.FALSE).sheet("数据字典")
                    .doWrite(ExcelDictDto);

        return R.ok().message("数据字典导出成功！");
    }

    @ApiOperation("数据字典列表")
    @GetMapping("listByParentId/{parentId}")
    public R listByParentId(
            @ApiParam(value = "父id",required = true)
            @PathVariable Long parentId){
        List<Dict> list = dictService.listByParentId(parentId);
        return R.ok().data("list", list).message("获取列表成功");
    }

}

