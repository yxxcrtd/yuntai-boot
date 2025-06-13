package com.joyintech.yuntai.framework.excel.core.util;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class MyAnalysisEventListener<T> extends AnalysisEventListener<T> {
    List<T> list = new ArrayList<>();
    Set<String> set = new HashSet<>();
    List<String> headerRow = new ArrayList<>();
    @Getter
    String sheetName = "";

    /**
     * 缓存的数据
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        // 处理表头行数据
        for (int i = 0; i < headMap.size(); i++) {
            String header = headMap.get(i);
            headerRow.add(header);
        }
    }

    @Override
    public void invoke(T t, AnalysisContext context) {
        sheetName = context.readSheetHolder().getSheetName();
        log.info("解析到一条数据:{}", t);
        list.add(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<String> getHeaderRow(){
        return headerRow;
    }

    public List<T> getDataList(){
        //自定义方法
        List<T> resultList = new ArrayList<>(list);
        list.clear();
        set.clear();
        return resultList;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("======>>>解析异常：", exception);
        throw exception;
    }

}
