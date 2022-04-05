package com.lele.srb.core.mapper;

import com.lele.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lele.srb.core.pojo.entity.dto.ExcelDictDto;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author lele
 * @since 2022-03-27
 */
public interface DictMapper extends BaseMapper<Dict> {

    void insertBatch(List<ExcelDictDto> list);

    List<Dict> listByParentId(Long parentId);
}
