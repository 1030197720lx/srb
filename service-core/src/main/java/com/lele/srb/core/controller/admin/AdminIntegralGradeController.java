package com.lele.srb.core.controller.admin;

import com.lele.common.exception.Assert;
import com.lele.common.result.R;
import com.lele.common.result.ResponseEnum;
import com.lele.srb.core.pojo.entity.IntegralGrade;
import com.lele.srb.core.service.IntegralGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "积分等级管理")
//@CrossOrigin
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {

    @Resource
    private IntegralGradeService integralGradeService;

    @ApiOperation(value = "获取积分等级列表", notes = "获取积分等级列表")
    @GetMapping("/list")
    public R listAll() {
        List<IntegralGrade> list = integralGradeService.list();
        return R.ok().data("list", list).message("获取列表成功");
    }

    @ApiOperation(value = "删除积分等级数据", notes = "根据id删除积分等级数据")
    @DeleteMapping("/remove/{id}")
    public R removeById(
            @ApiParam(value = "数据id", example = "2")
            @PathVariable Long id) {
        boolean result = integralGradeService.removeById(id);
        return result ? R.ok().message("删除成功") : R.error().message("删除失败");
    }

    @ApiOperation("新增积分等级")
    @PostMapping("/save")
    public R save(@ApiParam(value = "积分等级对象", required = true)
                  @RequestBody IntegralGrade integralGrade) {
        Assert.notNull(integralGrade.getBorrowAmount(), ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
        boolean result = integralGradeService.save(integralGrade);
        return result ? R.ok().message("保存成功") : R.error().message("保存失败");
    }

    @ApiOperation("根据id获取数据")
    @GetMapping("/getbyid/{id}")
    public R getById(@ApiParam(value = "数据id", required = true, example = "1")
                     @PathVariable Long id) {
        IntegralGrade integralGrade = integralGradeService.getById(id);
        if (integralGrade != null) {
            return R.ok().data("record", integralGrade);
        } else {
            return R.error().message("未获取到数据");
        }
    }

    @ApiOperation("更新积分等级")
    @PutMapping("/update")
    public R updateById(@ApiParam(value = "积分等级对象", required = true)
                        @RequestBody IntegralGrade integralGrade) {
        boolean result = integralGradeService.updateById(integralGrade);
        return result ? R.ok().message("更新成功") : R.error().message("更新失败");
    }
}
