package com.lele.srb.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lele.srb.core.mapper.DictMapper;
import com.lele.srb.core.pojo.entity.dto.ExcelDictDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class ExcelDictDTOListener extends AnalysisEventListener<ExcelDictDto> {

    private DictMapper dictMapper;
    //先把数据存到list中，等达到一定数量批量插入
    private List<ExcelDictDto> list =  new ArrayList<ExcelDictDto>();

    //每隔五条数据批量存储一次
    static final int BATCH_COUNT = 5;

    public ExcelDictDTOListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(ExcelDictDto data, AnalysisContext context) {
        log.info("解析: {}",data);
        list.add(data);

        if(list.size() >= BATCH_COUNT){
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //最后数据在这里存
        saveData();
        log.info("完成");
    }

    private void saveData(){
        log.info("{} 条数据被存储到数据库。",list.size());
        dictMapper.insertBatch(list);
        log.info("{} 条数据被存储到数据库成功！",list.size());

    }
}
