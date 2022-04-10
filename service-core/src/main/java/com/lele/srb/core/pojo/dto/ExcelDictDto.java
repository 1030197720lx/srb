package com.lele.srb.core.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelDictDto {
    @ExcelProperty(value = "id")
    private Long id;

    @ExcelProperty(value = "上级id")
    private Long parentId;

    @ExcelProperty(value = "名称")
    private String name;

    @ExcelProperty(value = "值")
    private Integer value;

    @ExcelProperty(value = "编码")
    private String dictCode;

}
