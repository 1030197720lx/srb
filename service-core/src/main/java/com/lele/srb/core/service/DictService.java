package com.lele.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lele.srb.core.pojo.entity.Dict;
import com.lele.srb.core.pojo.dto.ExcelDictDto;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author lele
 * @since 2022-03-27
 */
public interface DictService extends IService<Dict> {
     void importData(InputStream inputStream);

     List<Dict> listByParentId(Long parentId);

     List<ExcelDictDto> listDictData();
}
