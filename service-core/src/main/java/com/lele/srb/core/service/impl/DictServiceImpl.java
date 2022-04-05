package com.lele.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.lele.srb.core.listener.ExcelDictDTOListener;
import com.lele.srb.core.mapper.DictMapper;
import com.lele.srb.core.pojo.entity.dto.ExcelDictDto;
import com.lele.srb.core.service.DictService;
import com.lele.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author lele
 * @since 2022-03-27
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Resource
    private DictMapper dictMapper;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDto.class,new ExcelDictDTOListener(dictMapper)).sheet().doRead();
        log.info("Excel导入成功");
    }

    @Override
    public List<Dict> listByParentId(Long parentId) {
        return dictMapper.listByParentId(parentId);
    }

    @Override
    public List<ExcelDictDto> listDictData() {
        List<Dict> dictList = dictMapper.selectList(null);
        List<ExcelDictDto> excelDictDtoList = new ArrayList<>(dictList.size());
        dictList.forEach(
                dict -> {
                    ExcelDictDto excelDictDto = new ExcelDictDto();
                    BeanUtils.copyProperties(dict,excelDictDto);
                    excelDictDtoList.add(excelDictDto);
                }
        );
        return excelDictDtoList;
    }


}
