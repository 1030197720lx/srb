package com.lele.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lele.srb.core.pojo.entity.Dict;
import com.lele.srb.core.pojo.entity.dto.ExcelDictDto;

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
    public void importData(InputStream inputStream);

    public List<Dict> listByParentId(Long parentId);

    public List<ExcelDictDto> listDictData();
}
