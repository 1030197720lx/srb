package com.lele.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.lele.srb.core.listener.ExcelDictDTOListener;
import com.lele.srb.core.mapper.DictMapper;
import com.lele.srb.core.pojo.dto.ExcelDictDto;
import com.lele.srb.core.service.DictService;
import com.lele.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedisTemplate redisTemplate;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDto.class,new ExcelDictDTOListener(dictMapper)).sheet().doRead();
        log.info("Excel导入成功");
    }

    @Override
    public List<Dict> listByParentId(Long parentId) {
        try {
            //先从redis中查，如果redis有，则直接返回。

        List<Dict> dictList = (List<Dict>)redisTemplate.opsForValue().get("srb:core:dictList:"+parentId);
        if(!CollectionUtils.isEmpty(dictList)){
            return dictList;
        }
        }catch (Exception e){
            log.error("redis服务器异常:"+ ExceptionUtils.getStackTrace(e));
        }
        //如果redis没有，则从数据库中查询
        List<Dict> dictList = dictMapper.listByParentId(parentId);

        try {
            //将查询到的结果放到redis中
            redisTemplate.opsForValue().set("srb:core:dictList:"+parentId,dictList,5, TimeUnit.MINUTES);
        }catch (Exception e){
            log.error("redis服务器异常:"+ ExceptionUtils.getStackTrace(e));
        }
        //返回结果
        return dictList;
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
